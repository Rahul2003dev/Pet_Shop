package org.example.petshopcg.repository;

import org.example.petshopcg.entity.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PetRepository extends JpaRepository<Pet, Integer> {
    List<Pet> findByCategory_NameIgnoreCase(String name);
}
