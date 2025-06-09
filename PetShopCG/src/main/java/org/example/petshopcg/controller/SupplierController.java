package org.example.petshopcg.controller;

import org.example.petshopcg.dto.SupplierDto;
import org.example.petshopcg.entity.Supplier;
import org.example.petshopcg.mapper.SupplierMapper;
import org.example.petshopcg.repository.SupplierRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/suppliers")
public class SupplierController {

    @Autowired
    private SupplierRepo supplierRepo;

    @Autowired
    private SupplierMapper supplierMapper;

    // GET all suppliers
    @GetMapping
    public ResponseEntity<List<SupplierDto>> getAllSuppliers() {
        List<Supplier> suppliers = supplierRepo.findAll();
        List<SupplierDto> dtos = suppliers.stream().map(supplierMapper::toDto).toList();
        return ResponseEntity.ok(dtos);
    }

    // GET supplier by ID
    @GetMapping("/{supplierId}")
    public ResponseEntity<?> getSupplierById(@PathVariable Integer supplierId) {
        Optional<Supplier> supplier = supplierRepo.findById(supplierId);
        if (supplier.isPresent()) {
            return ResponseEntity.ok(supplierMapper.toDto(supplier.get()));
        } else {
            return validationFailedResponse();
        }
    }

    // GET supplier by name
    @GetMapping("/name/{name}")
    public ResponseEntity<?> getSupplierByName(@PathVariable String name) {
        List<Supplier> suppliers = supplierRepo.findByNameIgnoreCase(name);
        if (suppliers.isEmpty()) {
            return validationFailedResponse();
        }
        List<SupplierDto> dtos = suppliers.stream().map(supplierMapper::toDto).toList();
        return ResponseEntity.ok(dtos);
    }

    // POST add supplier
    @PostMapping("/add")
    public ResponseEntity<?> addSupplier(@RequestBody SupplierDto dto) {
        try {
            Supplier saved = supplierRepo.save(supplierMapper.toEntity(dto));
            SupplierDto savedDto = supplierMapper.toDto(saved);
            return buildResponse("Supplier added successfully", savedDto);
        } catch (Exception e) {
            return validationFailedResponse();
        }
    }

    // PUT update supplier
    @PutMapping("/update/{supplierId}")
    public ResponseEntity<?> updateSupplier(@PathVariable Integer supplierId,
                                            @RequestBody SupplierDto dto) {
        Optional<Supplier> existing = supplierRepo.findById(supplierId);
        if (existing.isPresent()) {
            Supplier supplierToUpdate = supplierMapper.toEntity(dto);
            supplierToUpdate.setId(supplierId);
            Supplier saved = supplierRepo.save(supplierToUpdate);
            SupplierDto savedDto = supplierMapper.toDto(saved);
            return buildResponse("Supplier updated successfully", savedDto);
        } else {
            return validationFailedResponse();
        }
    }

    // GET suppliers by city
    @GetMapping("/city/{city_name}")
    public ResponseEntity<?> getSuppliersByCity(@PathVariable("city_name") String city) {
        List<Supplier> suppliers = supplierRepo.findByAddressCityIgnoreCase(city);
        if (suppliers.isEmpty()) {
            return validationFailedResponse();
        }
        List<SupplierDto> dtos = suppliers.stream().map(supplierMapper::toDto).collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    // GET suppliers by state
    @GetMapping("/state/{state_name}")
    public ResponseEntity<?> getSuppliersByState(@PathVariable("state_name") String state) {
        List<Supplier> suppliers = supplierRepo.findByAddressStateIgnoreCase(state);
        if (suppliers.isEmpty()) {
            return validationFailedResponse();
        }
        List<SupplierDto> dtos = suppliers.stream().map(supplierMapper::toDto).collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    // Helper method to build the response JSON
    private ResponseEntity<Map<String, Object>> buildResponse(String message, SupplierDto dto) {
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("timeStamp", LocalDate.now().toString());
        response.put("message", message);
        response.put("data", dto);
        return ResponseEntity.ok(response);
    }

    // Common method to return error JSON
    private ResponseEntity<Map<String, String>> validationFailedResponse() {
        Map<String, String> error = new HashMap<>();
        error.put("timeStamp", LocalDate.now().toString());
        error.put("message", "Validation failed");
        return ResponseEntity.badRequest().body(error);
    }
}
