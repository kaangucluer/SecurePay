package com.firisbe.securepay.controller;

import com.firisbe.securepay.model.Customer;
import com.firisbe.securepay.service.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping("/save")
    public ResponseEntity<Customer> saveCustomer(@RequestBody Customer customer) {
        try {
            Customer savedCustomer = customerService.saveCustomer(customer);
            return new ResponseEntity<>(savedCustomer, HttpStatus.CREATED);
        } catch (IllegalArgumentException ex) {
            // E-posta benzersizlik hatası yakalandı, hata mesajıyla 400 Bad Request döndürülüyor
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<Customer>> getAllCustomer() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(customerService.getAllCustomer());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteCustomer(@PathVariable Long id) {
        customerService.deleteCustomerById(id);
        return ResponseEntity.ok("Customer deleted successfully");
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable Long id, @RequestBody Customer updatedCustomer) {
        Customer customer = customerService.updateCustomer(id, updatedCustomer);
        return ResponseEntity.ok(customer);
    }

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }
}
