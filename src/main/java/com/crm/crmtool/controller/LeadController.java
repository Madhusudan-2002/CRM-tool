package com.crm.crmtool.controller;

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

    private final LeadService leadService;

    public LeadController(LeadService leadService) {
        this.leadService = leadService;
    }

    @PostMapping
    public ResponseEntity<Lead> createLead(
            @Valid @RequestBody Lead lead) {

        Lead savedLead = leadService.createLead(lead);

        return new ResponseEntity<>(
                savedLead,
                HttpStatus.CREATED
        );
    }

    @PostMapping("/{id}/convert")
    public ResponseEntity<Contact> convertLeadToContact(
            @PathVariable Long id) {

        Contact contact =
                leadService.convertLeadToContact(id);

        return new ResponseEntity<>(
                contact,
                HttpStatus.CREATED
        );
    }

    @GetMapping
    public ResponseEntity<Page<Lead>> getAllLeads(
            Pageable pageable) {

        return ResponseEntity.ok(
                leadService.getAllLeads(pageable)
        );
    }
}
























     
     



       


        
  

  


