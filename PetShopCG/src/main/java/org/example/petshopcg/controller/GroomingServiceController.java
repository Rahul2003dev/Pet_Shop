package org.example.petshopcg.controller;

import lombok.RequiredArgsConstructor;
import org.example.petshopcg.dto.GroomingServiceDto;
import org.example.petshopcg.entity.GroomingService;
import org.example.petshopcg.mapper.GroomingServiceMapper;
import org.example.petshopcg.repository.GroomingServiceRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/services")
@RequiredArgsConstructor
public class GroomingServiceController {

    private final GroomingServiceRepository groomingRepo;
    private final GroomingServiceMapper groomingMapper;

    @GetMapping
    public ResponseEntity<?> getAllServices() {
        try {
            List<GroomingService> services = groomingRepo.findAll();
            List<GroomingServiceDto> dtos = services.stream()
                    .map(groomingMapper::toDto)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(dtos);
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(Map.of("timestamp", LocalDate.now().toString(), "message", "Validation failed"));
        }
    }

    @GetMapping("/{serviceId}")
    public ResponseEntity<?> getServiceById(@PathVariable Integer serviceId) {
        try {
            if (groomingRepo.existsById(serviceId)) {
                GroomingService service = groomingRepo.findById(serviceId).get();
                GroomingServiceDto dto = groomingMapper.toDto(service);
                return ResponseEntity.ok(dto);
            } else {
                return ResponseEntity.status(404)
                        .body(Map.of("timestamp", LocalDate.now().toString(),
                                "message", "Service with ID " + serviceId + " not found"));
            }
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(Map.of("timestamp", LocalDate.now().toString(), "message", "Validation failed"));
        }
    }

    @GetMapping("/available")
    public ResponseEntity<?> getAvailableServices() {
        try {
            List<GroomingService> available = groomingRepo.findAllByAvailable(true);
            List<GroomingServiceDto> dtos = available.stream()
                    .map(groomingMapper::toDto)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(dtos);
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(Map.of("timestamp", LocalDate.now().toString(), "message", "Validation failed"));
        }
    }

    @GetMapping("/unavailable")
    public ResponseEntity<?> getUnavailableServices() {
        try {
            List<GroomingService> unavailable = groomingRepo.findAllByAvailable(false);
            List<GroomingServiceDto> dtos = unavailable.stream()
                    .map(groomingMapper::toDto)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(dtos);
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(Map.of("timestamp", LocalDate.now().toString(), "message", "Validation failed"));
        }
    }

    @PostMapping("/add")
    public ResponseEntity<?> createService(@RequestBody GroomingServiceDto dto) {
        try {
            boolean exists = groomingRepo.existsByName(dto.getName());
            if (exists) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(Map.of(
                                "timeStamp", LocalDate.now().toString(),
                                "message", "Service with name \"" + dto.getName() + "\" already exists"
                        ));
            }

            GroomingService entity = groomingMapper.toEntity(dto);
            GroomingService saved = groomingRepo.save(entity);

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(Map.of(
                            "timeStamp", LocalDate.now().toString(),
                            "message", "Service \"" + saved.getName() + "\" added successfully"
                    ));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of(
                            "timeStamp", LocalDate.now().toString(),
                            "message", "Validation failed"
                    ));
        }
    }




    @PutMapping("/{serviceId}")
    public ResponseEntity<?> updateService(@PathVariable Integer serviceId,
                                           @RequestBody GroomingServiceDto dto) {
        try {
            if (!groomingRepo.existsById(serviceId)) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of(
                                "timeStamp", LocalDate.now().toString(),
                                "message", "Service with ID " + serviceId + " not found"
                        ));
            }

            GroomingService entity = groomingMapper.toEntity(dto);
            entity.setId(serviceId);
            groomingRepo.save(entity);

            return ResponseEntity.ok(Map.of(
                    "timeStamp", LocalDate.now().toString(),
                    "message", "Service \"" + dto.getName() + "\" updated successfully"
            ));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of(
                            "timeStamp", LocalDate.now().toString(),
                            "message", "Validation failed"
                    ));
        }
    }


    @DeleteMapping("/{serviceId}")
    public ResponseEntity<?> deleteService(@PathVariable Integer serviceId) {
        try {
            if (!groomingRepo.existsById(serviceId)) {
                return ResponseEntity.status(404)
                        .body(Map.of("timestamp", LocalDate.now().toString(),
                                "message", "Service with ID " + serviceId + " not found"));
            }
            groomingRepo.deleteById(serviceId);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(Map.of("timestamp", LocalDate.now().toString(), "message", "Validation failed"));
        }
    }
}
