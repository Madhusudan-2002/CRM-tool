package com.crm.crmtool.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.crm.crmtool.entity.Deal;
import com.crm.crmtool.repository.DealRepository;

@ExtendWith(MockitoExtension.class)
class DealServiceTest {

    @Mock
    private DealRepository dealRepository;

    @InjectMocks
    private DealService dealService;

    private Deal deal;

    @BeforeEach
    void setUp() {

        deal = new Deal();
        deal.setId(1L);
        deal.setTitle("CRM Software Deal");
        deal.setAmount(new BigDecimal("50000"));
        deal.setStage("QUALIFIED");
    }

    @Test
    void createDealSuccess() {

        when(dealRepository.save(deal))
                .thenReturn(deal);

        Deal result = dealService.createDeal(deal);

        assertNotNull(result);
        assertEquals("CRM Software Deal", result.getTitle());

        verify(dealRepository, times(1)).save(deal);
    }

    @Test
    void createDealWithoutTitle() {

        deal.setTitle(null);

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> dealService.createDeal(deal)
        );

        assertEquals(
                "Deal title is required",
                exception.getMessage()
        );
    }

    @Test
    void createDealWithoutAmount() {

        deal.setAmount(null);

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> dealService.createDeal(deal)
        );

        assertEquals(
                "Deal amount is required",
                exception.getMessage()
        );
    }

    @Test
    void createDealWithoutStage() {

        deal.setStage(null);

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> dealService.createDeal(deal)
        );

        assertEquals(
                "Deal stage is required",
                exception.getMessage()
        );
    }
}




