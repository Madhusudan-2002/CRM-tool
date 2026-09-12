package com.crm.crmtool.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.crm.crmtool.entity.Contact;
import com.crm.crmtool.repository.ContactRepository;

@Service
public class ContactService {

    private final ContactRepository contactRepository;

    public ContactService(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    public Contact createContact(Contact contact) {

        if (contact.getFirstName() == null ||
                contact.getFirstName().isBlank()) {

            throw new RuntimeException("First name is required");
        }

        if (contact.getEmail() == null ||
                contact.getEmail().isBlank()) {

            throw new RuntimeException("Email is required");
        }

        if (contactRepository.findByEmail(contact.getEmail()).isPresent()) {
            throw new RuntimeException(
                    "Contact already exists with this email");
        }

        return contactRepository.save(contact);
    }

    public Page<Contact> getAllContacts(Pageable pageable) {

        return contactRepository.findAll(pageable);
    }
}




