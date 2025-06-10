package org.example.petshopcg.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.petshopcg.dto.VaccinationDto;
import org.example.petshopcg.entity.Vaccination;
import org.example.petshopcg.mapper.VaccinationMapper;
import org.example.petshopcg.repository.VaccinationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(VaccinationController.class) // Load only the web layer for VaccinationController
class VaccinationControllerTest {

    @Autowired
    private MockMvc mockMvc; // Used to perform mock HTTP requests

    @MockBean
    private VaccinationRepository vaccinationRepository; // Mocked repository to isolate controller logic

    @MockBean
    private VaccinationMapper vaccinationMapper; // Mocked mapper to simulate DTO conversion

    private Vaccination vaccination;
    private VaccinationDto vaccinationDto;

    private final ObjectMapper objectMapper = new ObjectMapper(); // For JSON serialization/deserialization

    @BeforeEach
    void setUp() {
        // Prepare a sample vaccination entity and its corresponding DTO for test cases
        vaccination = new Vaccination();
        vaccination.setId(1);
        vaccination.setName("Rabies Shot");
        vaccination.setDescription("Protects against rabies");
        vaccination.setPrice(BigDecimal.valueOf(29.99));
        vaccination.setAvailable(true);

        vaccinationDto = new VaccinationDto(1, "Rabies Shot", "Protects against rabies", BigDecimal.valueOf(29.99), true);
    }

    @Test
    void getAllVaccinations() throws Exception {
        // Simulate fetching all vaccinations from repository
        when(vaccinationRepository.findAll()).thenReturn(Arrays.asList(vaccination));
        when(vaccinationMapper.toDto(vaccination)).thenReturn(vaccinationDto);

        // Perform GET request and validate response
        mockMvc.perform(get("/api/v1/vaccinations"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Rabies Shot"));
    }

    @Test
    void getVaccinationById() throws Exception {
        // Simulate fetching a vaccination by ID
        when(vaccinationRepository.findById(1)).thenReturn(Optional.of(vaccination));
        when(vaccinationMapper.toDto(vaccination)).thenReturn(vaccinationDto);

        // Perform GET by ID request and validate response
        mockMvc.perform(get("/api/v1/vaccinations/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void getAvailableVaccinations() throws Exception {
        // Simulate fetching vaccinations that are available
        when(vaccinationRepository.findByAvailableTrue()).thenReturn(Arrays.asList(vaccination));
        when(vaccinationMapper.toDto(vaccination)).thenReturn(vaccinationDto);

        // Perform GET request for available vaccinations
        mockMvc.perform(get("/api/v1/vaccinations/available"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].available").value(true));
    }

    @Test
    void getUnavailableVaccinations() throws Exception {
        // Simulate unavailable vaccination case
        vaccination.setAvailable(false);
        vaccinationDto = new VaccinationDto(1, "Rabies Shot", "Protects against rabies", BigDecimal.valueOf(29.99), false);

        when(vaccinationRepository.findByAvailableFalse()).thenReturn(Arrays.asList(vaccination));
        when(vaccinationMapper.toDto(vaccination)).thenReturn(vaccinationDto);

        // Perform GET request for unavailable vaccinations
        mockMvc.perform(get("/api/v1/vaccinations/unavailable"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].available").value(false));
    }

    @Test
    void addVaccination() throws Exception {
        // Simulate adding a new vaccination
        when(vaccinationMapper.toEntity(vaccinationDto)).thenReturn(vaccination);
        when(vaccinationRepository.save(vaccination)).thenReturn(vaccination);
        when(vaccinationMapper.toDto(vaccination)).thenReturn(vaccinationDto);

        // Perform POST request to add new vaccination
        mockMvc.perform(post("/api/v1/vaccinations/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(vaccinationDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Rabies Shot"));
    }

    @Test
    void updateVaccination() throws Exception {
        // Simulate updating an existing vaccination
        when(vaccinationRepository.findById(1)).thenReturn(Optional.of(vaccination));
        when(vaccinationMapper.toEntity(vaccinationDto)).thenReturn(vaccination);
        when(vaccinationRepository.save(vaccination)).thenReturn(vaccination);
        when(vaccinationMapper.toDto(vaccination)).thenReturn(vaccinationDto);

        // Perform PUT request to update vaccination
        mockMvc.perform(put("/api/v1/vaccinations/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(vaccinationDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }
}
