package com.crm.crmtool.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.crm.crmtool.entity.Contact;
import com.crm.crmtool.entity.Lead;
import com.crm.crmtool.repository.ContactRepository;
import com.crm.crmtool.repository.LeadRepository;

@Service
public class LeadService {

    private static final Logger logger =
            LoggerFactory.getLogger(LeadService.class);

    private final LeadRepository leadRepository;
    private final ContactRepository contactRepository;

    public LeadService(
            LeadRepository leadRepository,
            ContactRepository contactRepository) {

        this.leadRepository = leadRepository;
        this.contactRepository = contactRepository;
    }

    /**
     * Creates a new lead after validating mandatory fields
     * and checking for duplicate email.
     *
     * @param lead lead details
     * @return saved lead
     */
    public Lead createLead(Lead lead) {

        logger.info("LeadService.createLead - start");

        // Validate first name
        if (lead.getFirstName() == null
                || lead.getFirstName().isBlank()) {

            logger.warn(
                    "LeadService.createLead - first name is missing"
            );

            throw new RuntimeException(
                    "First name is required"
            );
        }

        // Validate email
        if (lead.getEmail() == null
                || lead.getEmail().isBlank()) {

            logger.warn(
                    "LeadService.createLead - email is missing"
            );

            throw new RuntimeException(
                    "Email is required"
            );
        }

        // Check duplicate email
        if (leadRepository
                .findByEmail(lead.getEmail())
                .isPresent()) {

            logger.warn(
                    "LeadService.createLead - duplicate email: {}",
                    lead.getEmail()
            );

            throw new RuntimeException(
                    "Lead already exists with this email"
            );
        }

        Lead savedLead =
                leadRepository.save(lead);

        logger.info(
                "LeadService.createLead - success - leadId={}",
                savedLead.getId()
        );

        return savedLead;
    }

    /**
     * Converts an existing lead into a contact.
     *
     * CONTACT.lead_id is used as the foreign key
     * to reference LEADS.id.
     *
     * @param leadId lead id
     * @return created contact
     */
    public Contact convertLeadToContact(Long leadId) {

        logger.info(
                "LeadService.convertLeadToContact - start - leadId={}",
                leadId
        );

        Lead lead = leadRepository
                .findById(leadId)
                .orElseThrow(() -> {

                    logger.warn(
                            "LeadService.convertLeadToContact - lead not found - leadId={}",
                            leadId
                    );

                    return new RuntimeException(
                            "Lead not found"
                    );
                });

        /*
         * Check whether a contact already exists for this lead.
         * This avoids maintaining duplicate relationship data
         * inside both LEADS and CONTACT.
         */
        boolean alreadyConverted =
                contactRepository
                        .findAll()
                        .stream()
                        .anyMatch(contact ->
                                contact.getLeadId() != null
                                        && contact.getLeadId()
                                        .equals(leadId)
                        );

        if (alreadyConverted) {

            logger.warn(
                    "LeadService.convertLeadToContact - lead already converted - leadId={}",
                    leadId
            );

            throw new RuntimeException(
                    "Lead is already converted"
            );
        }

        Contact contact = new Contact();

        // Copy lead data into contact
        contact.setFirstName(
                lead.getFirstName()
        );

        contact.setLastName(
                lead.getLastName()
        );

        contact.setEmail(
                lead.getEmail()
        );

        contact.setPhone(
                lead.getPhone()
        );

        contact.setCompanyName(
                lead.getCompanyName()
        );

        contact.setOwnerId(
                lead.getOwnerId()
        );

        // FK: CONTACT.lead_id -> LEADS.id
        contact.setLeadId(
                lead.getId()
        );

        Contact savedContact =
                contactRepository.save(contact);

        logger.info(
                "LeadService.convertLeadToContact - success - leadId={}, contactId={}",
                leadId,
                savedContact.getId()
        );

        return savedContact;
    }

    /**
     * Fetches all leads using pagination.
     *
     * @param pageable pagination details
     * @return paginated list of leads
     */
    public Page<Lead> getAllLeads(
            Pageable pageable) {

        logger.info(
                "LeadService.getAllLeads - start - page={}, size={}",
                pageable.getPageNumber(),
                pageable.getPageSize()
        );

        Page<Lead> leads =
                leadRepository.findAll(pageable);

        logger.info(
                "LeadService.getAllLeads - success - count={}",
                leads.getNumberOfElements()
        );

        return leads;
    }
}

 


     

        


     

        






