package com.crm.crmtool.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.crm.crmtool.entity.Contact;
import com.crm.crmtool.repository.ContactRepository;

@Service
public class ContactService {

    private static final Logger logger =
            LoggerFactory.getLogger(ContactService.class);

    private final ContactRepository contactRepository;

    public ContactService(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    /**
     * Creates a new contact after validating mandatory fields
     * and checking for duplicate email.
     *
     * @param contact contact details
     * @return saved contact
     */
    public Contact createContact(Contact contact) {

        logger.info("ContactService.createContact - start");

        // Validate first name
        if (contact.getFirstName() == null
                || contact.getFirstName().isBlank()) {

            logger.warn(
                    "ContactService.createContact - first name is missing"
            );

            throw new RuntimeException(
                    "First name is required"
            );
        }

        // Validate email
        if (contact.getEmail() == null
                || contact.getEmail().isBlank()) {

            logger.warn(
                    "ContactService.createContact - email is missing"
            );

            throw new RuntimeException(
                    "Email is required"
            );
        }

        // Prevent duplicate contacts with the same email
        if (contactRepository
                .findByEmail(contact.getEmail())
                .isPresent()) {

            logger.warn(
                    "ContactService.createContact - duplicate email: {}",
                    contact.getEmail()
            );

            throw new RuntimeException(
                    "Contact already exists with this email"
            );
        }

        Contact savedContact =
                contactRepository.save(contact);

        logger.info(
                "ContactService.createContact - success - contactId={}",
                savedContact.getId()
        );

        return savedContact;
    }

    /**
     * Fetches all contacts using pagination.
     *
     * @param pageable pagination details
     * @return paginated list of contacts
     */
    public Page<Contact> getAllContacts(Pageable pageable) {

        logger.info(
                "ContactService.getAllContacts - start - page={}, size={}",
                pageable.getPageNumber(),
                pageable.getPageSize()
        );

        Page<Contact> contacts =
                contactRepository.findAll(pageable);

        logger.info(
                "ContactService.getAllContacts - success - count={}",
                contacts.getNumberOfElements()
        );

        return contacts;
    }
}




