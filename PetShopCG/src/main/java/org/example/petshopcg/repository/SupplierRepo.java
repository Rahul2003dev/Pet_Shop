package org.example.petshopcg.repository;

import org.example.petshopcg.entity.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SupplierRepo extends JpaRepository<Supplier, Integer> {

    // Find suppliers by name ignoring case
    List<Supplier> findByNameIgnoreCase(String name);

    // Find suppliers where address.city matches ignoring case
    List<Supplier> findByAddressCityIgnoreCase(String city);

    // Find suppliers where address.state matches ignoring case
    List<Supplier> findByAddressStateIgnoreCase(String state);

    @Query("SELECT s FROM Supplier s WHERE LOWER(s.address.city) = LOWER(:city)")
    List<Supplier> findSuppliersByCityIgnoreCase(@Param("city") String city);

}
