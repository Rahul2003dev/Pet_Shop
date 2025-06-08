package org.example.petshopcg.repository;

import org.example.petshopcg.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    List<Employee> findByFirstNameIgnoreCase(String firstName);

    List<Employee> findByPositionContainingIgnoreCase(String position);
}
