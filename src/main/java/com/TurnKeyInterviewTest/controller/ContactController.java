package com.TurnKeyInterviewTest.controller;


import com.TurnKeyInterviewTest.config.ApiResponse;
import com.TurnKeyInterviewTest.dao.ContactDao;
import com.TurnKeyInterviewTest.entity.Contact;
import com.TurnKeyInterviewTest.exceptions.CustomException;
import com.TurnKeyInterviewTest.exceptions.CustomNotFoundException;
import com.TurnKeyInterviewTest.repository.ContactRepository;
import com.TurnKeyInterviewTest.service.ContactService;
import com.TurnKeyInterviewTest.validation.AddContactValidation;
import com.TurnKeyInterviewTest.validation.UpdateContactValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/api/contact")
public class ContactController {

    private ContactService contactService;

    private List<String> imageFormat =  List.of("image/jpeg", "image/jpg", "image/png");

    private ContactDao contactDao;

    @Autowired
    private ContactRepository contactRepository;

    public ContactController(ContactService contactService, ContactDao contactDao) {
        this.contactService = contactService;
        this.contactDao = contactDao;
    }

    @DeleteMapping("/bulk-delete")
    public ResponseEntity bulkDeleteContacts(@RequestBody List<Long> ids) {
        contactService.deleteContacts(ids);
        return new ResponseEntity<>(new ApiResponse<Object>("Bulk delete done.", true), HttpStatus.OK);
    }


    @GetMapping("/search")
    public ResponseEntity search(
            @RequestParam("search") String search
    ){

        List<Contact> contacts = contactService.search(search);
        return new ResponseEntity<>(new ApiResponse<Object>("Contact search.", true, contacts), HttpStatus.OK);
    }




    @GetMapping("/delete")
    public ResponseEntity delete(
            @RequestParam("contactId") int contactId
    ){

        Contact currentContact = contactDao.findById(contactId);
        if(currentContact == null){
            throw new CustomException("Contact not found");
        }

        contactService.deleteById(contactId);
        return new ResponseEntity<>(new ApiResponse<Object>("Contact deleted.", true), HttpStatus.OK);
    }




    @PostMapping("/update")
    public ResponseEntity updateUser(@RequestBody @Valid UpdateContactValidation updateContactValidation) {
        Contact contact = contactDao.findById(updateContactValidation.getId());
        if(contact == null){
            throw new CustomNotFoundException("Contact not found");
        }

        // Update ////
        contactService.update(updateContactValidation);

        return new ResponseEntity<>(new ApiResponse<Object>("Contact updated.", true),
                HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity registerUser(@RequestBody @Valid AddContactValidation addContactValidation) {

        if (contactRepository.existsByEmail(addContactValidation.getEmail())) {
            return new ResponseEntity<>(new ApiResponse<Object>("Email already registered", true), HttpStatus.OK);
        }

        //// Save ////
        contactService.save(addContactValidation);
        return new ResponseEntity<>(new ApiResponse<Object>("Contact added", true),
                HttpStatus.OK);

    }


    @GetMapping("/")
    public ResponseEntity allContact(){
        List<Contact> contacts = contactService.getSortedContacts();
        return new ResponseEntity<>(new ApiResponse<Object>("All contacts.", true, contacts), HttpStatus.OK);
    }







}
