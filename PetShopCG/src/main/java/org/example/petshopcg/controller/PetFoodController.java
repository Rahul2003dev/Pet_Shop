package org.example.petshopcg.controller;

import org.example.petshopcg.dto.PetFoodDto;
import org.example.petshopcg.entity.PetFood;
import org.example.petshopcg.mapper.PetFoodMapper;
import org.example.petshopcg.repository.PetFoodRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/pet_foods")
public class PetFoodController {

    @Autowired
    private PetFoodRepo petFoodRepo;

    @Autowired
    private PetFoodMapper petFoodMapper;

    // GET all pet foods
    @GetMapping
    public ResponseEntity<?> getAllPetFoods() {
        List<PetFoodDto> foods = petFoodRepo.findAll()
                .stream()
                .map(petFoodMapper::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(foods);
    }

    @GetMapping("/{food_id}")
    public ResponseEntity<?> getPetFoodById(@PathVariable("food_id") Integer id) {
        Optional<PetFood> food = petFoodRepo.findById(id);
        if (food.isPresent()) {
            PetFoodDto dto = petFoodMapper.toDto(food.get());
            return ResponseEntity.ok(dto);
        } else {
            return validationFailedResponse();  // Returns JSON with timestamp + message
        }
    }

    // GET by name (search)
    @GetMapping("/search")
    public ResponseEntity<?> searchByName(@RequestParam("name") String name) {
        List<PetFood> foods = petFoodRepo.findByNameContainingIgnoreCase(name);
        if (foods.isEmpty()) {
            return validationFailedResponse();
        }
        return ResponseEntity.ok(
                foods.stream().map(petFoodMapper::toDto).collect(Collectors.toList())
        );
    }

    // GET by type
    @GetMapping("/food_type/{type}")
    public ResponseEntity<?> getByType(@PathVariable("type") String type) {
        List<PetFood> foods = petFoodRepo.findByTypeIgnoreCase(type);
        if (foods.isEmpty()) {
            return validationFailedResponse();
        }
        return ResponseEntity.ok(
                foods.stream().map(petFoodMapper::toDto).collect(Collectors.toList())
        );
    }

    // GET by brand
    @GetMapping("/brand/{brand_name}")
    public ResponseEntity<?> getByBrand(@PathVariable("brand_name") String brand) {
        List<PetFood> foods = petFoodRepo.findByBrandIgnoreCase(brand);
        if (foods.isEmpty()) {
            return validationFailedResponse();
        }
        return ResponseEntity.ok(
                foods.stream().map(petFoodMapper::toDto).collect(Collectors.toList())
        );
    }

    // POST - add new pet food
    @PostMapping("/add")
    public ResponseEntity<?> addPetFood(@RequestBody PetFoodDto dto) {
        try {
            PetFood saved = petFoodRepo.save(petFoodMapper.toEntity(dto));
            PetFoodDto savedDto = petFoodMapper.toDto(saved);

            Map<String, Object> response = new LinkedHashMap<>();
            response.put("timeStamp", LocalDate.now().toString());
            response.put("message", "Pet food added successfully");
            response.put("data", savedDto);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return validationFailedResponse();
        }
    }

    // PUT - update full pet food by id
    @PutMapping("/update/{food_id}")
    public ResponseEntity<?> updatePetFood(
            @PathVariable("food_id") Integer id,
            @RequestBody PetFoodDto updatedDto
    ) {
        Optional<PetFood> optional = petFoodRepo.findById(id);
        if (optional.isEmpty()) {
            return validationFailedResponse();
        }

        PetFood food = optional.get();
        food.setName(updatedDto.getName());
        food.setBrand(updatedDto.getBrand());
        food.setType(updatedDto.getType());
        food.setQuantity(updatedDto.getQuantity());
        food.setPrice(updatedDto.getPrice());

        PetFood updated = petFoodRepo.save(food);
        PetFoodDto updatedRes = petFoodMapper.toDto(updated);

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("timeStamp", LocalDate.now().toString());
        response.put("message", "Pet food updated successfully");
        response.put("data", updatedRes);

        return ResponseEntity.ok(response);
    }

    // PUT - update quantity only
    @PutMapping("/quantity/{food_id}")
    public ResponseEntity<?> updateQuantity(
            @PathVariable("food_id") Integer id,
            @RequestParam("quantity") Integer quantity
    ) {
        Optional<PetFood> optional = petFoodRepo.findById(id);
        if (optional.isEmpty()) {
            return validationFailedResponse();
        }

        PetFood food = optional.get();
        food.setQuantity(quantity);
        PetFood updated = petFoodRepo.save(food);
        PetFoodDto updatedDto = petFoodMapper.toDto(updated);

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("timeStamp", LocalDate.now().toString());
        response.put("message", "Quantity updated successfully");
        response.put("data", updatedDto);

        return ResponseEntity.ok(response);
    }

    private ResponseEntity<Map<String, String>> validationFailedResponse() {
        Map<String, String> error = new LinkedHashMap<>();
        error.put("timeStamp", LocalDate.now().toString());
        error.put("message", "Validation failed");
        return ResponseEntity.badRequest().body(error);
    }


}
