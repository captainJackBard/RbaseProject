package com.templesalad.service;

import com.templesalad.domain.Branch;
import com.templesalad.domain.Invoice;
import com.templesalad.repository.BranchRepository;
import com.templesalad.repository.InvoiceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class InvoiceService {

    private final Logger log = LoggerFactory.getLogger(InvoiceService.class);

    private final InvoiceRepository invoiceRepository;

    private final BranchRepository branchRepository;

    public InvoiceService(InvoiceRepository invoiceRepository, BranchRepository branchRepository) {
        this.invoiceRepository = invoiceRepository;
        this.branchRepository = branchRepository;
    }

    @Transactional(readOnly = true)
    public List<Invoice> findInvoiceByCurrentUser() {
        List<Invoice> invoices = new ArrayList<>();
        List<Branch> branches = branchRepository.findByUserIsCurrentUser();
        for (Branch branch : branches) {
            invoices.addAll(branch.getInvoices());
        }
        return invoices;
    }
}
