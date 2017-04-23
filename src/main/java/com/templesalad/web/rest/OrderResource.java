package com.templesalad.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.errors.ApiException;
import com.google.maps.model.GeocodingResult;
import com.templesalad.domain.Battery;
import com.templesalad.domain.Branch;
import com.templesalad.domain.Invoice;
import com.templesalad.domain.Location;
import com.templesalad.domain.enumeration.Vehicle;
import com.templesalad.repository.BatteryRepository;
import com.templesalad.repository.BranchRepository;
import com.templesalad.repository.InvoiceRepository;
import com.templesalad.repository.LocationRepository;
import com.templesalad.service.BatteryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

/**
 * Created by bobjr on 4/23/17.
 */
@RestController
@RequestMapping("/api")
public class OrderResource {

    private final Logger log = LoggerFactory.getLogger(OrderResource.class);

    private final InvoiceRepository invoiceRepository;

    private final LocationRepository locationRepository;

    private final BatteryRepository batteryRepository;

    private final BatteryService batteryService;

    private final BranchRepository branchRepository;

    public OrderResource(InvoiceRepository invoiceRepository, LocationRepository locationRepository, BatteryRepository batteryRepository, BatteryService batteryService, BranchRepository branchRepository) {
        this.invoiceRepository = invoiceRepository;
        this.locationRepository = locationRepository;
        this.batteryRepository = batteryRepository;
        this.batteryService = batteryService;
        this.branchRepository = branchRepository;
    }

    @GetMapping("/smshook")
    @Timed
    public String webhook(@RequestParam String keyword,
                          @RequestParam String from,
                          @RequestParam String to) throws InterruptedException, ApiException, IOException {
        String[] order = keyword.split("/");

        Battery battery = batteryService.findByModel(Vehicle.valueOf(order[0]), order[1]);

        if (battery == null) {
            return "No Battery for that Type/Model combination";
        }

        String[] address = order[2].split(",");

        Invoice invoice = new Invoice();
        invoice.setBattery(battery);
        invoice.setTotal(battery.getPrice());

        Location location = new Location();
        location.setStreetAddress(address[0]);
        location.setCity(address[1]);
        location.setStateProvince(address[2]);
        location.setPostalCode(address[3]);

        GeoApiContext context = new GeoApiContext().setApiKey("AIzaSyAOCUiioJ-rKGfB81oyKn98yyubrflEuIs");
        GeocodingResult[] results = GeocodingApi.geocode(context, order[2]).await();

        invoice.setLocation(location);

        List<Branch> branches = branchRepository.findAll();

        invoice.setBranch(branches.get(0));

        invoiceRepository.save(invoice);

        return "Order has been received!";
    }

}
