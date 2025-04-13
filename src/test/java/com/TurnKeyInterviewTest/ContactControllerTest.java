package com.TurnKeyInterviewTest;

import com.TurnKeyInterviewTest.config.ApiResponse;
import com.TurnKeyInterviewTest.contactmanager.ContactGroup;
import com.TurnKeyInterviewTest.controller.ContactController;
import com.TurnKeyInterviewTest.entity.Contact;
import com.TurnKeyInterviewTest.repository.ContactRepository;
import com.TurnKeyInterviewTest.service.ContactService;
import com.TurnKeyInterviewTest.validation.AddContactValidation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;


import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import java.util.Collections;

public class ContactControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ContactService contactService;


    @InjectMocks
    private ContactController contactController;

    @Mock
    private ContactRepository contactRepository;



    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(contactController).build();

    }

    @Test
    void testCreateContact() {
        Contact contact = new Contact();

        contact.setContactImage("www.google.com");
        contact.setEmail("achawayne23@gmail.com");
        contact.setLastName("Wizkid");
        contact.setFirstName("Odimu");
        contact.setPhysicalAddress("ago okota");
        contact.setContactImage("www.google.com");
        contact.setPhoneNumber("787876767678");
        contact.setContactGroup(ContactGroup.valueOf("OTHER"));


        when(contactService.save(contact)).thenReturn(contact);

        AddContactValidation addContactValidation = new AddContactValidation();
        addContactValidation.setContactImage("www.google.com");
        addContactValidation.setFirstName("Odimu");
        addContactValidation.setLastName("Wizkid");
        addContactValidation.setEmail("achawayne23@gmail.com");
        addContactValidation.setPhoneNumber("787876767678");
        addContactValidation.setPhysicalAddress("ago okota");
        addContactValidation.setContactGroup("OTHER");



        ResponseEntity<Contact> response = contactController.saveContact(addContactValidation);
        ApiResponse apiResponse = new ApiResponse("Contact added", true);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        // assertEquals(apiResponse, response.getBody());
      ////  verify(contactService, times(1)).save(contact);
    }


    @Test
    public void testGetAllContacts() throws Exception {
        when(contactService.getSortedContacts()).thenReturn(Collections.singletonList(new Contact()));

        mockMvc.perform(get("/api/contact/"))
                .andExpect(status().isOk());
    }
}
