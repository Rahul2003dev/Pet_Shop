package org.example.petshopcg.dto;

import lombok.Data;

import java.util.List;
@Data
public class CustomerFullDto {
    private Integer id;
    private String email;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private AddressDto address;
    private List<TransactionDto> transactions;
}
