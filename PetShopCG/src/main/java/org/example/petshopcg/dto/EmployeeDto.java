package org.example.petshopcg.dto;

import jakarta.validation.constraints.Size;
import lombok.Value;
import org.example.petshopcg.entity.Employee;

import java.io.Serializable;
import java.time.LocalDate;

@Value // Makes the class immutable and generates getters, toString, equals, and hashCode
public class EmployeeDto implements Serializable {

    Integer id; // Unique ID of the employee

    @Size(max = 255) // Limits the length of first name to 255 characters
    String firstName; // First name of the employee

    @Size(max = 255) // Limits the length of last name to 255 characters
    String lastName; // Last name of the employee

    @Size(max = 255) // Limits the length of position to 255 characters
    String position; // Job position/title of the employee

    LocalDate hireDate; // Date the employee was hired

    @Size(max = 255) // Limits the length of phone number to 255 characters
    String phoneNumber; // Contact number of the employee

    @Size(max = 255) // Limits the length of email to 255 characters
    String email; // Email address of the employee
}
