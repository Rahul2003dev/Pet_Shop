package org.example.petshopcg.repository;

import org.example.petshopcg.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CustomerRepo extends JpaRepository<Customer,Integer> {
    List<Customer> findByFirstName(String firstName);
}
