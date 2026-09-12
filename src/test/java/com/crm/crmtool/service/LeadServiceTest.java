package com.crm.crmtool.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.crm.crmtool.entity.Lead;
import com.crm.crmtool.repository.LeadRepository;

@ExtendWith(MockitoExtension.class)
class LeadServiceTest {

    @Mock
    private LeadRepository leadRepository;

    @InjectMocks
    private LeadService leadService;

    private Lead lead;

    @BeforeEach
    void setUp() {
        lead = new Lead();
        lead.setId(1L);
        lead.setFirstName("Rahul");
        lead.setLastName("Sharma");
        lead.setEmail("rahul.sharma@gmail.com");
    }

    @Test
    void createLeadSuccess() {

        when(leadRepository.findByEmail(lead.getEmail()))
                .thenReturn(Optional.empty());

        when(leadRepository.save(lead))
                .thenReturn(lead);

        Lead result = leadService.createLead(lead);

        assertNotNull(result);
        assertEquals("Rahul", result.getFirstName());

        verify(leadRepository, times(1)).save(lead);
    }

    @Test
    void createLeadDuplicateEmail() {

        when(leadRepository.findByEmail(lead.getEmail()))
                .thenReturn(Optional.of(lead));

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> leadService.createLead(lead)
        );

        assertEquals(
                "Lead already exists with this email",
                exception.getMessage()
        );

        verify(leadRepository, never()).save(any());
    }

    @Test
    void createLeadWithoutFirstName() {

        lead.setFirstName(null);

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> leadService.createLead(lead)
        );

        assertEquals(
                "First name is required",
                exception.getMessage()
        );
    }

    @Test
    void createLeadWithoutEmail() {

        lead.setEmail(null);

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> leadService.createLead(lead)
        );

        assertEquals(
                "Email is required",
                exception.getMessage()
        );
    }
}




