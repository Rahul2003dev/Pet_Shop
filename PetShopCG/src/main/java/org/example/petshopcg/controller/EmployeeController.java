package org.example.petshopcg.controller;

import org.example.petshopcg.dto.EmployeeDto;
import org.example.petshopcg.entity.Employee;
import org.example.petshopcg.mapper.EmployeeMapper;
import org.example.petshopcg.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/employees")
public class EmployeeController {

    @Autowired
    private EmployeeRepository employeeRepo;

    @Autowired
    private EmployeeMapper employeeMapper;

    // Utility method for error response
    private Map<String, Object> errorResponse(String message) {
        Map<String, Object> error = new HashMap<>();
        error.put("timestamp", LocalDate.now());
        error.put("message", message);
        return error;
    }

    // Get all employees
    @GetMapping
    public ResponseEntity<?> getAllEmployees() {
        try {
            List<EmployeeDto> list = employeeRepo.findAll()
                    .stream()
                    .map(employeeMapper::toDto)
                    .collect(Collectors.toList());

            // Return 404 if no employees found
            if (list.isEmpty()) {
                return ResponseEntity.status(404).body(errorResponse("No employees found."));
            }

            // Return the list of employees with 200 OK
            return ResponseEntity.ok(list);
        } catch (Exception e) {
            // Return 500 Internal Server Error on exception
            return ResponseEntity.internalServerError().body(errorResponse("Failed to fetch employees."));
        }
    }

    // Get employee by ID
    @GetMapping("/{employee_id}")
    public ResponseEntity<?> getEmployeeById(@PathVariable("employee_id") Integer id) {
        try {
            Optional<Employee> employeeOpt = employeeRepo.findById(id);

            if (employeeOpt.isPresent()) {
                EmployeeDto dto = employeeMapper.toDto(employeeOpt.get());
                return ResponseEntity.ok(dto);
            } else {
                // Return 404 if employee with given ID does not exist
                return ResponseEntity.status(404).body(errorResponse("Employee not found with ID: " + id));
            }
        } catch (Exception e) {
            // Return 500 Internal Server Error on exception
            return ResponseEntity.internalServerError().body(errorResponse("Error fetching employee by ID."));
        }
    }

    // Search employees by first name (exact match, case-insensitive)
    @GetMapping("/name/{name}")
    public ResponseEntity<?> getEmployeesByName(@PathVariable String name) {
        try {
            List<EmployeeDto> list = employeeRepo.findByFirstName(name)
                    .stream()
                    .map(employeeMapper::toDto)
                    .collect(Collectors.toList());

            // Return 404 if no employees match the search criteria
            if (list.isEmpty()) {
                return ResponseEntity.status(404).body(errorResponse("No employees found with name: " + name));
            }

            // Return matched employees
            return ResponseEntity.ok(list);
        } catch (Exception e) {
            // Return 500 Internal Server Error on exception
            return ResponseEntity.internalServerError().body(errorResponse("Error searching employees by name."));
        }
    }

    // Filter employees by position (case-insensitive substring match)
    @GetMapping("/position/{position_name}")
    public ResponseEntity<?> getEmployeesByPosition(@PathVariable("position_name") String position) {
        try {
            List<EmployeeDto> list = employeeRepo.findByPosition(position)
                    .stream()
                    .map(employeeMapper::toDto)
                    .collect(Collectors.toList());

            // Return 404 if no employees found for the given position
            if (list.isEmpty()) {
                return ResponseEntity.status(404).body(errorResponse("No employees found with position: " + position));
            }

            // Return matched employees
            return ResponseEntity.ok(list);
        } catch (Exception e) {
            // Return 500 Internal Server Error on exception
            return ResponseEntity.internalServerError().body(errorResponse("Error filtering employees by position."));
        }
    }

    // Add new employee
    @PostMapping("/add")
    public ResponseEntity<?> addEmployee(@RequestBody EmployeeDto dto) {
        try {
            Employee saved = employeeRepo.save(employeeMapper.toEntity(dto));
            return ResponseEntity.ok(employeeMapper.toDto(saved));
        } catch (Exception e) {
            // Return 500 Internal Server Error on failure
            return ResponseEntity.internalServerError().body(errorResponse("Error adding employee."));
        }
    }

    // Update existing employee
    @PutMapping("/update/{employee_id}")
    public ResponseEntity<?> updateEmployee(@PathVariable("employee_id") Integer id,
                                            @RequestBody EmployeeDto dto) {
        try {
            Optional<Employee> existingOpt = employeeRepo.findById(id);
            if (existingOpt.isEmpty()) {
                // Return 404 if employee to update does not exist
                return ResponseEntity.status(404).body(errorResponse("Employee not found with ID: " + id));
            }

            Employee employee = employeeMapper.toEntity(dto);
            employee.setId(id);  // Preserve the original ID
            Employee updated = employeeRepo.save(employee);

            return ResponseEntity.ok(employeeMapper.toDto(updated));
        } catch (Exception e) {
            // Return 500 Internal Server Error on failure
            return ResponseEntity.internalServerError().body(errorResponse("Error updating employee."));
        }
    }
}
