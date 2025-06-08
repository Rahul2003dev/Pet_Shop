package org.example.petshopcg.repository;

import org.example.petshopcg.entity.PetFood;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PetFoodRepo extends JpaRepository<PetFood, Integer> {

    // Search by name (partial match, case-insensitive)
    List<PetFood> findByNameContainingIgnoreCase(String name);

    // Filter by type (case-insensitive)
    List<PetFood> findByTypeIgnoreCase(String type);

    // Filter by brand (case-insensitive)
    List<PetFood> findByBrandIgnoreCase(String brand);
}
