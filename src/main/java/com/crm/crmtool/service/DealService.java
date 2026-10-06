package com.crm.crmtool.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.crm.crmtool.entity.Deal;
import com.crm.crmtool.repository.DealRepository;

@Service
public class DealService {

    private static final Logger logger =
            LoggerFactory.getLogger(DealService.class);

    private final DealRepository dealRepository;

    public DealService(DealRepository dealRepository) {
        this.dealRepository = dealRepository;
    }

    /**
     * Creates a new deal after validating mandatory fields.
     *
     * @param deal deal details
     * @return saved deal
     */
    public Deal createDeal(Deal deal) {

        logger.info("DealService.createDeal - start");

        // Validate deal title
        if (deal.getTitle() == null
                || deal.getTitle().isBlank()) {

            logger.warn(
                    "DealService.createDeal - deal title is missing"
            );

            throw new RuntimeException(
                    "Deal title is required"
            );
        }

        // Validate deal amount
        if (deal.getAmount() == null) {

            logger.warn(
                    "DealService.createDeal - deal amount is missing"
            );

            throw new RuntimeException(
                    "Deal amount is required"
            );
        }

        // Validate deal stage
        if (deal.getStage() == null
                || deal.getStage().isBlank()) {

            logger.warn(
                    "DealService.createDeal - deal stage is missing"
            );

            throw new RuntimeException(
                    "Deal stage is required"
            );
        }

        Deal savedDeal =
                dealRepository.save(deal);

        logger.info(
                "DealService.createDeal - success - dealId={}",
                savedDeal.getId()
        );

        return savedDeal;
    }

    /**
     * Fetches all deals using pagination.
     *
     * @param pageable pagination details
     * @return paginated list of deals
     */
    public Page<Deal> getAllDeals(Pageable pageable) {

        logger.info(
                "DealService.getAllDeals - start - page={}, size={}",
                pageable.getPageNumber(),
                pageable.getPageSize()
        );

        Page<Deal> deals =
                dealRepository.findAll(pageable);

        logger.info(
                "DealService.getAllDeals - success - count={}",
                deals.getNumberOfElements()
        );

        return deals;
    }
}







