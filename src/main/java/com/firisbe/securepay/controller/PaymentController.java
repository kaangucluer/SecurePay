package com.firisbe.securepay.controller;

import com.firisbe.securepay.model.Payment;
import com.firisbe.securepay.service.PaymentService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/payment")
public class PaymentController {
    private final PaymentService paymentService;

    @PostMapping("/save")
    public ResponseEntity<Payment> savePayment(@RequestBody Payment payment) {
        try {
            Payment savedPayment = paymentService.savePayment(payment);
            return new ResponseEntity<>(savedPayment, HttpStatus.CREATED);
        } catch (IllegalArgumentException ex) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<Payment>> getAllPayment(){
        return ResponseEntity.status(HttpStatus.OK)
                             .body(paymentService.getAllPayment());
    }



    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;

    }
}
