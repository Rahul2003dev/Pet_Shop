package org.example.petshopcg.dto;

import lombok.Data;
import java.util.List;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class CustomerSummaryDto {
    private Integer id;
    private String email;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private AddressDto address;
    private List<TransactionDto> transactions;

    @Data
    public static class AddressDto {
        private Integer id;
        private String street;
        private String city;
        private String state;
        private String zipCode;
    }

    @Data
    public static class TransactionDto {
        private Integer id;
        private LocalDate transactionDate;
        private BigDecimal amount;
        private String transactionStatus;
    }
}
