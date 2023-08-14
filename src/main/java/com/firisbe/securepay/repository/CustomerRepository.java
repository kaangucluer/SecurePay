package com.firisbe.securepay.repository;

import com.firisbe.securepay.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CustomerRepository extends JpaRepository<Customer,Long> {

    Customer findByEmailAndTcNo(String email, String tcNo);
}
