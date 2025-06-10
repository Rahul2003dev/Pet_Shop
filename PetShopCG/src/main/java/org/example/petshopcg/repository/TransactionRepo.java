package org.example.petshopcg.repository;

import org.example.petshopcg.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepo extends JpaRepository<Transaction, Integer> {
    List<Transaction> findByCustomer_Id(Integer customerId);
}
