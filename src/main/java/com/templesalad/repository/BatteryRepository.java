package com.templesalad.repository;

import com.templesalad.domain.Battery;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Battery entity.
 */
@SuppressWarnings("unused")
public interface BatteryRepository extends JpaRepository<Battery,Long> {

}
