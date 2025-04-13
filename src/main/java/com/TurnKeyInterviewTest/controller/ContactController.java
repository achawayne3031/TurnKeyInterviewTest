package com.TurnKeyInterviewTest.controller;


import com.TurnKeyInterviewTest.config.ApiResponse;
import com.TurnKeyInterviewTest.contactmanager.ContactGroup;
import com.TurnKeyInterviewTest.dao.ContactDao;
import com.TurnKeyInterviewTest.entity.Contact;
import com.TurnKeyInterviewTest.exceptions.CustomException;
import com.TurnKeyInterviewTest.exceptions.CustomNotFoundException;
import com.TurnKeyInterviewTest.repository.ContactRepository;
import com.TurnKeyInterviewTest.service.ContactService;
import com.TurnKeyInterviewTest.utils.CsvGeneratorUtil;
import com.TurnKeyInterviewTest.validation.AddContactValidation;
import com.TurnKeyInterviewTest.validation.UpdateContactValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@RestController
@RequestMapping("/api/contact")
@CrossOrigin(originPatterns = "*", allowedHeaders = "*", allowCredentials = "true")
public class ContactController {

    private ContactService contactService;

    private List<String> imageFormat =  List.of("image/jpeg", "image/jpg", "image/png");

    private ContactDao contactDao;

    @Autowired
    private ContactRepository contactRepository;

    @Autowired
    private CsvGeneratorUtil csvGeneratorUtil;

    @Autowired
    public ContactController(ContactService contactService, ContactDao contactDao, ContactRepository contactRepository) {
        this.contactService = contactService;
        this.contactDao = contactDao;
        this.contactRepository = contactRepository;

    }

    @GetMapping("/export")
    public ResponseEntity exportContactsToCSV() throws IOException {
        List<Contact> contacts = contactService.getSortedContacts();

        byte[] csvBytes = csvGeneratorUtil.generateCsv(contacts).getBytes();

        return new ResponseEntity<>(new ApiResponse<Object>("Export data.", true, csvBytes), HttpStatus.OK);
    }

    @DeleteMapping("/bulk-delete")
    public ResponseEntity bulkDeleteContacts(@RequestBody List<Long> ids) {
        contactService.deleteContacts(ids);
        return new ResponseEntity<>(new ApiResponse<Object>("Bulk delete done.", true), HttpStatus.OK);
    }


    @GetMapping("/filter-group")
    public ResponseEntity filterByGroup(
            @RequestParam("contactGroup") String contactGroup
    ){

        List<Contact> contacts = contactService.filterByGroup(contactGroup);
        return new ResponseEntity<>(new ApiResponse<Object>("Contact filter group.", true, contacts), HttpStatus.OK);
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



    @PostMapping("/save")
    public ResponseEntity saveContact(@RequestBody @Valid AddContactValidation addContactValidation) {

        if (contactRepository.existsByEmail(addContactValidation.getEmail())) {
            return new ResponseEntity<>(new ApiResponse<Object>("Email already registered", true), HttpStatus.OK);
        }

       Contact contact = contactService.saveContact(addContactValidation);

        return new ResponseEntity<>(new ApiResponse<Object>("Contact added", true, contact),
                HttpStatus.OK);

    }




    @PostMapping("/add")
    public ResponseEntity registerUser(@RequestBody @Valid AddContactValidation addContactValidation) {

        if (contactRepository.existsByEmail(addContactValidation.getEmail())) {
            return new ResponseEntity<>(new ApiResponse<Object>("Email already registered", true), HttpStatus.OK);
        }

        //// Save ////
        Contact contact = new Contact();
        contact.setLastName(addContactValidation.getLastName());
        contact.setFirstName(addContactValidation.getFirstName());
        contact.setContactImage(addContactValidation.getContactImage());

//        if(!contact.getContactGroup().isEmpty() && !addContactValidation.getContactGroup().isBlank()){
//          contact.setContactGroup(ContactGroup.valueOf(addContactValidation.getContactGroup()));
//        }

        contact.setContactGroup(ContactGroup.valueOf(addContactValidation.getContactGroup()));
        contact.setEmail(addContactValidation.getEmail());
        contact.setPhoneNumber(addContactValidation.getPhoneNumber());
        contact.setPhysicalAddress(addContactValidation.getPhysicalAddress());


        contactService.save(contact);
        return new ResponseEntity<>(new ApiResponse<Object>("Contact added", true),
                HttpStatus.OK);

    }


    @GetMapping("/")
    public ResponseEntity allContact(){
        List<Contact> contacts = contactService.getSortedContacts();
        return new ResponseEntity<>(new ApiResponse<Object>("All contacts.", true, contacts), HttpStatus.OK);
    }







}
