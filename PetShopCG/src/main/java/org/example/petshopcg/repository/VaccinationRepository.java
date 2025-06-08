package org.example.petshopcg.repository;

import org.example.petshopcg.entity.Vaccination;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VaccinationRepository extends JpaRepository<Vaccination, Integer> {

    // Find all vaccinations where available is true
    List<Vaccination> findByAvailableTrue();

    // Find all vaccinations where available is false
    List<Vaccination> findByAvailableFalse();
}
