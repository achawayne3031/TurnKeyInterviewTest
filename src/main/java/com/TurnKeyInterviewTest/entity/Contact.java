package com.TurnKeyInterviewTest.entity;

import com.TurnKeyInterviewTest.contactmanager.ContactGroup;
import lombok.*;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(schema = "contact")
public class Contact {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;


    @Column(name = "contact_image")
    private String contactImage;

    @Column(name = "physical_address", nullable = false)
    private String physicalAddress;


    @Enumerated(EnumType.STRING)
    @Column(name = "contact_group", columnDefinition = "varchar(20) default 'OTHER'")
    private ContactGroup contactGroup = ContactGroup.OTHER;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private Instant createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Date updatedAt;

    @Override
    public String toString() {
        return "Contact{" +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", contactImage='" + contactImage + '\'' +
                ", physicalAddress='" + physicalAddress + '\'' +
                ", contactGroup=" + contactGroup +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
