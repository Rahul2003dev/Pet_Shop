package org.example.petshopcg.controller;

import org.example.petshopcg.dto.VaccinationDto;
import org.example.petshopcg.entity.Vaccination;
import org.example.petshopcg.mapper.VaccinationMapper;
import org.example.petshopcg.repository.VaccinationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/vaccinations")
public class VaccinationController {

    @Autowired
    private VaccinationRepository vaccinationRepository;

    @Autowired
    private VaccinationMapper vaccinationMapper;

    // Utility method for error response
    private Map<String, Object> errorResponse(String message) {
        Map<String, Object> error = new HashMap<>();
        error.put("timestamp", LocalDate.now());
        error.put("message", message);
        return error;
    }

    // Get all vaccinations
    @GetMapping
    public ResponseEntity<?> getAllVaccinations() {
        try {
            List<VaccinationDto> list = vaccinationRepository.findAll()
                    .stream()
                    .map(vaccinationMapper::toDto)
                    .collect(Collectors.toList());

            // Return 404 if no vaccinations found
            if (list.isEmpty()) {
                return ResponseEntity.status(404).body(errorResponse("No vaccinations found."));
            }

            // Return the list of vaccinations with 200 OK
            return ResponseEntity.ok(list);
        } catch (Exception e) {
            // Return 500 Internal Server Error on exception
            return ResponseEntity.internalServerError().body(errorResponse("Failed to fetch vaccinations."));
        }
    }

    // Get vaccination by ID
    @GetMapping("/{vaccination_id}")
    public ResponseEntity<?> getVaccinationById(@PathVariable("vaccination_id") Integer id) {
        try {
            Optional<Vaccination> vaccinationOpt = vaccinationRepository.findById(id);

            if (vaccinationOpt.isPresent()) {
                VaccinationDto dto = vaccinationMapper.toDto(vaccinationOpt.get());
                return ResponseEntity.ok(dto);
            } else {
                // Return 404 if vaccination with given ID does not exist
                return ResponseEntity.status(404).body(errorResponse("Vaccination not found with ID: " + id));
            }
        } catch (Exception e) {
            // Return 500 Internal Server Error on exception
            return ResponseEntity.internalServerError().body(errorResponse("Error fetching vaccination by ID."));
        }
    }

    // Get available vaccinations
    @GetMapping("/available")
    public ResponseEntity<?> getAvailableVaccinations() {
        try {
            List<VaccinationDto> list = vaccinationRepository.findByAvailableTrue()
                    .stream()
                    .map(vaccinationMapper::toDto)
                    .collect(Collectors.toList());

            // Return 404 if no available vaccinations found
            if (list.isEmpty()) {
                return ResponseEntity.status(404).body(errorResponse("No available vaccinations found."));
            }

            // Return available vaccinations with 200 OK
            return ResponseEntity.ok(list);
        } catch (Exception e) {
            // Return 500 Internal Server Error on exception
            return ResponseEntity.internalServerError().body(errorResponse("Failed to fetch available vaccinations."));
        }
    }

    // Get unavailable vaccinations
    @GetMapping("/unavailable")
    public ResponseEntity<?> getUnavailableVaccinations() {
        try {
            List<VaccinationDto> list = vaccinationRepository.findByAvailableFalse()
                    .stream()
                    .map(vaccinationMapper::toDto)
                    .collect(Collectors.toList());

            // Return 404 if no unavailable vaccinations found
            if (list.isEmpty()) {
                return ResponseEntity.status(404).body(errorResponse("No unavailable vaccinations found."));
            }

            // Return unavailable vaccinations with 200 OK
            return ResponseEntity.ok(list);
        } catch (Exception e) {
            // Return 500 Internal Server Error on exception
            return ResponseEntity.internalServerError().body(errorResponse("Failed to fetch unavailable vaccinations."));
        }
    }

    // Add new vaccination
    @PostMapping("/add")
    public ResponseEntity<?> addVaccination(@RequestBody VaccinationDto dto) {
        try {
            Vaccination saved = vaccinationRepository.save(vaccinationMapper.toEntity(dto));
            return ResponseEntity.ok(vaccinationMapper.toDto(saved));
        } catch (Exception e) {
            // Return 500 Internal Server Error on failure
            return ResponseEntity.internalServerError().body(errorResponse("Error adding vaccination."));
        }
    }

    // Update existing vaccination
    @PutMapping("/update/{vaccination_id}")
    public ResponseEntity<?> updateVaccination(@PathVariable("vaccination_id") Integer id,
                                               @RequestBody VaccinationDto dto) {
        try {
            Optional<Vaccination> existingOpt = vaccinationRepository.findById(id);
            if (existingOpt.isEmpty()) {
                // Return 404 if vaccination to update does not exist
                return ResponseEntity.status(404).body(errorResponse("Vaccination not found with ID: " + id));
            }

            Vaccination vaccination = vaccinationMapper.toEntity(dto);
            vaccination.setId(id);  // Preserve the original ID
            Vaccination updated = vaccinationRepository.save(vaccination);

            // Return updated vaccination with 200 OK
            return ResponseEntity.ok(vaccinationMapper.toDto(updated));
        } catch (Exception e) {
            // Return 500 Internal Server Error on failure
            return ResponseEntity.internalServerError().body(errorResponse("Error updating vaccination."));
        }
    }
}
