package com.TurnKeyInterviewTest.validation;


import jakarta.persistence.Column;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddContactValidation {

    private int id;

    @NotNull(message = "first name is required")
    @NotEmpty(message = "first name not be empty")
    private String firstName;

    @NotNull(message = "last name is required")
    @NotEmpty(message = "last name not be empty")
    private String lastName;

    @NotNull(message = "email is required")
    @NotEmpty(message = "email not be empty")
    @Email(message = "email: Invalid email address")
    private String email;

    @NotNull(message = "phone number is required")
    @NotEmpty(message = "phone number not be empty")
    @Size(min = 11, max = 11, message = "phone number must be 11 digits")
    private String phoneNumber;


    @NotNull(message = "physical address is required")
    @NotEmpty(message = "physical address not be empty")
    private String physicalAddress;


    private String contactGroup;


    private String contactImage;

    @Override
    public String toString() {
        return "AddContactValidation{" +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", physicalAddress='" + physicalAddress + '\'' +
                ", contactGroup='" + contactGroup + '\'' +
                ", contactImage='" + contactImage + '\'' +
                '}';
    }
}
