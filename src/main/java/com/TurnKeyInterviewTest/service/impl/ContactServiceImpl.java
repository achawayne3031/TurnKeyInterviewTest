package com.TurnKeyInterviewTest.service.impl;

import com.TurnKeyInterviewTest.contactmanager.ContactGroup;
import com.TurnKeyInterviewTest.dao.ContactDao;
import com.TurnKeyInterviewTest.entity.Contact;
import com.TurnKeyInterviewTest.exceptions.CustomNotFoundException;
import com.TurnKeyInterviewTest.repository.ContactRepository;
import com.TurnKeyInterviewTest.service.ContactService;
import com.TurnKeyInterviewTest.validation.AddContactValidation;
import com.TurnKeyInterviewTest.validation.UpdateContactValidation;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class ContactServiceImpl implements ContactService {


    @Autowired
    private final ContactRepository contactRepository;

    private ContactDao contactDao;


    @Autowired
    public ContactServiceImpl(ContactDao contactDao, ContactRepository contactRepository) {
        this.contactDao = contactDao;
        this.contactRepository = contactRepository;
    }

    public boolean existsByEmail(String email) {
        return contactRepository.existsByEmail(email);
    }

    public List<Contact> getSortedContacts() {
        return contactRepository.findAll(Sort.by(Sort.Direction.ASC, "firstName"));
    }


    @Override
    public Contact save(Contact contact) {


        contactDao.save(contact);

        return contact;
    }

    @Override
    public Contact saveContact(AddContactValidation addContactValidation) {

        Contact contact = new Contact();
        contact.setEmail(addContactValidation.getEmail());
        contact.setFirstName(addContactValidation.getFirstName());
        contact.setLastName(addContactValidation.getLastName());
        if(!addContactValidation.getContactGroup().isEmpty() && !addContactValidation.getContactGroup().isBlank()){
            contact.setContactGroup(ContactGroup.valueOf(addContactValidation.getContactGroup()));
        }

        if(!addContactValidation.getContactImage().isEmpty() && !addContactValidation.getContactImage().isBlank()){
            contact.setContactImage(addContactValidation.getContactImage());
        }

        contact.setPhoneNumber(addContactValidation.getPhoneNumber());
        contact.setPhysicalAddress(addContactValidation.getPhysicalAddress());

        return contact;

    }

    @Override
    public Contact findById(int id) {
        return contactDao.findById(id);
    }


    @Override
    public List<Contact> allContact() {
        return List.of();
    }

    @Override
    public void deleteById(int id) {
        contactDao.deleteById(id);
    }


    @Override
    public void update(UpdateContactValidation updateContactValidation) {
        Contact contact = contactDao.findById(updateContactValidation.getId());

        contact.setLastName(updateContactValidation.getLastName());
        contact.setFirstName(updateContactValidation.getFirstName());
        contact.setContactImage(updateContactValidation.getContactImage());
        contact.setEmail(updateContactValidation.getEmail());
        contact.setPhysicalAddress(updateContactValidation.getPhysicalAddress());
        contact.setPhoneNumber(updateContactValidation.getPhoneNumber());
        contact.setContactGroup(ContactGroup.valueOf(updateContactValidation.getContactGroup()));

        contactDao.update(contact);
    }

    @Override
    public List<Contact> search(String searchText) {
        return contactDao.search(searchText);
    }

    @Override
    public List<Contact> filterByGroup(String filterText) {
        return contactDao.filterByGroup(ContactGroup.valueOf(filterText.toUpperCase()));
    }

    @Override
    @Transactional
    public void deleteContacts(List<Long> ids) {
        contactRepository.deleteByIdIn(ids);
    }
}
