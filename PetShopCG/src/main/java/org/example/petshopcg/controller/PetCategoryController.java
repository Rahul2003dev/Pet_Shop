package org.example.petshopcg.controller;

import org.example.petshopcg.dto.PetCategoryDto;
import org.example.petshopcg.entity.PetCategory;
import org.example.petshopcg.mapper.PetCategoryMapper;
import org.example.petshopcg.repository.PetCategoryRepository;
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
@RequestMapping("/api/v1/pet-categories")
public class PetCategoryController {

    @Autowired
    private PetCategoryRepository petCategoryRepository;

    @Autowired
    private PetCategoryMapper petCategoryMapper;

    private Map<String, Object> errorResponse(String message) {
        Map<String, Object> error = new HashMap<>();
        error.put("timeStamp", LocalDate.now());
        error.put("message", message);
        return error;
    }

    @GetMapping
    public ResponseEntity<?> getAllCategories() {
        try {
            List<PetCategoryDto> categories = petCategoryRepository.findAll()
                    .stream()
                    .map(petCategoryMapper::toDto)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(categories);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(errorResponse("Validation failed"));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCategoryById(@PathVariable Integer id) {
        try {
            Optional<PetCategory> catOpt = petCategoryRepository.findById(id);
            if (catOpt.isPresent()) {
                return ResponseEntity.ok(petCategoryMapper.toDto(catOpt.get()));
            } else {
                return ResponseEntity.badRequest().body(errorResponse("Validation failed"));
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(errorResponse("Validation failed"));
        }
    }

    @PostMapping
    public ResponseEntity<?> createCategory(@RequestBody PetCategoryDto petCategoryDto) {
        try {
            PetCategory petCategory = petCategoryMapper.toEntity(petCategoryDto);
            PetCategory saved = petCategoryRepository.save(petCategory);
            return ResponseEntity.ok(petCategoryMapper.toDto(saved));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(errorResponse("Validation failed"));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCategory(@PathVariable Integer id, @RequestBody PetCategoryDto petCategoryDto) {
        try {
            Optional<PetCategory> existingOpt = petCategoryRepository.findById(id);
            if (existingOpt.isPresent()) {
                PetCategory updated = petCategoryMapper.toEntity(petCategoryDto);
                updated.setId(id);
                PetCategory saved = petCategoryRepository.save(updated);
                return ResponseEntity.ok(petCategoryMapper.toDto(saved));
            } else {
                return ResponseEntity.badRequest().body(errorResponse("Validation failed"));
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(errorResponse("Validation failed"));
        }
    }
}
