package org.example.petshopcg.dto;

import lombok.Data;

@Data
public class customeraddressDto {
    private Integer id;
    private String email;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private AddressDto address;
}
