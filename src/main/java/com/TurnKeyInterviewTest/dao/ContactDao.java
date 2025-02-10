package com.TurnKeyInterviewTest.dao;

import com.TurnKeyInterviewTest.contactmanager.ContactGroup;
import com.TurnKeyInterviewTest.entity.Contact;

import java.util.List;

public interface ContactDao {

    Contact findById(int id);

    void save(Contact contact);

    void deleteById(int id);

    List<Contact> allContact();

    void update(Contact contact);

    List<Contact> search(String searchText);

    List<Contact> filterByGroup(ContactGroup filterText);
}
