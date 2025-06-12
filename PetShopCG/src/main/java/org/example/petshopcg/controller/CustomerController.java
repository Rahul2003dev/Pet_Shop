package org.example.petshopcg.controller;

import org.example.petshopcg.dto.*;
import org.example.petshopcg.entity.Address;
import org.example.petshopcg.entity.Customer;
import org.example.petshopcg.entity.Transaction;
import org.example.petshopcg.mapper.CustomerMapper;
import org.example.petshopcg.repository.AddressRepository;
import org.example.petshopcg.repository.CustomerRepo;
import org.example.petshopcg.repository.TransactionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;
//rahulpatel code
@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api/customers")
public class CustomerController {

   @Autowired
   private CustomerRepo customerRepo;

    @Autowired
    private CustomerMapper customerMapper;

    @Autowired
    private TransactionRepo transactionRepo;

    @Autowired
    private AddressRepository addressRepository;

    @GetMapping
    public List<CustomerDto> getAllCustomers() {
        return customerRepo.findAll()
                .stream()
                .map(customerMapper::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/by-first-name")
    public List<CustomerDto> getByFirstName(@RequestParam String firstName) {
        return customerRepo.findByFirstName(firstName)
                .stream()
                .map(customerMapper::toDto)
                .toList();
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

    @GetMapping("/summary/{id}")
    public ResponseEntity<CustomerSummaryDto> getCustomerSummary(@PathVariable Integer id) {
        // Fetch customer
        Customer customer = customerRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found"));

        // Create DTO and map base info
        CustomerSummaryDto dto = new CustomerSummaryDto();
        dto.setId(customer.getId());
        dto.setEmail(customer.getEmail());
        dto.setFirstName(customer.getFirstName());
        dto.setLastName(customer.getLastName());
        dto.setPhoneNumber(customer.getPhoneNumber());


        Address address = customer.getAddress();
        if (address != null) {
            CustomerSummaryDto.AddressDto addressDto = new CustomerSummaryDto.AddressDto();
            addressDto.setId(address.getId());
            addressDto.setStreet(address.getStreet());
            addressDto.setCity(address.getCity());
            addressDto.setState(address.getState());
            addressDto.setZipCode(address.getZipCode());
            dto.setAddress(addressDto);
        }

        // Fetch and map transactions
        List<Transaction> transactions = transactionRepo.findByCustomer_Id(customer.getId());
        List<CustomerSummaryDto.TransactionDto> transactionDtos = transactions.stream().map(t -> {
            CustomerSummaryDto.TransactionDto txDto = new CustomerSummaryDto.TransactionDto();
            txDto.setId(t.getId());
            txDto.setTransactionDate(t.getTransactionDate());
            txDto.setAmount(t.getAmount());
            txDto.setTransactionStatus(t.getTransactionStatus());
            return txDto;
        }).collect(Collectors.toList());

        dto.setTransactions(transactionDtos);

        return ResponseEntity.ok(dto);
    }

    @GetMapping("/summary/all")
    public ResponseEntity<List<CustomerSummaryDto>> getAllCustomerSummaries() {
        List<Customer> customers = customerRepo.findAll();

        List<CustomerSummaryDto> summaries = customers.stream().map(customer -> {
            CustomerSummaryDto dto = new CustomerSummaryDto();
            dto.setId(customer.getId());
            dto.setEmail(customer.getEmail());
            dto.setFirstName(customer.getFirstName());
            dto.setLastName(customer.getLastName());
            dto.setPhoneNumber(customer.getPhoneNumber());

            Address address = customer.getAddress();
            if (address != null) {
                CustomerSummaryDto.AddressDto addressDto = new CustomerSummaryDto.AddressDto();
                addressDto.setId(address.getId());
                addressDto.setStreet(address.getStreet());
                addressDto.setCity(address.getCity());
                addressDto.setState(address.getState());
                addressDto.setZipCode(address.getZipCode());
                dto.setAddress(addressDto);
            }

            List<Transaction> transactions = transactionRepo.findByCustomer_Id(customer.getId());
            List<CustomerSummaryDto.TransactionDto> transactionDtos = transactions.stream().map(t -> {
                CustomerSummaryDto.TransactionDto txDto = new CustomerSummaryDto.TransactionDto();
                txDto.setId(t.getId());
                txDto.setTransactionDate(t.getTransactionDate());
                txDto.setAmount(t.getAmount());
                txDto.setTransactionStatus(t.getTransactionStatus());
                return txDto;
            }).collect(Collectors.toList());

            dto.setTransactions(transactionDtos);

            return dto;
        }).collect(Collectors.toList());

        return ResponseEntity.ok(summaries);
    }


    @PostMapping("/frontend")
    public ResponseEntity<String> createCustomer(@RequestBody CustomerFullDto customerDto) {
        // Map Address
        Address address = new Address();
        address.setId(customerDto.getAddress().getId());
        address.setStreet(customerDto.getAddress().getStreet());
        address.setCity(customerDto.getAddress().getCity());
        address.setState(customerDto.getAddress().getState());
        address.setZipCode(customerDto.getAddress().getZipCode());
        addressRepository.save(address);

        // Map Customer
        Customer customer = new Customer();
        customer.setId(customerDto.getId());
        customer.setEmail(customerDto.getEmail());
        customer.setFirstName(customerDto.getFirstName());
        customer.setLastName(customerDto.getLastName());
        customer.setPhoneNumber(customerDto.getPhoneNumber());
        customer.setAddress(address);
        customerRepo.save(customer);

        // Map Transactions
        if (customerDto.getTransactions() != null) {
            for (TransactionDto txDto : customerDto.getTransactions()) {
                Transaction tx = new Transaction();
                tx.setId(txDto.getId());
                tx.setCustomer(customer);
                tx.setTransactionDate(txDto.getTransactionDate());
                tx.setAmount(txDto.getAmount());
                tx.setTransactionStatus(txDto.getTransactionStatus());
                transactionRepo.save(tx);
            }
        }

        return ResponseEntity.ok("Customer saved successfully");
    }

    @PutMapping("/{id}/full") // or "/{id}/address"
    public ResponseEntity<customeraddressDto> updateCustomer(@PathVariable Integer id, @RequestBody customeraddressDto customerDto) {
        Customer customer = customerRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        customer.setEmail(customerDto.getEmail());
        customer.setFirstName(customerDto.getFirstName());
        customer.setLastName(customerDto.getLastName());
        customer.setPhoneNumber(customerDto.getPhoneNumber());

        AddressDto addressDto = customerDto.getAddress();
        if (addressDto != null) {
            Address address = customer.getAddress();
            if (address == null) {
                address = new Address();
            }

            address.setStreet(addressDto.getStreet());
            address.setCity(addressDto.getCity());
            address.setState(addressDto.getState());
            address.setZipCode(addressDto.getZipCode());

            addressRepository.save(address);
            customer.setAddress(address);
        }

       customerRepo.save(customer);

        customeraddressDto updatedDto = new customeraddressDto();
        updatedDto.setId(customer.getId());
        updatedDto.setEmail(customer.getEmail());
        updatedDto.setFirstName(customer.getFirstName());
        updatedDto.setLastName(customer.getLastName());
        updatedDto.setPhoneNumber(customer.getPhoneNumber());

        AddressDto updatedAddress = new AddressDto(); // ✅ Only if `setters` exist
        updatedAddress.setId(customer.getAddress().getId());
        updatedAddress.setStreet(customer.getAddress().getStreet());
        updatedAddress.setCity(customer.getAddress().getCity());
        updatedAddress.setState(customer.getAddress().getState());
        updatedAddress.setZipCode(customer.getAddress().getZipCode());

        updatedDto.setAddress(updatedAddress);

        return ResponseEntity.ok(updatedDto);
    }




}
