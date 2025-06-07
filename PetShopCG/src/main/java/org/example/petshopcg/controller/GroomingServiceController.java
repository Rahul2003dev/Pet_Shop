package org.example.petshopcg.controller;

import lombok.RequiredArgsConstructor;
import org.example.petshopcg.dto.GroomingServiceDto;
import org.example.petshopcg.entity.GroomingService;
import org.example.petshopcg.mapper.GroomingServiceMapper;
import org.example.petshopcg.repository.GroomingServiceRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/services")
@RequiredArgsConstructor
public class GroomingServiceController {

    private final GroomingServiceRepository groomingRepo;
    private final GroomingServiceMapper groomingMapper;

    @GetMapping
    public ResponseEntity<List<GroomingServiceDto>> getAllServices() {
        List<GroomingService> services = groomingRepo.findAll();
        List<GroomingServiceDto> dtos = services.stream()
                .map(groomingMapper::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{serviceId}")
    public ResponseEntity<GroomingServiceDto> getServiceById(@PathVariable Integer serviceId) {
        return groomingRepo.findById(serviceId)
                .map(groomingMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    @GetMapping("/available")
    public ResponseEntity<List<GroomingServiceDto>> getAvailableServices() {
        List<GroomingService> available = groomingRepo.findAllByAvailable(true);
        List<GroomingServiceDto> dtos = available.stream()
                .map(groomingMapper::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/unavailable")
    public ResponseEntity<List<GroomingServiceDto>> getUnavailableServices() {
        List<GroomingService> unavailable = groomingRepo.findAllByAvailable(false);
        List<GroomingServiceDto> dtos = unavailable.stream()
                .map(groomingMapper::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }
    @PostMapping("/add")
    public ResponseEntity<GroomingServiceDto> createService(@RequestBody GroomingServiceDto dto) {
        GroomingService entity = groomingMapper.toEntity(dto);
        GroomingService saved = groomingRepo.save(entity);
        return ResponseEntity.ok(groomingMapper.toDto(saved));
    }

    @PutMapping("/{serviceId}")
    public ResponseEntity<GroomingServiceDto> updateService(@PathVariable Integer serviceId,
                                                            @RequestBody GroomingServiceDto dto) {
        if (!groomingRepo.existsById(serviceId)) {
            return ResponseEntity.notFound().build();
        }
        GroomingService entity = groomingMapper.toEntity(dto);
        entity.setId(serviceId); // ensure path ID is applied
        GroomingService updated = groomingRepo.save(entity);
        return ResponseEntity.ok(groomingMapper.toDto(updated));
    }

    @DeleteMapping("/{serviceId}")
    public ResponseEntity<Void> deleteService(@PathVariable Integer serviceId) {
        if (!groomingRepo.existsById(serviceId)) {
            return ResponseEntity.notFound().build();
        }
        groomingRepo.deleteById(serviceId);
        return ResponseEntity.noContent().build();
    }


}
