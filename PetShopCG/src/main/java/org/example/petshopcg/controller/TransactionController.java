package org.example.petshopcg.controller;

import org.example.petshopcg.dto.TransactionDto;
import org.example.petshopcg.dto.TransactionHistoryResponse;
import org.example.petshopcg.entity.Transaction;
import org.example.petshopcg.mapper.TransactionMapper;
import org.example.petshopcg.repository.TransactionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/transaction_history")
public class TransactionController {

    @Autowired
    private TransactionRepo transactionRepo;

    // GET /transactions → get all transactions
    @GetMapping
    public ResponseEntity<TransactionHistoryResponse> getAllTransactions() {
        List<TransactionDto> dtos = transactionRepo.findAll().stream()
                .map(TransactionMapper::toDto)
                .collect(Collectors.toList());

        return ResponseEntity.ok(new TransactionHistoryResponse(LocalDate.now().toString(), dtos));
    }

    // GET /transactions/{transaction_id}
    @GetMapping("/{transaction_id}")
    public ResponseEntity<TransactionHistoryResponse> getTransactionById(@PathVariable Integer transaction_id) {
        Optional<Transaction> optional = transactionRepo.findById(transaction_id);

        if (optional.isPresent()) {
            TransactionDto dto = TransactionMapper.toDto(optional.get());
            return ResponseEntity.ok(new TransactionHistoryResponse(LocalDate.now().toString(), dto));
        } else {
            return ResponseEntity.badRequest()
                    .body(new TransactionHistoryResponse(LocalDate.now().toString(), "Validation failed"));
        }
    }

    // GET /transactions/by_customer/{customer_id}
    @GetMapping("/by_customer/{customer_id}")
    public ResponseEntity<TransactionHistoryResponse> getTransactionsByCustomerId(@PathVariable Integer customer_id) {
        List<TransactionDto> dtos = transactionRepo.findAll().stream()
                .filter(t -> t.getCustomer() != null && t.getCustomer().getId().equals(customer_id))
                .map(TransactionMapper::toDto)
                .collect(Collectors.toList());

        return ResponseEntity.ok(new TransactionHistoryResponse(LocalDate.now().toString(), dtos));
    }

    // GET /transactions/successful
    @GetMapping("/Success")
    public ResponseEntity<TransactionHistoryResponse> getSuccessfulTransactions() {
        List<TransactionDto> dtos = transactionRepo.findAll().stream()
                .filter(t -> "SUCCESS".equalsIgnoreCase(t.getTransactionStatus()))
                .map(TransactionMapper::toDto)
                .collect(Collectors.toList());

        return ResponseEntity.ok(new TransactionHistoryResponse(LocalDate.now().toString(), dtos));
    }

    // GET /transactions/failed
    @GetMapping("/Failed")
    public ResponseEntity<TransactionHistoryResponse> getFailedTransactions() {
        List<TransactionDto> dtos = transactionRepo.findAll().stream()
                .filter(t -> "FAILED".equalsIgnoreCase(t.getTransactionStatus()))
                .map(TransactionMapper::toDto)
                .collect(Collectors.toList());

        return ResponseEntity.ok(new TransactionHistoryResponse(LocalDate.now().toString(), dtos));
    }

    // POST /transactions/add
    @PostMapping("/add")
    public ResponseEntity<TransactionHistoryResponse> addTransaction(@RequestBody TransactionDto transactionDto) {
        try {
            Transaction transaction = TransactionMapper.toEntity(transactionDto);
            Transaction savedTransaction = transactionRepo.save(transaction);
            TransactionDto savedDto = TransactionMapper.toDto(savedTransaction);

            return ResponseEntity.ok(new TransactionHistoryResponse(LocalDate.now().toString(), savedDto));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(new TransactionHistoryResponse(LocalDate.now().toString(), "Validation failed"));
        }
    }
}
