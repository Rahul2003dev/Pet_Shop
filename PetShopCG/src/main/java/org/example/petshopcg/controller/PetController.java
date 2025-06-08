package org.example.petshopcg.controller;

import org.example.petshopcg.dto.PetDto;
import org.example.petshopcg.entity.Pet;
import org.example.petshopcg.mapper.PetMapper;
import org.example.petshopcg.repository.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1")
public class PetController {

    @Autowired
    private PetRepository petRepository;

    @Autowired
    private PetMapper petMapper;

    // Inline error response builder
    private Map<String, Object> errorResponse(String message) {
        Map<String, Object> error = new HashMap<>();
        error.put("timeStamp", LocalDate.now());
        error.put("message", message);
        return error;
    }

    @GetMapping("/pets")
    public ResponseEntity<?> getAllPets() {
        try {
            List<PetDto> pets = petRepository.findAll()
                    .stream()
                    .map(petMapper::toDto)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(pets);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(errorResponse("Validation failed"));
        }
    }

    @GetMapping("/pets/{id}")
    public ResponseEntity<?> getPetById(@PathVariable Integer id) {
        try {
            Optional<Pet> petOpt = petRepository.findById(id);
            if (petOpt.isPresent()) {
                return ResponseEntity.ok(petMapper.toDto(petOpt.get()));
            } else {
                return ResponseEntity.badRequest().body(errorResponse("Validation failed"));
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(errorResponse("Validation failed"));
        }
    }

    @PostMapping
    public ResponseEntity<?> createPet(@RequestBody PetDto petDto) {
        try {
            Pet pet = petMapper.toEntity(petDto);
            Pet savedPet = petRepository.save(pet);
            return ResponseEntity.ok(petMapper.toDto(savedPet));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(errorResponse("Validation failed"));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updatePet(@PathVariable Integer id, @RequestBody PetDto petDto) {
        try {
            Optional<Pet> existingPetOpt = petRepository.findById(id);
            if (existingPetOpt.isPresent()) {
                Pet updatedPet = petMapper.toEntity(petDto);
                updatedPet.setId(id);
                Pet saved = petRepository.save(updatedPet);
                return ResponseEntity.ok(petMapper.toDto(saved));
            } else {
                return ResponseEntity.badRequest().body(errorResponse("Validation failed"));
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(errorResponse("Validation failed"));
        }
    }
}