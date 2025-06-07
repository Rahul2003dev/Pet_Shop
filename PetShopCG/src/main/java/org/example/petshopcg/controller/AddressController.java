//package org.example.petshopcg.controller;
//
//import org.example.petshopcg.dto.AddressDto;
//import org.example.petshopcg.entity.Address;
//import org.example.petshopcg.mapper.AddressMapper;
//import org.example.petshopcg.repository.AddressRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.time.LocalDate;
//import java.util.*;
//import java.util.stream.Collectors;
//
//@RestController
//@RequestMapping("/api/v1/address")
//public class AddressController {
//
//    @Autowired
//    private AddressRepository addressRepository;
//
//    @Autowired
//    private AddressMapper addressMapper;
//
//    // Inline error response builder
//    private Map<String, Object> errorResponse(String message) {
//        Map<String, Object> error = new HashMap<>();
//        error.put("timeStamp", LocalDate.now());
//        error.put("message", message);
//        return error;
//    }
//
//    // GET: /api/v1/address
//    @GetMapping
//    public ResponseEntity<?> getAllAddresses() {
//        try {
//            List<AddressDto> addresses = addressRepository.findAll()
//                    .stream()
//                    .map(addressMapper::toDto)
//                    .collect(Collectors.toList());
//            return ResponseEntity.ok(addresses);
//        } catch (Exception e) {
//            return ResponseEntity.badRequest().body(errorResponse("Validation failed"));
//        }
//    }
//
//    // GET: /api/v1/address/{address_id}
//    @GetMapping("/{address_id}")
//    public ResponseEntity<?> getAddressById(@PathVariable("address_id") Integer id) {
//        try {
//            Optional<Address> addressOpt = addressRepository.findById(id);
//            if (addressOpt.isPresent()) {
//                return ResponseEntity.ok(addressMapper.toDto(addressOpt.get()));
//            } else {
//                return ResponseEntity.badRequest().body(errorResponse("Address not found"));
//            }
//        } catch (Exception e) {
//            return ResponseEntity.badRequest().body(errorResponse("Validation failed"));
//        }
//    }
//
//    // POST: /api/v1/address/add
//    @PostMapping("/add")
//    public ResponseEntity<?> createAddress(@RequestBody AddressDto addressDto) {
//        try {
//            Address address = addressMapper.toEntity(addressDto);
//            Address savedAddress = addressRepository.save(address);
//            return ResponseEntity.ok(addressMapper.toDto(savedAddress));
//        } catch (Exception e) {
//            return ResponseEntity.badRequest().body(errorResponse("Validation failed"));
//        }
//    }
//
//    // PUT: /api/v1/address/update/{address_id}
//    @PutMapping("/update/{address_id}")
//    public ResponseEntity<?> updateAddress(@PathVariable("address_id") Integer id, @RequestBody AddressDto addressDto) {
//        try {
//            Optional<Address> existingAddressOpt = addressRepository.findById(id);
//            if (existingAddressOpt.isPresent()) {
//                Address updated = existingAddressOpt.get();
//                updated.setStreet(addressDto.getStreet());
//                updated.setCity(addressDto.getCity());
//                updated.setState(addressDto.getState());
//                updated.setZipCode(addressDto.getZipCode());
//                Address saved = addressRepository.save(updated);
//                return ResponseEntity.ok(addressMapper.toDto(saved));
//            } else {
//                return ResponseEntity.badRequest().body(errorResponse("Address not found"));
//            }
//        } catch (Exception e) {
//            return ResponseEntity.badRequest().body(errorResponse("Validation failed"));
//        }
//    }
//}


package org.example.petshopcg.controller;

import org.example.petshopcg.dto.AddressDto;
import org.example.petshopcg.entity.Address;
import org.example.petshopcg.mapper.AddressMapper;
import org.example.petshopcg.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/address")
public class AddressController {

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private AddressMapper addressMapper;

    // Inline response builder
    private Map<String, Object> responseMessage(String message) {
        Map<String, Object> response = new HashMap<>();
        response.put("timeStamp", LocalDate.now());
        response.put("message", message);
        return response;
    }

    // GET: /api/v1/address
    @GetMapping
    public ResponseEntity<?> getAllAddresses() {
        try {
            List<AddressDto> addresses = addressRepository.findAll()
                    .stream()
                    .map(addressMapper::toDto)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(addresses);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(responseMessage("Validation failed"));
        }
    }

    // GET: /api/v1/address/{address_id}
    @GetMapping("/{address_id}")
    public ResponseEntity<?> getAddressById(@PathVariable("address_id") Integer id) {
        try {
            Optional<Address> addressOpt = addressRepository.findById(id);
            if (addressOpt.isPresent()) {
                return ResponseEntity.ok(addressMapper.toDto(addressOpt.get()));
            } else {
                return ResponseEntity.badRequest().body(responseMessage("Address not found"));
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(responseMessage("Validation failed"));
        }
    }

    // POST: /api/v1/address/add
    @PostMapping("/add")
    public ResponseEntity<?> createAddress(@RequestBody AddressDto addressDto) {
        try {
            Address address = addressMapper.toEntity(addressDto);
            addressRepository.save(address);
            return ResponseEntity.ok(responseMessage("Address added successfully"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(responseMessage("Validation failed"));
        }
    }

    // PUT: /api/v1/address/update/{address_id}
    @PutMapping("/update/{address_id}")
    public ResponseEntity<?> updateAddress(@PathVariable("address_id") Integer id, @RequestBody AddressDto addressDto) {
        try {
            Optional<Address> existingAddressOpt = addressRepository.findById(id);
            if (existingAddressOpt.isPresent()) {
                Address updated = existingAddressOpt.get();
                updated.setStreet(addressDto.getStreet());
                updated.setCity(addressDto.getCity());
                updated.setState(addressDto.getState());
                updated.setZipCode(addressDto.getZipCode());
                addressRepository.save(updated);
                return ResponseEntity.ok(responseMessage("Address updated successfully"));
            } else {
                return ResponseEntity.badRequest().body(responseMessage("Address not found"));
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(responseMessage("Validation failed"));
        }
    }
}

