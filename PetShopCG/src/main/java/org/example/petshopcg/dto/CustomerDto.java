package org.example.petshopcg.dto;

import jakarta.validation.constraints.Size;
import lombok.Value;
import org.example.petshopcg.entity.Customer;

import java.io.Serializable;

@Value
public class CustomerDto implements Serializable {
    Integer id;
    @Size(max = 255)
    String email;
    @Size(max = 255)
    String firstName;
    @Size(max = 255)
    String lastName;
    @Size(max = 255)
    String phoneNumber;
}