package org.example.petshopcg.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.petshopcg.dto.EmployeeDto;
import org.example.petshopcg.entity.Employee;
import org.example.petshopcg.mapper.EmployeeMapper;
import org.example.petshopcg.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(EmployeeController.class) // Test only the EmployeeController layer
class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc; // Used to perform HTTP requests in tests

    @MockBean
    private EmployeeRepository employeeRepo; // Mocked repository

    @MockBean
    private EmployeeMapper employeeMapper; // Mocked mapper for DTO and entity

    @Autowired
    private ObjectMapper objectMapper; // Used to convert objects to/from JSON

    private Employee employee; // Sample employee entity
    private EmployeeDto employeeDto; // Sample employee DTO

    @BeforeEach
    void setUp() {
        // Initialize employee entity
        employee = new Employee();
        employee.setId(1);
        employee.setFirstName("John");
        employee.setLastName("Doe");
        employee.setPosition("Manager");
        employee.setHireDate(LocalDate.now());
        employee.setPhoneNumber("123456789");
        employee.setEmail("john.doe@example.com");

        // Initialize corresponding employee DTO
        employeeDto = new EmployeeDto(
                1,
                "John",
                "Doe",
                "Manager",
                LocalDate.now(),
                "123456789",
                "john.doe@example.com"
        );
    }

    @Test
    void getAllEmployees() throws Exception {
        when(employeeRepo.findAll()).thenReturn(List.of(employee)); // Mock repository response
        when(employeeMapper.toDto(employee)).thenReturn(employeeDto); // Mock mapper conversion

        // Perform GET request and check response
        mockMvc.perform(get("/api/v1/employees"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].firstName").value("John"));
    }

    @Test
    void getEmployeeById() throws Exception {
        when(employeeRepo.findById(1)).thenReturn(Optional.of(employee));
        when(employeeMapper.toDto(employee)).thenReturn(employeeDto);

        // Perform GET by ID and validate response
        mockMvc.perform(get("/api/v1/employees/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("John"));
    }

    @Test
    void getEmployeesByName() throws Exception {
        when(employeeRepo.findByFirstName("John")).thenReturn(List.of(employee));
        when(employeeMapper.toDto(employee)).thenReturn(employeeDto);

        // Perform GET by name and check firstName field
        mockMvc.perform(get("/api/v1/employees/name/John"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].firstName").value("John"));
    }

    @Test
    void getEmployeesByPosition() throws Exception {
        when(employeeRepo.findByPosition("Manager")).thenReturn(List.of(employee));
        when(employeeMapper.toDto(employee)).thenReturn(employeeDto);

        // Perform GET by position and verify position field
        mockMvc.perform(get("/api/v1/employees/position/Manager"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].position").value("Manager"));
    }

    @Test
    void addEmployee() throws Exception {
        when(employeeMapper.toEntity(employeeDto)).thenReturn(employee);
        when(employeeRepo.save(employee)).thenReturn(employee);
        when(employeeMapper.toDto(employee)).thenReturn(employeeDto);

        // Perform POST to add a new employee and check response
        mockMvc.perform(post("/api/v1/employees/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(employeeDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("John"));
    }

    @Test
    void updateEmployee() throws Exception {
        when(employeeRepo.findById(1)).thenReturn(Optional.of(employee));
        when(employeeMapper.toEntity(employeeDto)).thenReturn(employee);
        when(employeeRepo.save(any(Employee.class))).thenReturn(employee);
        when(employeeMapper.toDto(employee)).thenReturn(employeeDto);

        // Perform PUT to update an existing employee and check response
        mockMvc.perform(put("/api/v1/employees/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(employeeDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("John"));
    }
}
