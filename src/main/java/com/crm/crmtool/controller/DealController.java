package com.crm.crmtool.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger logger =
            LoggerFactory.getLogger(DealController.class);

    private final DealService dealService;

    public DealController(DealService dealService) {
        this.dealService = dealService;
    }

    /**
     * Creates a new deal.
     *
     * @param deal deal details
     * @return created deal
     */
    @PostMapping
    public ResponseEntity<Deal> createDeal(
            @Valid @RequestBody Deal deal) {

        logger.info(
                "DealController.createDeal - request received"
        );

        Deal savedDeal =
                dealService.createDeal(deal);

        logger.info(
                "DealController.createDeal - success - dealId={}",
                savedDeal.getId()
        );

        return new ResponseEntity<>(
                savedDeal,
                HttpStatus.CREATED
        );
    }

    /**
     * Fetches all deals using pagination.
     *
     * @param pageable pagination information
     * @return paginated deals
     */
    @GetMapping
    public ResponseEntity<Page<Deal>> getAllDeals(
            Pageable pageable) {

        logger.info(
                "DealController.getAllDeals - request received - page={}, size={}",
                pageable.getPageNumber(),
                pageable.getPageSize()
        );

        Page<Deal> deals =
                dealService.getAllDeals(pageable);

        logger.info(
                "DealController.getAllDeals - success - count={}",
                deals.getNumberOfElements()
        );

        return ResponseEntity.ok(deals);
    }
}




