package com.crm.crmtool.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.crm.crmtool.entity.Deal;
import com.crm.crmtool.service.DealService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/deals")
public class DealController {

    private final DealService dealService;

    public DealController(DealService dealService) {
        this.dealService = dealService;
    }

    @PostMapping
    public ResponseEntity<Deal> createDeal(
            @Valid @RequestBody Deal deal) {

        Deal savedDeal =
                dealService.createDeal(deal);

        return new ResponseEntity<>(
                savedDeal,
                HttpStatus.CREATED
        );
    }

    @GetMapping
    public ResponseEntity<Page<Deal>> getAllDeals(
            Pageable pageable) {

        return ResponseEntity.ok(
                dealService.getAllDeals(pageable)
        );
    }
}




