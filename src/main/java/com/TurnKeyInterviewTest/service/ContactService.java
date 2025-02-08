package com.TurnKeyInterviewTest.service;

import com.TurnKeyInterviewTest.entity.Contact;
import com.TurnKeyInterviewTest.validation.AddContactValidation;
import com.TurnKeyInterviewTest.validation.UpdateContactValidation;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ContactService {

    List<Contact> getSortedContacts();

    void save(AddContactValidation addBlogValidation);

    Contact findById(int id);

    List<Contact> allContact();

    void deleteById(int id);

    void update(UpdateContactValidation updateContactValidation);

    List<Contact> search(String searchText);

    void deleteContacts(List<Long> ids);
}
