package org.example.petshopcg.mapper;

import org.example.petshopcg.dto.TransactionDto;
import org.example.petshopcg.entity.Customer;
import org.example.petshopcg.entity.Pet;
import org.example.petshopcg.entity.Transaction;

public class TransactionMapper {

    public static TransactionDto toDto(Transaction transaction) {
        if (transaction == null) {
            return null;
        }

        Integer customerId = transaction.getCustomer() != null ? transaction.getCustomer().getId() : null;
        Integer petId = transaction.getPet() != null ? transaction.getPet().getId() : null;

        return new TransactionDto(
                transaction.getId(),
                transaction.getTransactionDate(),
                transaction.getAmount(),
                transaction.getTransactionStatus(),
                customerId,
                petId
        );
    }

    public static Transaction toEntity(TransactionDto transactionDto) {
        if (transactionDto == null) {
            return null;
        }

        Transaction transaction = new Transaction();
        transaction.setId(transactionDto.getId());
        transaction.setTransactionDate(transactionDto.getTransactionDate());
        transaction.setAmount(transactionDto.getAmount());
        transaction.setTransactionStatus(transactionDto.getTransactionStatus());

        // Set Customer (only with ID)
        if (transactionDto.getCustomerId() != null) {
            Customer customer = new Customer();
            customer.setId(transactionDto.getCustomerId());
            transaction.setCustomer(customer);
        } else {
            transaction.setCustomer(null);
        }

        // Set Pet (only with ID)
        if (transactionDto.getPetId() != null) {
            Pet pet = new Pet();
            pet.setId(transactionDto.getPetId());
            transaction.setPet(pet);
        } else {
            transaction.setPet(null);
        }

        return transaction;
    }
}
