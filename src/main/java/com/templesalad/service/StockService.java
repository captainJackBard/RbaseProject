package com.templesalad.service;

import com.templesalad.domain.Battery;
import com.templesalad.domain.Branch;
import com.templesalad.domain.Stock;
import com.templesalad.repository.BatteryRepository;
import com.templesalad.repository.BranchRepository;
import com.templesalad.repository.StockRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class StockService {

    private final Logger log = LoggerFactory.getLogger(StockService.class);

    private final BranchRepository branchRepository;

    private final StockRepository stockRepository;

    public StockService(BranchRepository branchRepository, StockRepository stockRepository) {
        this.branchRepository = branchRepository;
        this.stockRepository = stockRepository;
    }

    @Transactional(readOnly = true)
    public List<Stock> findStocskByCurrentUser() {
        List<Stock> stocks = new ArrayList<>();
        List<Branch> branches = branchRepository.findByUserIsCurrentUser();
        for(Branch branch : branches) {
            stocks.addAll(branch.getStocks());
        }
        return stocks;
    }

    public Stock reduceStock(Stock stock) {
        stock.setQuantity(stock.getQuantity() -1);
        stockRepository.save(stock);
        return stock;
    }
}
