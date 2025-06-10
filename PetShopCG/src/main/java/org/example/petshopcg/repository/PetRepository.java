package org.example.petshopcg.repository;

import org.example.petshopcg.dto.PetCountByBreedDto;
import org.example.petshopcg.entity.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PetRepository extends JpaRepository<Pet, Integer> {
    @Query("SELECT p FROM Pet p " +
            "JOIN FETCH p.category c " +
            "WHERE c.name IS NOT NULL " +
            "ORDER BY c.name, p.name")
    List<Pet> findAllWithCategorySorted();

    @Query("SELECT new org.example.petshopcg.dto.PetCountByBreedDto(p.breed, COUNT(p)) " +
            "FROM Pet p " +
            "WHERE p.breed IS NOT NULL " +
            "GROUP BY p.breed " +
            "ORDER BY p.breed")
    List<PetCountByBreedDto> countPetsByBreed();

}
