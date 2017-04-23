package com.templesalad.repository;

import com.templesalad.domain.Stock;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Stock entity.
 */
@SuppressWarnings("unused")
public interface StockRepository extends JpaRepository<Stock,Long> {

}
