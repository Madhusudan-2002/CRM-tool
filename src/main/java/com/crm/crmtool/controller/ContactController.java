package com.crm.crmtool.controller;

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

    private final ContactService contactService;

    public ContactController(
            ContactService contactService) {

        this.contactService = contactService;
    }

    @PostMapping
    public ResponseEntity<Contact> createContact(
            @Valid @RequestBody Contact contact) {

        Contact savedContact =
                contactService.createContact(contact);

        return new ResponseEntity<>(
                savedContact,
                HttpStatus.CREATED
        );
    }

    @GetMapping
    public ResponseEntity<Page<Contact>> getAllContacts(
            Pageable pageable) {

        return ResponseEntity.ok(
                contactService.getAllContacts(pageable)
        );
    }
}




