package com.templesalad.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.templesalad.domain.Battery;

import com.templesalad.repository.BatteryRepository;
import com.templesalad.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * REST controller for managing Battery.
 */
@RestController
@RequestMapping("/api")
public class BatteryResource {

    private final Logger log = LoggerFactory.getLogger(BatteryResource.class);

    private static final String ENTITY_NAME = "battery";
        
    private final BatteryRepository batteryRepository;

    public BatteryResource(BatteryRepository batteryRepository) {
        this.batteryRepository = batteryRepository;
    }

    /**
     * POST  /batteries : Create a new battery.
     *
     * @param battery the battery to create
     * @return the ResponseEntity with status 201 (Created) and with body the new battery, or with status 400 (Bad Request) if the battery has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/batteries")
    @Timed
    public ResponseEntity<Battery> createBattery(@Valid @RequestBody Battery battery) throws URISyntaxException {
        log.debug("REST request to save Battery : {}", battery);
        if (battery.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new battery cannot already have an ID")).body(null);
        }
        Battery result = batteryRepository.save(battery);
        return ResponseEntity.created(new URI("/api/batteries/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /batteries : Updates an existing battery.
     *
     * @param battery the battery to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated battery,
     * or with status 400 (Bad Request) if the battery is not valid,
     * or with status 500 (Internal Server Error) if the battery couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/batteries")
    @Timed
    public ResponseEntity<Battery> updateBattery(@Valid @RequestBody Battery battery) throws URISyntaxException {
        log.debug("REST request to update Battery : {}", battery);
        if (battery.getId() == null) {
            return createBattery(battery);
        }
        Battery result = batteryRepository.save(battery);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, battery.getId().toString()))
            .body(result);
    }

    /**
     * GET  /batteries : get all the batteries.
     *
     * @param filter the filter of the request
     * @return the ResponseEntity with status 200 (OK) and the list of batteries in body
     */
    @GetMapping("/batteries")
    @Timed
    public List<Battery> getAllBatteries(@RequestParam(required = false) String filter) {
        if ("stock-is-null".equals(filter)) {
            log.debug("REST request to get all Batterys where stock is null");
            return StreamSupport
                .stream(batteryRepository.findAll().spliterator(), false)
                .filter(battery -> battery.getStock() == null)
                .collect(Collectors.toList());
        }
        log.debug("REST request to get all Batteries");
        List<Battery> batteries = batteryRepository.findAll();
        return batteries;
    }

    /**
     * GET  /batteries/:id : get the "id" battery.
     *
     * @param id the id of the battery to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the battery, or with status 404 (Not Found)
     */
    @GetMapping("/batteries/{id}")
    @Timed
    public ResponseEntity<Battery> getBattery(@PathVariable Long id) {
        log.debug("REST request to get Battery : {}", id);
        Battery battery = batteryRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(battery));
    }

    /**
     * DELETE  /batteries/:id : delete the "id" battery.
     *
     * @param id the id of the battery to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/batteries/{id}")
    @Timed
    public ResponseEntity<Void> deleteBattery(@PathVariable Long id) {
        log.debug("REST request to delete Battery : {}", id);
        batteryRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
