package org.example.petshopcg.dto;

import lombok.Value;
import org.example.petshopcg.entity.Transaction;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * DTO for {@link Transaction}
 */
@Value
public class TransactionDto implements Serializable {
    Integer id;
    LocalDate transactionDate;
    BigDecimal amount;
    String transactionStatus;
    Integer customerId;
    Integer petId;
}