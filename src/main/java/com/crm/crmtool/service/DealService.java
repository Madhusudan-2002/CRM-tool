package com.crm.crmtool.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.crm.crmtool.entity.Deal;
import com.crm.crmtool.repository.DealRepository;

@Service
public class DealService {

    private final DealRepository dealRepository;

    public DealService(DealRepository dealRepository) {
        this.dealRepository = dealRepository;
    }

    public Deal createDeal(Deal deal) {

        if (deal.getTitle() == null ||
                deal.getTitle().isBlank()) {

            throw new RuntimeException(
                    "Deal title is required");
        }

        if (deal.getAmount() == null) {
            throw new RuntimeException(
                    "Deal amount is required");
        }

        if (deal.getStage() == null ||
                deal.getStage().isBlank()) {

            throw new RuntimeException(
                    "Deal stage is required");
        }

        return dealRepository.save(deal);
    }

    public Page<Deal> getAllDeals(Pageable pageable) {

        return dealRepository.findAll(pageable);
    }
}







