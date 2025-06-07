package org.example.petshopcg.controller;

import org.example.petshopcg.dto.PetDto;
import org.example.petshopcg.entity.Pet;
import org.example.petshopcg.mapper.PetMapper;
import org.example.petshopcg.repository.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/pets")
public class PetController {

    @Autowired
    private PetRepository petRepository;

    @Autowired
    private PetMapper petMapper;

    @GetMapping
    public List<PetDto> getAllPets() {
        return petRepository.findAll()
                .stream()
                .map(petMapper::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PetDto> getPetById(@PathVariable Integer id) {
        return petRepository.findById(id)
                .map(petMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<PetDto> createPet(@RequestBody PetDto petDto) {
        Pet pet = petMapper.toEntity(petDto);
        Pet savedPet = petRepository.save(pet);
        return ResponseEntity.ok(petMapper.toDto(savedPet));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PetDto> updatePet(@PathVariable
                                                Integer id, @RequestBody PetDto petDto) {
        return petRepository.findById(id).map(existingPet -> {
            Pet updatedPet = petMapper.toEntity(petDto);
            updatedPet.setId(id); // Ensure ID stays the same
            Pet saved = petRepository.save(updatedPet);
            return ResponseEntity.ok(petMapper.toDto(saved));
        }).orElse(ResponseEntity.notFound().build());
    }


}
