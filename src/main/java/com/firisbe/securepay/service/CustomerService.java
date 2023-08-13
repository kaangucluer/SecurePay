package com.firisbe.securepay.service;

import com.firisbe.securepay.model.Customer;
import com.firisbe.securepay.model.Log;
import com.firisbe.securepay.repository.CustomerRepository;
import com.firisbe.securepay.repository.LogRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final LogRepository logRepository;

    private Log createLog(String level, String message) {
        Log log = new Log();
        log.setTimestamp(LocalDateTime.now());
        log.setLevel(level);
        log.setMessage(message);
        return log;
    }

    public Customer saveCustomer(Customer customer){
        try{
            return customerRepository.save(customer);
        } catch (DataIntegrityViolationException ex){
            String errorMessage = "Error: E-mail already exists.";
            Log log = new Log();
            log.setTimestamp(LocalDateTime.now());
            log.setLevel("E-mail ERROR");
            log.setMessage(errorMessage);
            logRepository.save(log);
            throw new IllegalArgumentException(errorMessage, ex);
        }
    }
    public List<Customer> getAllCustomer() {
        return customerRepository.findAll();
    }

    public void deleteCustomerById(Long customer_id) {
        customerRepository.deleteById(customer_id);
    }

    public Customer updateCustomer(Long customer_id, Customer updatedCustomer) {
        try {
            Customer existingCustomer = customerRepository.findById(customer_id)
                    .orElseThrow(() -> new EntityNotFoundException("Customer not found"));
            existingCustomer.setName(updatedCustomer.getName());
            existingCustomer.setTcNo(updatedCustomer.getTcNo());
            existingCustomer.setEmail(updatedCustomer.getEmail());
            existingCustomer.setPhoneNumber(updatedCustomer.getPhoneNumber());
            existingCustomer.setAddress(updatedCustomer.getAddress());

            logRepository.save(createLog("INFO", "Customer updated: " + existingCustomer.getName()));

            return customerRepository.save(existingCustomer);
        }catch (EntityNotFoundException exe){
        logRepository.save(createLog("ERROR", "EntityNotFoundException occurred: " + exe.getMessage()));
        throw exe;
        }
    }


    public CustomerService(CustomerRepository customerRepository, LogRepository logRepository) {
        this.customerRepository = customerRepository;    // Dependency Injection
        this.logRepository = logRepository;
    }


}
