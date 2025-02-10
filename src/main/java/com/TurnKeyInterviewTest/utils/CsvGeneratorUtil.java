package com.TurnKeyInterviewTest.utils;


import com.TurnKeyInterviewTest.entity.Contact;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CsvGeneratorUtil {

    private static final String CSV_HEADER = "ID,firstName,lastName,phoneNumber,email,contactGroup,physicalAddress\n";

    public String generateCsv(List<Contact> contacts) {
        StringBuilder csvContent = new StringBuilder();
        csvContent.append(CSV_HEADER);

        for (Contact contact : contacts) {
            csvContent.append(contact.getId()).append(",")
                    .append(contact.getFirstName()).append(",")
                    .append(contact.getLastName()).append(",")
                    .append(contact.getPhoneNumber()).append(",")
                    .append(contact.getEmail()).append(",")
                    .append(contact.getContactGroup()).append(",")
                    .append(contact.getPhysicalAddress()).append("\n");

        }

        return csvContent.toString();
    }

}
