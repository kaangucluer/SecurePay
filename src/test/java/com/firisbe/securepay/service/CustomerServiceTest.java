package com.firisbe.securepay.service;

import com.firisbe.securepay.model.Customer;
import com.firisbe.securepay.repository.CustomerRepository;
import com.firisbe.securepay.repository.LogRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;
    @Mock
    private LogRepository logRepository;
    @InjectMocks
    private CustomerService customerService;

    @BeforeEach
    public void setUp() {
        customerService = new CustomerService(customerRepository, logRepository);
    }

    @Test
    public void getAllCustomer_shouldReturnEmptyList() {
        // Given
        when(customerRepository.findAll()).thenReturn(List.of());

        // When
        List<Customer> allCustomers = customerService.getAllCustomer();

        // Then
        assertEquals(0, allCustomers.size());
    }

    @Test
    public void getAllCustomer_shouldReturnOneCustomer() {
        // Given
        Customer customer = new Customer();
        customer.setName("Kaan Gucluer");
        customer.setTcNo("1234567890");
        customer.setEmail("gucluer.kaan71@gmail.com");
        customer.setPhoneNumber("05466718283");
        customer.setAddress("Gebze");

        when(customerRepository.findAll()).thenReturn(List.of(customer));

        // When
        List<Customer> allCustomers = customerService.getAllCustomer();

        // Then
        assertEquals(1, allCustomers.size());
        assertEquals(customer, allCustomers.get(0));
    }




    /******save*/
    @Test
    public void saveCustomer_shouldSaveCustomer() {
        // Given
        Customer customer = new Customer();
        customer.setName("Kaan Gucluer");
        customer.setTcNo("1234567890");
        customer.setEmail("gucluer.kaan71@gmail.com");
        customer.setPhoneNumber("05466718283");
        customer.setAddress("Gebze");

        when(customerRepository.save(customer)).thenReturn(customer);

        // When
        Customer savedCustomer = customerService.saveCustomer(customer);

        // Then
        assertEquals(customer, savedCustomer);
    }

    @Test
    public void saveCustomer_shouldThrowExceptionWhenCustomerAlreadyExists() {
        // Given
        Customer customer = new Customer();
        customer.setName("JKaan Gucluer");
        customer.setTcNo("1234567890");
        customer.setEmail("gucluer.kaan71@gmail.com");
        customer.setPhoneNumber("05466718283");
        customer.setAddress("Gebze");

        when(customerRepository.save(customer)).thenThrow(new DataIntegrityViolationException("Customer already exists"));

        // When
        assertThrows(DataIntegrityViolationException.class, () -> customerService.saveCustomer(customer));
    }
}