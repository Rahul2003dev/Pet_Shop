package org.example.petshopcg.controller;

import org.example.petshopcg.dto.TransactionDto;
import org.example.petshopcg.entity.Transaction;
import org.example.petshopcg.mapper.TransactionMapper;
import org.example.petshopcg.repository.TransactionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
//manjot
@RestController
@RequestMapping("/api/v1/transaction_history")
public class TransactionController {

    @Autowired
    private TransactionRepo transactionRepo;

    @GetMapping
    public ResponseEntity<?> getAllTransactions() {
        List<TransactionDto> dtos = transactionRepo.findAll().stream()
                .map(TransactionMapper::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{transaction_id}")
    public ResponseEntity<?> getTransactionById(@PathVariable Integer transaction_id) {
        Optional<Transaction> optional = transactionRepo.findById(transaction_id);

        if (optional.isPresent()) {
            TransactionDto dto = TransactionMapper.toDto(optional.get());
            return ResponseEntity.ok(dto);
        } else {
            Map<String, Object> errorResponse = new LinkedHashMap<>();
            errorResponse.put("timestamp", LocalDate.now().toString());
            errorResponse.put("message", "Validation failed");

            return ResponseEntity.status(404).body(errorResponse);
        }
    }

    @GetMapping("/by_customer/{customer_id}")
    public ResponseEntity<?> getTransactionsByCustomerId(@PathVariable Integer customer_id) {
        List<TransactionDto> dtos = transactionRepo.findAll().stream()
                .filter(t -> t.getCustomer() != null && t.getCustomer().getId().equals(customer_id))
                .map(TransactionMapper::toDto)
                .collect(Collectors.toList());

        if (!dtos.isEmpty()) {
            return ResponseEntity.ok(dtos);
        } else {
            Map<String, Object> errorResponse = new LinkedHashMap<>();
            errorResponse.put("timestamp", LocalDate.now().toString());
            errorResponse.put("message", "Validation failed");

            return ResponseEntity.status(404).body(errorResponse);
        }
    }


    @GetMapping("/successful")
    public ResponseEntity<?> getSuccessfulTransactions() {
        List<TransactionDto> dtos = transactionRepo.findAll().stream()
                .filter(t -> "SUCCESS".equalsIgnoreCase(t.getTransactionStatus()))
                .map(TransactionMapper::toDto)
                .collect(Collectors.toList());

        return ResponseEntity.ok(dtos);
    }


    @GetMapping("/failed")
    public ResponseEntity<?> getFailedTransactions() {
        List<TransactionDto> dtos = transactionRepo.findAll().stream()
                .filter(t -> "FAILED".equalsIgnoreCase(t.getTransactionStatus()))
                .map(TransactionMapper::toDto)
                .collect(Collectors.toList());

        return ResponseEntity.ok(dtos);
    }


    @PostMapping("/add")
    public ResponseEntity<?> addTransaction(@RequestBody TransactionDto transactionDto) {
        try {

            boolean exists = transactionRepo.existsById(transactionDto.getId());

            if (exists) {

                Map<String, Object> existsResponse = new LinkedHashMap<>();
                existsResponse.put("timeStamp", LocalDate.now().toString());
                existsResponse.put("message", "Transaction already exists");

                return ResponseEntity.badRequest().body(existsResponse);
            }


            Transaction transaction = TransactionMapper.toEntity(transactionDto);
            Transaction savedTransaction = transactionRepo.save(transaction);


            Map<String, Object> successResponse = new LinkedHashMap<>();
            successResponse.put("timeStamp", LocalDate.now().toString());
            successResponse.put("message", "Transaction added Successfully.");

            return ResponseEntity.ok(successResponse);

        } catch (Exception e) {
            Map<String, Object> errorResponse = new LinkedHashMap<>();
            errorResponse.put("timeStamp", LocalDate.now().toString());
            errorResponse.put("message", "Validation failed");

            return ResponseEntity.badRequest().body(errorResponse);
        }
    }


}
