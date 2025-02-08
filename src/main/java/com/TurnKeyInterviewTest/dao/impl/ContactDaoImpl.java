package com.TurnKeyInterviewTest.dao.impl;

import com.TurnKeyInterviewTest.dao.ContactDao;
import com.TurnKeyInterviewTest.entity.Contact;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ContactDaoImpl implements ContactDao {

    private EntityManager entityManager;

    public ContactDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Contact findById(int id) {
        TypedQuery<Contact> query = entityManager.createQuery("from Contact where id = :id", Contact.class);
        query.setParameter("id", id);
        Contact contact = null;

        try{
            contact = query.getSingleResult();
        }catch(Exception e){
            contact = null;
        }
        return contact;
    }



    @Override
    @Transactional
    public void save(Contact contact) {
        entityManager.merge(contact);
    }

    @Override
    @Transactional
    public void deleteById(int id) {
        Contact contact = entityManager.find(Contact.class, id);
        entityManager.remove(contact);
    }

    @Override
    public List<Contact> allContact() {
        return List.of();
    }



    @Override
    @Transactional
    public void update(Contact contact) {
        entityManager.merge(contact);
    }

    @Override
    public List<Contact> search(String searchText) {

        String queryText = "SELECT c FROM Contact c WHERE " +
                "LOWER(c.firstName) LIKE LOWER(CONCAT('%', :searchText, '%')) OR " +
                "LOWER(c.lastName) LIKE LOWER(CONCAT('%', :searchText, '%')) OR " +
                "LOWER(c.email) LIKE LOWER(CONCAT('%', :searchText, '%')) OR " +
                "LOWER(c.phoneNumber) LIKE LOWER(CONCAT('%', :searchText, '%'))";

        TypedQuery<Contact> query = entityManager.createQuery(queryText, Contact.class);
        query.setParameter("searchText", searchText);

       List<Contact> contact = null;
        try{
            contact = query.getResultList();
        }catch(Exception e){
            contact = null;
        }
        return contact;
    }
}
