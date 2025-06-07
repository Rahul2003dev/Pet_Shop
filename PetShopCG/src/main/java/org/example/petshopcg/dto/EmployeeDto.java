package org.example.petshopcg.dto;

import jakarta.validation.constraints.Size;
import lombok.Value;
import org.example.petshopcg.entity.Employee;

import java.io.Serializable;
import java.time.LocalDate;

@Value
public class EmployeeDto implements Serializable {
    Integer id;
    @Size(max = 255)
    String firstName;
    @Size(max = 255)
    String lastName;
    @Size(max = 255)
    String position;
    LocalDate hireDate;
    @Size(max = 255)
    String phoneNumber;
    @Size(max = 255)
    String email;
}