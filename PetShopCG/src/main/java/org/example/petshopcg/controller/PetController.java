package org.example.petshopcg.controller;

import jakarta.validation.Valid;
import org.example.petshopcg.dto.GroomingServiceDto;
import org.example.petshopcg.dto.PetCareDetail;
import org.example.petshopcg.dto.PetDto;
import org.example.petshopcg.dto.PetCategoryDto;
import org.example.petshopcg.entity.GroomingService;
import org.example.petshopcg.entity.Pet;
import org.example.petshopcg.entity.PetCategory;
import org.example.petshopcg.entity.Transaction;
import org.example.petshopcg.mapper.GroomingServiceMapper;
import org.example.petshopcg.mapper.PetMapper;
import org.example.petshopcg.mapper.PetCategoryMapper;
import org.example.petshopcg.mapper.TransactionMapper;
import org.example.petshopcg.repository.PetRepository;
import org.example.petshopcg.repository.PetCategoryRepository;
import org.example.petshopcg.repository.TransactionRepo;
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


    @Autowired
    private GroomingServiceMapper groomingServiceMapper;



    // Helper method to build error response map
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

    // ----------- PLACEHOLDER ROUTES -----------



    @GetMapping("/pets/grooming_services/{pet_id}")
    public ResponseEntity<?> getGroomingServicesForPet(@PathVariable Integer pet_id) {
        try {
            Optional<Pet> petOptional = petRepository.findById(pet_id);
            if (petOptional.isEmpty()) {
                return ResponseEntity.badRequest().body(Map.of(
                        "timestamp", LocalDate.now(),
                        "message", "Pet not found"
                ));
            }

            Pet pet = petOptional.get();

            // Mock grooming data - this can be dynamically customized per pet if needed
            List<Map<String, Object>> groomingServices = List.of(
                    Map.of("service", "Bath", "price", 300, "duration", "30 minutes"),
                    Map.of("service", "Nail Clipping", "price", 150, "duration", "15 minutes"),
                    Map.of("service", "Ear Cleaning", "price", 200, "duration", "20 minutes")
            );

            return ResponseEntity.ok(Map.of(
                    "petId", pet_id,
                    "petName", pet.getName(),
                    "groomingServices", groomingServices
            ));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                    "timestamp", LocalDate.now(),
                    "message", "Failed to fetch grooming services"
            ));
        }
    }

    @GetMapping("/pets/vaccination/{pet_id}")
    public ResponseEntity<?> getVaccinationsForPet(@PathVariable Integer pet_id) {
        try {
            Optional<Pet> petOptional = petRepository.findById(pet_id);
            if (petOptional.isEmpty()) {
                return ResponseEntity.badRequest().body(Map.of(
                        "timestamp", LocalDate.now(),
                        "message", "Pet not found"
                ));
            }

            Pet pet = petOptional.get();

            // Hardcoded vaccination details (can be made dynamic by pet type later)
            List<Map<String, Object>> vaccinations = List.of(
                    Map.of("vaccine", "Rabies", "date", "2024-01-10", "nextDue", "2025-01-10"),
                    Map.of("vaccine", "Distemper", "date", "2024-03-15", "nextDue", "2025-03-15"),
                    Map.of("vaccine", "Parvovirus", "date", "2024-05-01", "nextDue", "2025-05-01")
            );

            return ResponseEntity.ok(Map.of(
                    "petId", pet_id,
                    "petName", pet.getName(),
                    "vaccinationRecords", vaccinations
            ));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                    "timestamp", LocalDate.now(),
                    "message", "Failed to fetch vaccination details"
            ));
        }
    }

    @GetMapping("/pets/food/{pet_id}")
    public ResponseEntity<?> getFoodDetailsForPet(@PathVariable Integer pet_id) {
        try {
            Optional<Pet> petOptional = petRepository.findById(pet_id);
            if (petOptional.isEmpty()) {
                return ResponseEntity.badRequest().body(Map.of(
                        "timestamp", LocalDate.now(),
                        "message", "Pet not found"
                ));
            }

            Pet pet = petOptional.get();

            // Hardcoded pet food details (can be customized later by pet type)
            List<Map<String, Object>> foodDetails = List.of(
                    Map.of("foodName", "Chicken Kibble", "brand", "PetFoodCo", "quantity", "2 kg", "price", 800),
                    Map.of("foodName", "Beef Wet Food", "brand", "YummyPets", "quantity", "500 gm", "price", 350),
                    Map.of("foodName", "Fish Treats", "brand", "OceanPets", "quantity", "200 gm", "price", 150)
            );

            return ResponseEntity.ok(Map.of(
                    "petId", pet_id,
                    "petName", pet.getName(),
                    "foodDetails", foodDetails
            ));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                    "timestamp", LocalDate.now(),
                    "message", "Failed to fetch pet food details"
            ));
        }
    }

    @GetMapping("/pets/transactions/{pet_id}")
    public ResponseEntity<?> getTransactionsForPet(@PathVariable Integer pet_id) {
        try {
            Optional<Pet> petOptional = petRepository.findById(pet_id);
            if (petOptional.isEmpty()) {
                return ResponseEntity.badRequest().body(Map.of(
                        "timestamp", LocalDate.now(),
                        "message", "Pet not found"
                ));
            }

            Pet pet = petOptional.get();

            // Hardcoded transactions data
            List<Map<String, Object>> transactions = List.of(
                    Map.of(
                            "transactionId", 101,
                            "date", "2024-04-01",
                            "amount", 1500,
                            "description", "Grooming and Vaccination",
                            "status", "Completed"
                    ),
                    Map.of(
                            "transactionId", 102,
                            "date", "2024-05-10",
                            "amount", 2000,
                            "description", "Food Purchase",
                            "status", "Completed"
                    ),
                    Map.of(
                            "transactionId", 103,
                            "date", "2024-06-05",
                            "amount", 1800,
                            "description", "Regular Checkup",
                            "status", "Pending"
                    )
            );

            return ResponseEntity.ok(Map.of(
                    "petId", pet_id,
                    "petName", pet.getName(),
                    "transactions", transactions
            ));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                    "timestamp", LocalDate.now(),
                    "message", "Failed to fetch transactions"
            ));
        }
    }

    @GetMapping("/pets/suppliers/{pet_id}")
    public ResponseEntity<?> getSuppliersForPet(@PathVariable Integer pet_id) {
        try {
            Optional<Pet> petOptional = petRepository.findById(pet_id);
            if (petOptional.isEmpty()) {
                return ResponseEntity.badRequest().body(Map.of(
                        "timestamp", LocalDate.now(),
                        "message", "Pet not found"
                ));
            }

            Pet pet = petOptional.get();

            // Hardcoded suppliers data
            List<Map<String, Object>> suppliers = List.of(
                    Map.of(
                            "supplierId", 201,
                            "name", "Happy Pets Supply",
                            "contact", "contact@happypets.com",
                            "phone", "123-456-7890",
                            "address", "123 Pet St, Pet City"
                    ),
                    Map.of(
                            "supplierId", 202,
                            "name", "Animal Care Wholesale",
                            "contact", "sales@animalcare.com",
                            "phone", "987-654-3210",
                            "address", "456 Animal Rd, Pet Town"
                    )
            );

            return ResponseEntity.ok(Map.of(
                    "petId", pet_id,
                    "petName", pet.getName(),
                    "suppliers", suppliers
            ));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                    "timestamp", LocalDate.now(),
                    "message", "Failed to fetch suppliers"
            ));
        }
    }


    @GetMapping("/pets/transaction_history/{pet_id}")
    public ResponseEntity<?> getTransactionHistoryForPet(@PathVariable Integer pet_id) {
        try {
            Optional<Pet> petOptional = petRepository.findById(pet_id);
            if (petOptional.isEmpty()) {
                return ResponseEntity.badRequest().body(Map.of(
                        "timestamp", LocalDate.now(),
                        "message", "Pet not found"
                ));
            }

            Pet pet = petOptional.get();

            // Hardcoded transaction history
            List<Map<String, Object>> transactions = List.of(
                    Map.of(
                            "transactionId", 1001,
                            "date", "2024-04-01",
                            "amount", 500,
                            "details", "Grooming and checkup",
                            "petsInvolved", List.of(
                                    Map.of("petId", pet.getId(), "petName", pet.getName()),
                                    Map.of("petId", 2, "petName", "Max")
                            )
                    ),
                    Map.of(
                            "transactionId", 1002,
                            "date", "2024-05-10",
                            "amount", 300,
                            "details", "Vaccination",
                            "petsInvolved", List.of(
                                    Map.of("petId", pet.getId(), "petName", pet.getName())
                            )
                    )
            );

            return ResponseEntity.ok(Map.of(
                    "petId", pet.getId(),
                    "petName", pet.getName(),
                    "transactionHistory", transactions
            ));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                    "timestamp", LocalDate.now(),
                    "message", "Failed to fetch transaction history"
            ));
        }
    }


    @GetMapping("/pets/care_details/{pet_id}")
    public ResponseEntity<?> getPetCareDetails(@PathVariable Integer pet_id) {
        try {
            Optional<Pet> petOptional = petRepository.findById(pet_id);
            if (petOptional.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Pet not found");
            }

            Pet pet = petOptional.get();

            // Hardcoded vaccination details
            List<PetCareDetail> vaccinations = List.of(
                    new PetCareDetail("vaccination", "Rabies", "2024-01-10", null, "2025-01-10"),
                    new PetCareDetail("vaccination", "Distemper", "2024-03-15", null, "2025-03-15"),
                    new PetCareDetail("vaccination", "Parvovirus", "2024-05-01", null, "2025-05-01")
            );

            // Hardcoded grooming services
            List<PetCareDetail> groomingServices = List.of(
                    new PetCareDetail("grooming", "Bath", "30 minutes", 300, null),
                    new PetCareDetail("grooming", "Nail Clipping", "15 minutes", 150, null),
                    new PetCareDetail("grooming", "Ear Cleaning", "20 minutes", 200, null)
            );

            // Combine lists
            List<PetCareDetail> combined = new ArrayList<>();
            combined.addAll(vaccinations);
            combined.addAll(groomingServices);

            // Create response map with pet details + combined care details list
            Map<String, Object> response = new HashMap<>();
            response.put("petId", pet.getId());
            response.put("petName", pet.getName());
            response.put("careDetails", combined);

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to fetch pet care details");
        }
    }









}
