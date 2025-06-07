package org.example.petshopcg.repository;

import org.example.petshopcg.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    // Finds employees whose first name contains the given string (case-insensitive)
    List<Employee> findByFirstNameContainingIgnoreCase(String firstName);

    // Finds employees based on their job position/title (case-insensitive)
    List<Employee> findByPositionContainingIgnoreCase(String position);
}
