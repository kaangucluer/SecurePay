package com.firisbe.securepay.controller;

import com.firisbe.securepay.model.Customer;
import com.firisbe.securepay.model.Log;
import com.firisbe.securepay.service.CustomerService;
import com.firisbe.securepay.service.LogService;
import io.swagger.annotations.Api;
import org.apache.el.util.ExceptionUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
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
    public ResponseEntity<List<Customer>> getAllCustomer(){
        return ResponseEntity.status(HttpStatus.OK)
                .body(customerService.getAllCustomer());
    }

    @DeleteMapping("/customers/{id}")
    public ResponseEntity<String> deleteCustomer(@PathVariable Long customer_id) {
        customerService.deleteCustomerById(customer_id);
        return ResponseEntity.ok("Customer deleted successfully");
    }

    @PutMapping("/customers/{id}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable Long customer_id, @RequestBody Customer updatedCustomer) {
        Customer customer = customerService.updateCustomer(customer_id, updatedCustomer);
        return ResponseEntity.ok(customer);
    }

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }
}