package org.example.petshopcg.controller;

import jakarta.validation.Valid;
import org.example.petshopcg.dto.*;
import org.example.petshopcg.entity.Pet;
import org.example.petshopcg.mapper.*;
import org.example.petshopcg.repository.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1")
public class PetController {

    @Autowired
    private PetRepository petRepository;

    @Autowired
    private PetMapper petMapper;


    // Helper method to build error response map inside the controller itself
    private Map<String, Object> errorMap(String message) {
        Map<String, Object> map = new HashMap<>();
        map.put("timeStamp", LocalDate.now());
        map.put("message", message);
        return map;
    }

    // ----------- PET ENDPOINTS -----------

    @GetMapping("/pets")
    public ResponseEntity<?> getAllPets() {
        try {
            List<PetDto> pets = petRepository.findAll()
                    .stream()
                    .map(petMapper::toDto)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(pets);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(errorMap("Failed to fetch pets"));
        }
    }

    @GetMapping("/pets/{id}")
    public ResponseEntity<?> getPetById(@PathVariable Integer id) {
        Optional<Pet> optionalPet = petRepository.findById(id);
        if (optionalPet.isPresent()) {
            return ResponseEntity.ok(petMapper.toDto(optionalPet.get()));
        } else {
            return ResponseEntity.badRequest().body(errorMap("Pet not found"));
        }
    }

    @GetMapping("/pets/category/{categoryName}")
    public ResponseEntity<?> getPetsByCategory(@PathVariable String categoryName) {
        try {
            List<PetDto> pets = petRepository.findAll()
                    .stream()
                    .filter(p -> p.getCategory() != null && categoryName.equalsIgnoreCase(p.getCategory().getName()))
                    .map(petMapper::toDto)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(pets);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(errorMap("Failed to fetch pets by category"));
        }
    }

    @PostMapping("/pets/add")
    public ResponseEntity<?> createPet(@Valid @RequestBody PetDto petDto, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(errorMap("Validation failed"));
        }
        try {
            Pet pet = petMapper.toEntity(petDto);
            Pet savedPet = petRepository.save(pet);
            return ResponseEntity.status(HttpStatus.CREATED).body(petMapper.toDto(savedPet));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(errorMap("Failed to create pet"));
        }
    }

    @PutMapping("/pets/update/{id}")
    public ResponseEntity<?> updatePet(@PathVariable Integer id, @Valid @RequestBody PetDto petDto, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(errorMap("Validation failed"));
        }
        Optional<Pet> existingPetOpt = petRepository.findById(id);
        if (existingPetOpt.isPresent()) {
            Pet pet = petMapper.toEntity(petDto);
            pet.setId(id);
            Pet saved = petRepository.save(pet);
            return ResponseEntity.ok(petMapper.toDto(saved));
        } else {
            return ResponseEntity.badRequest().body(errorMap("Pet not found"));
        }
    }

    // ----------- GROUP BY ROUTES -----------
//
//    @GetMapping("/group-by-category")
//    public ResponseEntity<List<PetCountByCategoryDto>> getPetCountByCategory() {
//        List<Pet> pets = petRepository.findAll();
//
//        List<PetCountByCategoryDto> result = pets.stream()
//                .filter(p -> p.getCategory() != null && p.getCategory().getName() != null)
//                .collect(Collectors.groupingBy(
//                        p -> p.getCategory().getName(),
//                        Collectors.counting()
//                ))
//                .entrySet()
//                .stream()
//                .map(entry -> new PetCountByCategoryDto(entry.getKey(), entry.getValue()))
//                .collect(Collectors.toList());
//
//        return ResponseEntity.ok(result);
//    }
//
//    @GetMapping("/pets/grouped-by-category")
//    public ResponseEntity<?> getGroupedSortedPets() {
//        try {
//            Map<String, List<PetDto>> groupedAndSortedPets = petRepository.findAll()
//                    .stream()
//                    .filter(pet -> pet.getCategory() != null)
//                    .collect(Collectors.groupingBy(
//                            pet -> pet.getCategory().getName(),
//                            Collectors.collectingAndThen(
//                                    Collectors.mapping(petMapper::toDto, Collectors.toList()),
//                                    list -> list.stream()
//                                            .sorted(Comparator.comparing(PetDto::getName)) // choose your field
//                                            .collect(Collectors.toList())
//                            )
//                    ));
//
//            return ResponseEntity.ok(groupedAndSortedPets);
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
//                    "timestamp", LocalDate.now(),
//                    "message", "Failed to group and sort pets"
//            ));
//        }
//    }

    @GetMapping("/pets/grouped-by-category")
    public ResponseEntity<?> getGroupedSortedPets() {
        try {
            List<Pet> pets = petRepository.findAllWithCategorySorted();

            Map<String, List<PetDto>> groupedAndSortedPets = pets.stream()
                    .collect(Collectors.groupingBy(
                            pet -> pet.getCategory().getName(),
                            LinkedHashMap::new, // maintains the order from DB (category-wise sorted)
                            Collectors.mapping(petMapper::toDto, Collectors.toList())
                    ));

            return ResponseEntity.ok(groupedAndSortedPets);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                    "timestamp", LocalDate.now(),
                    "message", "Failed to group and sort pets"
            ));
        }
    }
    @GetMapping("/group-by-breed")
    public ResponseEntity<List<PetCountByBreedDto>> getPetCountByBreed() {
        List<PetCountByBreedDto> result = petRepository.countPetsByBreed();
        return ResponseEntity.ok(result);
    }






}
