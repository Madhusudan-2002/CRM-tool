package com.crm.crmtool.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.crm.crmtool.entity.Contact;
import com.crm.crmtool.entity.Lead;
import com.crm.crmtool.repository.ContactRepository;
import com.crm.crmtool.repository.LeadRepository;

@Service
public class LeadService {

    private final LeadRepository leadRepository;
    private final ContactRepository contactRepository;

    public LeadService(LeadRepository leadRepository,
                       ContactRepository contactRepository) {

        this.leadRepository = leadRepository;
        this.contactRepository = contactRepository;
    }

    public Lead createLead(Lead lead) {

        if (lead.getFirstName() == null || lead.getFirstName().isBlank()) {
            throw new RuntimeException("First name is required");
        }

        if (lead.getEmail() == null || lead.getEmail().isBlank()) {
            throw new RuntimeException("Email is required");
        }

        if (leadRepository.findByEmail(lead.getEmail()).isPresent()) {
            throw new RuntimeException("Lead already exists with this email");
        }

        return leadRepository.save(lead);
    }

    public Contact convertLeadToContact(Long leadId) {

        Lead lead = leadRepository.findById(leadId)
                .orElseThrow(() -> new RuntimeException("Lead not found"));

        if (lead.getConvertedContactId() != null) {
            throw new RuntimeException("Lead is already converted");
        }

        Contact contact = new Contact();

        contact.setFirstName(lead.getFirstName());
        contact.setLastName(lead.getLastName());
        contact.setEmail(lead.getEmail());
        contact.setPhone(lead.getPhone());
        contact.setCompanyName(lead.getCompanyName());
        contact.setOwnerId(lead.getOwnerId());
        contact.setLeadId(lead.getId());

        Contact savedContact = contactRepository.save(contact);

        lead.setConvertedContactId(savedContact.getId());

        leadRepository.save(lead);

        return savedContact;
    }

    public Page<Lead> getAllLeads(Pageable pageable) {
        return leadRepository.findAll(pageable);
    }
}

 


     

        


     

        






