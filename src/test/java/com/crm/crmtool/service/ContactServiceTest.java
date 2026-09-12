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

import com.crm.crmtool.entity.Contact;
import com.crm.crmtool.repository.ContactRepository;

@ExtendWith(MockitoExtension.class)
class ContactServiceTest {

    @Mock
    private ContactRepository contactRepository;

    @InjectMocks
    private ContactService contactService;

    private Contact contact;

    @BeforeEach
    void setUp() {
        contact = new Contact();
        contact.setId(1L);
        contact.setFirstName("Priya");
        contact.setLastName("Patil");
        contact.setEmail("priya.patil@gmail.com");
    }

    @Test
    void createContactSuccess() {

        when(contactRepository.findByEmail(contact.getEmail()))
                .thenReturn(Optional.empty());

        when(contactRepository.save(contact))
                .thenReturn(contact);

        Contact result = contactService.createContact(contact);

        assertNotNull(result);
        assertEquals("Priya", result.getFirstName());

        verify(contactRepository, times(1)).save(contact);
    }

    @Test
    void createContactDuplicateEmail() {

        when(contactRepository.findByEmail(contact.getEmail()))
                .thenReturn(Optional.of(contact));

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> contactService.createContact(contact)
        );

        assertEquals(
                "Contact already exists with this email",
                exception.getMessage()
        );

        verify(contactRepository, never()).save(any());
    }

    @Test
    void createContactWithoutFirstName() {

        contact.setFirstName(null);

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> contactService.createContact(contact)
        );

        assertEquals(
                "First name is required",
                exception.getMessage()
        );
    }

    @Test
    void createContactWithoutEmail() {

        contact.setEmail(null);

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> contactService.createContact(contact)
        );

        assertEquals(
                "Email is required",
                exception.getMessage()
        );
    }
}




