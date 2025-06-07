package org.example.petshopcg.repository;

import org.example.petshopcg.entity.PetCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PetCategoryRepository extends JpaRepository<PetCategory, Integer> {
}
