package org.example.petshopcg.repository;

import org.example.petshopcg.entity.PetCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PetCategoryRepository extends JpaRepository<PetCategory, Integer> {
    Optional<PetCategory> findByName(String name);
}
