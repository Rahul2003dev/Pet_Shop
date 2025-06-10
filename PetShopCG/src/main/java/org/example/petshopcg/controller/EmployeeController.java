package org.example.petshopcg.controller;

import org.example.petshopcg.dto.EmployeeDto;
import org.example.petshopcg.entity.Employee;
import org.example.petshopcg.mapper.EmployeeMapper;
import org.example.petshopcg.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/employees")
public class EmployeeController {

    @Autowired
    private EmployeeRepository employeeRepo;

    @Autowired
    private EmployeeMapper employeeMapper;

    // Fetches all employees from the database
    @GetMapping
    public List<EmployeeDto> getAllEmployees() {
        // Fetch all employee records from DB
        // Convert each Employee entity to DTO
        return employeeRepo.findAll()
                .stream()
                .map(employeeMapper::toDto)
                .collect(Collectors.toList());
    }

    // Returns a single employee based on their unique ID
    @GetMapping("/{employee_id}")
    public ResponseEntity<EmployeeDto> getEmployeeById(@PathVariable("employee_id") Integer id) {
        // Find employee by ID
        // If present, convert to DTO and return 200 OK
        // If not, return 404 Not Found
        return employeeRepo.findById(id)
                .map(employeeMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Searches employees by first name (case-insensitive)
    @GetMapping("/name/{name}")
    public List<EmployeeDto> getEmployeesByName(@PathVariable String name) {
        // Query employees by first name
        // Convert results to DTO list
        return employeeRepo.findByFirstName(name)
                .stream()
                .map(employeeMapper::toDto)
                .collect(Collectors.toList());
    }

    // Filters employees by their position title (case-insensitive)
    @GetMapping("/position/{position_name}")
    public List<EmployeeDto> getEmployeesByPosition(@PathVariable("position_name") String position) {
        // Filter employees based on position name
        // Map entities to DTOs for the response
        return employeeRepo.findByPosition(position)
                .stream()
                .map(employeeMapper::toDto)
                .collect(Collectors.toList());
    }

    // Adds a new employee to the system
    @PostMapping("/add")
    public EmployeeDto addEmployee(@RequestBody EmployeeDto dto) {
        // Convert input DTO to Employee entity
        // Save entity to DB and convert saved record back to DTO
        Employee saved = employeeRepo.save(employeeMapper.toEntity(dto));
        return employeeMapper.toDto(saved);
    }

    // Updates an existing employee's data by their ID
    @PutMapping("/update/{employee_id}")
    public ResponseEntity<EmployeeDto> updateEmployee(@PathVariable("employee_id") Integer id,
                                                      @RequestBody EmployeeDto dto) {
        // Check if employee with given ID exists
        return employeeRepo.findById(id).map(existing -> {
            // Map input DTO to entity and retain the same ID
            Employee employee = employeeMapper.toEntity(dto);
            employee.setId(id); // Make sure we update existing, not create new
            // Save updated entity and convert to DTO for response
            return ResponseEntity.ok(employeeMapper.toDto(employeeRepo.save(employee)));
        }).orElse(ResponseEntity.notFound().build()); // If not found, return 404
    }
}
