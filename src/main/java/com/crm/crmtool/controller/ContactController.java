package com.crm.crmtool.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.crm.crmtool.entity.Contact;
import com.crm.crmtool.service.ContactService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/contacts")
public class ContactController {

    private static final Logger logger =
            LoggerFactory.getLogger(ContactController.class);

    private final ContactService contactService;

    public ContactController(
            ContactService contactService) {

        this.contactService = contactService;
    }

    /**
     * Creates a new contact.
     *
     * @param contact contact details
     * @return created contact
     */
    @PostMapping
    public ResponseEntity<Contact> createContact(
            @Valid @RequestBody Contact contact) {

        logger.info(
                "ContactController.createContact - request received"
        );

        Contact savedContact =
                contactService.createContact(contact);

        logger.info(
                "ContactController.createContact - success - contactId={}",
                savedContact.getId()
        );

        return new ResponseEntity<>(
                savedContact,
                HttpStatus.CREATED
        );
    }

    /**
     * Fetches all contacts using pagination.
     *
     * @param pageable pagination information
     * @return paginated contacts
     */
    @GetMapping
    public ResponseEntity<Page<Contact>> getAllContacts(
            Pageable pageable) {

        logger.info(
                "ContactController.getAllContacts - request received - page={}, size={}",
                pageable.getPageNumber(),
                pageable.getPageSize()
        );

        Page<Contact> contacts =
                contactService.getAllContacts(pageable);

        logger.info(
                "ContactController.getAllContacts - success - count={}",
                contacts.getNumberOfElements()
        );

        return ResponseEntity.ok(contacts);
    }
}




