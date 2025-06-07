package org.example.petshopcg.controller;

import org.example.petshopcg.dto.VaccinationDto;
import org.example.petshopcg.entity.Vaccination;
import org.example.petshopcg.mapper.VaccinationMapper;
import org.example.petshopcg.repository.VaccinationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/vaccinations")
public class VaccinationController {

    @Autowired
    private VaccinationRepository vaccinationRepository;

    @Autowired
    private VaccinationMapper vaccinationMapper;

    // Get all vaccination records
    @GetMapping
    public List<VaccinationDto> getAllVaccinations() {
        return vaccinationRepository.findAll()
                .stream()
                .map(vaccinationMapper::toDto)
                .collect(Collectors.toList());
    }

    // Get a specific vaccination by its ID
    @GetMapping("/{vaccination_id}")
    public ResponseEntity<VaccinationDto> getVaccinationById(@PathVariable("vaccination_id") Integer id) {
        return vaccinationRepository.findById(id)
                .map(vaccinationMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Get only available vaccinations (where available = true)
    @GetMapping("/available")
    public List<VaccinationDto> getAvailableVaccinations() {
        return vaccinationRepository.findByAvailableTrue()
                .stream()
                .map(vaccinationMapper::toDto)
                .collect(Collectors.toList());
    }

    // Get vaccinations that are currently not available
    @GetMapping("/unavailable")
    public List<VaccinationDto> getUnavailableVaccinations() {
        return vaccinationRepository.findByAvailableFalse()
                .stream()
                .map(vaccinationMapper::toDto)
                .collect(Collectors.toList());
    }

    // Add a new vaccination record
    @PostMapping("/add")
    public VaccinationDto addVaccination(@RequestBody VaccinationDto dto) {
        Vaccination saved = vaccinationRepository.save(vaccinationMapper.toEntity(dto));
        return vaccinationMapper.toDto(saved);
    }

    // Update an existing vaccination using its ID
    @PutMapping("/update/{vaccination_id}")
    public ResponseEntity<VaccinationDto> updateVaccination(@PathVariable("vaccination_id") Integer id,
                                                            @RequestBody VaccinationDto dto) {
        return vaccinationRepository.findById(id).map(existing -> {
            // Convert DTO to entity and make sure the ID stays the same
            Vaccination vaccination = vaccinationMapper.toEntity(dto);
            vaccination.setId(id);
            Vaccination updated = vaccinationRepository.save(vaccination);
            return ResponseEntity.ok(vaccinationMapper.toDto(updated));
        }).orElse(ResponseEntity.notFound().build());
    }
}
