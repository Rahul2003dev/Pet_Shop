package org.example.petshopcg.controller;

import jakarta.validation.Valid;
import org.example.petshopcg.dto.PetCategoryDto;
import org.example.petshopcg.entity.PetCategory;
import org.example.petshopcg.mapper.PetCategoryMapper;
import org.example.petshopcg.repository.PetCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;

import java.time.LocalDate;
import java.util.*;

@RestController
@RequestMapping("/api/v1/categories")
public class PetCategoryController {

    @Autowired
    private PetCategoryRepository repository;

    @Autowired
    private PetCategoryMapper PetCategoryMapper;

    // GET: All Categories
    @GetMapping
    public ResponseEntity<?> getAllCategories() {
        List<PetCategory> categories = repository.findAll();
        List<PetCategoryDto> dtos = categories.stream()
                .map(PetCategoryMapper::toDto)
                .toList();

        return ResponseEntity.ok(dtos);
    }

    // GET: Category by ID
    @GetMapping("/{category_id}")
    public ResponseEntity<?> getCategoryById(@PathVariable Integer category_id) {
        Optional<PetCategory> optional = repository.findById(category_id);

        if (optional.isPresent()) {
            PetCategoryDto dto = PetCategoryMapper.toDto(optional.get());
            return ResponseEntity.ok(dto);
        } else {
            return ResponseEntity.badRequest().body(Map.of(
                    "timeStamp", LocalDate.now().toString(),
                    "message", "Category not found"
            ));
        }
    }

    // GET: Category by Name
    @GetMapping("/name/{category_name}")
    public ResponseEntity<?> getCategoryByName(@PathVariable String category_name) {
        Optional<PetCategory> optional = repository.findByName(category_name);

        if (optional.isPresent()) {
            PetCategoryDto dto = PetCategoryMapper.toDto(optional.get());
            return ResponseEntity.ok(dto);
        } else {
            return ResponseEntity.badRequest().body(Map.of(
                    "timeStamp", LocalDate.now().toString(),
                    "message", "Category not found"
            ));
        }
    }

    // POST: Add Category
    @PostMapping("/add")
    public ResponseEntity<?> addCategory(@Valid @RequestBody PetCategoryDto dto, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(Map.of(
                    "timeStamp", LocalDate.now().toString(),
                    "message", "Validation failed"
            ));
        }

        PetCategory entity = PetCategoryMapper.toEntity(dto);
        repository.save(entity);

        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
                "timeStamp", LocalDate.now().toString(),
                "message", "Category added successfully"
        ));
    }

    // PUT: Update Category
    @PutMapping("/update/{category_id}")
    public ResponseEntity<?> updateCategory(@PathVariable Integer category_id, @Valid @RequestBody PetCategoryDto dto, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(Map.of(
                    "timeStamp", LocalDate.now().toString(),
                    "message", "Validation failed"
            ));
        }

        Optional<PetCategory> optional = repository.findById(category_id);
        if (optional.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of(
                    "timeStamp", LocalDate.now().toString(),
                    "message", "Category not found"
            ));
        }

        PetCategory category = optional.get();
        category.setName(dto.getName());
        repository.save(category);

        return ResponseEntity.ok(Map.of(
                "timeStamp", LocalDate.now().toString(),
                "message", "Category updated successfully"
        ));
    }
}
