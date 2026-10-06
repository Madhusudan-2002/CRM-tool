package com.crm.crmtool.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.crm.crmtool.entity.Contact;
import com.crm.crmtool.entity.Lead;
import com.crm.crmtool.service.LeadService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/leads")
public class LeadController {

    private static final Logger logger =
            LoggerFactory.getLogger(LeadController.class);

    private final LeadService leadService;

    public LeadController(LeadService leadService) {
        this.leadService = leadService;
    }

    /**
     * Creates a new lead.
     *
     * @param lead lead details
     * @return created lead
     */
    @PostMapping
    public ResponseEntity<Lead> createLead(
            @Valid @RequestBody Lead lead) {

        logger.info(
                "LeadController.createLead - request received"
        );

        Lead savedLead =
                leadService.createLead(lead);

        logger.info(
                "LeadController.createLead - success - leadId={}",
                savedLead.getId()
        );

        return new ResponseEntity<>(
                savedLead,
                HttpStatus.CREATED
        );
    }

    /**
     * Converts an existing lead into a contact.
     *
     * @param id lead id
     * @return created contact
     */
    @PostMapping("/{id}/convert")
    public ResponseEntity<Contact> convertLeadToContact(
            @PathVariable Long id) {

        logger.info(
                "LeadController.convertLeadToContact - request received - leadId={}",
                id
        );

        Contact contact =
                leadService.convertLeadToContact(id);

        logger.info(
                "LeadController.convertLeadToContact - success - contactId={}",
                contact.getId()
        );

        return new ResponseEntity<>(
                contact,
                HttpStatus.CREATED
        );
    }

    /**
     * Fetches all leads using pagination.
     *
     * @param pageable pagination information
     * @return paginated leads
     */
    @GetMapping
    public ResponseEntity<Page<Lead>> getAllLeads(
            Pageable pageable) {

        logger.info(
                "LeadController.getAllLeads - request received - page={}, size={}",
                pageable.getPageNumber(),
                pageable.getPageSize()
        );

        Page<Lead> leads =
                leadService.getAllLeads(pageable);

        logger.info(
                "LeadController.getAllLeads - success - count={}",
                leads.getNumberOfElements()
        );

        return ResponseEntity.ok(leads);
    }
}
























     
     



       


        
  

  


