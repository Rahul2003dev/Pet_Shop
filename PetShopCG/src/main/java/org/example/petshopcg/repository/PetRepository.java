package org.example.petshopcg.repository;

import org.example.petshopcg.entity.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PetRepository extends JpaRepository<Pet, Integer> {
    // You can add custom query methods here if needed, e.g.:
    // List<Pet> findByBreed(String breed);
}
