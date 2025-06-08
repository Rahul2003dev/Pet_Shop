package org.example.petshopcg.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TransactionHistoryResponse {
    private String timeStamp;
    private Object message;
}
