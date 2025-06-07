package org.example.petshopcg.repository;

import org.example.petshopcg.entity.GroomingService;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GroomingServiceRepository extends JpaRepository<GroomingService, Integer> {
    List<GroomingService> findAllByAvailable(boolean available);
}
