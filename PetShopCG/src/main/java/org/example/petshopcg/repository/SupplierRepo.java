package org.example.petshopcg.repository;

import org.example.petshopcg.entity.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SupplierRepo extends JpaRepository<Supplier, Integer> {

    // Optional custom query methods

    // Find supplier by name

    List<Supplier> findByNameIgnoreCase(String name);


    // Find suppliers by contact person
    List<Supplier> findByContactPerson(String contactPerson);

    // Find suppliers by email
    List<Supplier> findByEmail(String email);
}
