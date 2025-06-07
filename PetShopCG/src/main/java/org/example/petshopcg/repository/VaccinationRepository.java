package org.example.petshopcg.repository;

import org.example.petshopcg.entity.Vaccination;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VaccinationRepository extends JpaRepository<Vaccination, Integer> {

    // Returns all vaccinations that are marked as available (true)
    List<Vaccination> findByAvailableTrue();

    // Returns all vaccinations that are marked as unavailable (false)
    List<Vaccination> findByAvailableFalse();
}
