package com.templesalad.service;

import com.templesalad.domain.Battery;
import com.templesalad.domain.Branch;
import com.templesalad.domain.Stock;
import com.templesalad.repository.BranchRepository;
import com.templesalad.repository.StockRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class BranchService {

    private final Logger log = LoggerFactory.getLogger(BranchService.class);

    private final BranchRepository branchRepository;

    private final StockRepository stockRepository;

    public BranchService(BranchRepository branchRepository, StockRepository stockRepository) {
        this.branchRepository = branchRepository;
        this.stockRepository = stockRepository;
    }

    @Transactional(readOnly = true)
    public boolean hasStock(Branch branch, Battery battery) {
        return branch.getStocks().stream()
            .anyMatch(s -> s.getBattery().getName().equals(battery.getName()));
    }

    @Transactional(readOnly = true)
    public Stock getStock(Branch branch, Battery battery) {
        return branch.getStocks().stream()
            .filter(s -> s.getBattery().getName().equals(battery.getName()))
            .findFirst()
            .orElse(null);
    }

    public boolean toggleActive(Branch branch) {
        branch.setAvailable(!branch.isAvailable());
        branchRepository.save(branch);
        return branch.isAvailable();
    }
}
