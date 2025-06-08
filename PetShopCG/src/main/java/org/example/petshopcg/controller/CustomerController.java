package org.example.petshopcg.controller;

import org.example.petshopcg.dto.CustomerDto;
import org.example.petshopcg.entity.Customer;
import org.example.petshopcg.mapper.CustomerMapper;
import org.example.petshopcg.repository.CustomerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

   @Autowired
   private CustomerRepo customerRepo;

    @Autowired
    private CustomerMapper customerMapper;

    @GetMapping
    public List<CustomerDto> getAllCustomers() {
        return customerRepo.findAll()
                .stream()
                .map(customerMapper::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerDto> getCustomerById(@PathVariable Integer id) {
        return customerRepo.findById(id)
                .map(customerMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public CustomerDto createCustomer(@RequestBody CustomerDto dto) {
        Customer saved = customerRepo.save(customerMapper.toEntity(dto));
        return customerMapper.toDto(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomerDto> updateCustomer(@PathVariable Integer id, @RequestBody CustomerDto dto) {
        return customerRepo.findById(id).map(existing -> {
            Customer customer = customerMapper.toEntity(dto);
            customer.setId(id); // Ensure the ID is set
            return ResponseEntity.ok(customerMapper.toDto(customerRepo.save(customer)));
        }).orElse(ResponseEntity.notFound().build());
    }

}
