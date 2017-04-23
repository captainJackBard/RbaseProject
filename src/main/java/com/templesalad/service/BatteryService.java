package com.templesalad.service;

import com.templesalad.domain.Battery;
import com.templesalad.domain.enumeration.Vehicle;
import com.templesalad.repository.BatteryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class BatteryService {

    private final Logger log = LoggerFactory.getLogger(BatteryService.class);

    private final BatteryRepository batteryRepository;

    public BatteryService(BatteryRepository batteryRepository) {
        this.batteryRepository = batteryRepository;
    }

    @Transactional(readOnly = true)
    public List<Battery> findByVehicleType(Vehicle type) {
        log.debug("Finding by type...");
        List<Battery> batteries = batteryRepository.findAll();
        List<Battery> results = new ArrayList<>();
        for ( Battery battery : batteries ) {
            if (battery.getType() == type) {
                results.add(battery);
            }
        }
        return results;
    }

    @Transactional(readOnly = true)
    public Battery findByModel(Vehicle type, String model) {
        List<Battery> batteries = findByVehicleType(type);
        for(Battery battery : batteries) {
            if (battery.getModel().equalsIgnoreCase(model)) {
                return battery;
            }
        }
        return null;
    }

}
