package com.TurnKeyInterviewTest.repository;

import com.TurnKeyInterviewTest.entity.Contact;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Long> {

    Contact findByfirstName(String firstName);

    Optional<Contact> findByEmail(String email);

    Boolean existsByEmail(String email);

    List<Contact> findAll(Sort sort);

    void deleteByIdIn(List<Long> ids);

}
