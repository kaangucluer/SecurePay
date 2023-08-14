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
import java.util.Random;

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

    public Customer saveCustomer(Customer customer) {
        Customer existingCustomer = customerRepository.findByEmailAndTcNo(customer.getEmail(), customer.getTcNo());
        if (existingCustomer != null) {
            Log log = new Log();
            log.setTimestamp(LocalDateTime.now());
            log.setLevel("E-mail_tcNo_email ERROR");
            log.setMessage("Customer already exists");
            logRepository.save(log);

            throw new DataIntegrityViolationException("Customer already exists");
        }

        customer.setCustomerNumber(new Random().nextInt(2147483647));

        return customerRepository.save(customer);
    }
    public List<Customer> getAllCustomer() {
        return customerRepository.findAll();
    }

    public void deleteCustomerById(Long id) {
        customerRepository.deleteById(id);
    }

    public Customer updateCustomer(Long id, Customer updatedCustomer) {
        try {
            Customer existingCustomer = customerRepository.findById(id)
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
        this.customerRepository = customerRepository;
        this.logRepository = logRepository;
    }


}
