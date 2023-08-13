package com.firisbe.securepay.repository;

import com.firisbe.securepay.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PaymentRepository extends JpaRepository<Payment,Long> {

}
