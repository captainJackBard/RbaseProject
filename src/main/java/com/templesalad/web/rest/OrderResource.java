package com.templesalad.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.errors.ApiException;
import com.google.maps.model.GeocodingResult;
import com.templesalad.Haversine;
import com.templesalad.domain.*;
import com.templesalad.domain.enumeration.Vehicle;
import com.templesalad.repository.BatteryRepository;
import com.templesalad.repository.BranchRepository;
import com.templesalad.repository.InvoiceRepository;
import com.templesalad.repository.LocationRepository;
import com.templesalad.service.BatteryService;
import com.templesalad.service.BranchService;
import com.templesalad.service.StockService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by bobjr on 4/23/17.
 */
@RestController
@RequestMapping("/api")
public class OrderResource {

    private static GeoApiContext context = new GeoApiContext().setApiKey("AIzaSyAOCUiioJ-rKGfB81oyKn98yyubrflEuIs");

    private final Logger log = LoggerFactory.getLogger(OrderResource.class);

    private final InvoiceRepository invoiceRepository;

    private final LocationRepository locationRepository;

    private final BatteryRepository batteryRepository;

    private final BatteryService batteryService;

    private final BranchRepository branchRepository;

    private final StockService stockService;

    private final BranchService branchService;

    public OrderResource(InvoiceRepository invoiceRepository, LocationRepository locationRepository, BatteryRepository batteryRepository, BatteryService batteryService, BranchRepository branchRepository, StockService stockService, BranchService branchService) {
        this.invoiceRepository = invoiceRepository;
        this.locationRepository = locationRepository;
        this.batteryRepository = batteryRepository;
        this.batteryService = batteryService;
        this.branchRepository = branchRepository;
        this.stockService = stockService;
        this.branchService = branchService;
    }

    @GetMapping("/smshook")
    @Timed
    public String webhook(@RequestParam String keyword,
                          @RequestParam String from,
                          @RequestParam String to) throws InterruptedException, ApiException, IOException {
        String[] order = keyword.split("/");

        try {

            if (StringUtils.isBlank(order[0]) || StringUtils.isBlank(order[1]) || StringUtils.isBlank(order[2]) || StringUtils.isBlank(order[3])) {
                return "Incomplete Input, Please follow the proper format of TYPE/MODEL/ADDRESS/YOUR NAME";
            }
        } catch (Exception ex) {
            return "Something Went Wrong! Please Check your Inputs :D";
        }

        Battery battery = batteryService.findByModel(Vehicle.valueOf(order[0]), order[1]);

        if (battery == null) {
            return "No Battery for that TYPE/MODEL combination";
        }

        Invoice invoice = new Invoice();
        invoice.setBattery(battery);
        invoice.setTotal(battery.getPrice());
        invoice.setAddress(order[2]);
        invoice.setName(order[3]);
        invoice.paid(false);

        GeocodingResult[] results = GeocodingApi.geocode(context, order[2]).await();

        if (results.length == 0) {
            return "Invalid Address, Please do not use any abbreviation";
        }

        double startLat = results[0].geometry.location.lat;
        double startLng = results[0].geometry.location.lng;


        List<Branch> nearbyBranches = new ArrayList<>();

        List<Branch> branches = branchRepository.findAll();

        for ( Branch branch : branches ) {
            double distance = Haversine.distance(startLat, startLng, branch.getLatitude(), branch.getLongitude());
            // check if has stock
            boolean hasStock = branchService.hasStock(branch, battery);
            if (distance <= 8 && branch.isAvailable() && hasStock ) {
                nearbyBranches.add(branch);
                Stock stock = branchService.getStock(branch, battery);
                stockService.reduceStock(stock);
            }
        }

        if ( nearbyBranches.isEmpty() ) {
            return "Oh No!! No Avaiable Nearby Branch. Please try again later :D";
        }

        invoice.setBranch(nearbyBranches.get(0));

        branchService.toggleActive(nearbyBranches.get(0));

        invoiceRepository.save(invoice);

        return "Your Order is under process! Please patiently wait for your order to arrive";
    }

}
