package org.example.petshopcg.repository;

import org.example.petshopcg.entity.Pet;
import org.example.petshopcg.entity.PetCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PetCategoryRepository extends JpaRepository<PetCategory, Integer> {
//    List<Pet> findByCategory_CategoryId(Integer categoryId);
}
