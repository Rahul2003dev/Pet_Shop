package org.example.petshopcg.repository;

import org.example.petshopcg.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for Employee entity.
 * Extends JpaRepository to provide basic CRUD operations and custom query methods.
 */
@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    // Finds a list of employees with the given first name.
    List<Employee> findByFirstName(String firstName);

    // Finds a list of employees with the given position
    List<Employee> findByPosition(String position);
}
