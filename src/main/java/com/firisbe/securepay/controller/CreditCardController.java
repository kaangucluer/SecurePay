package com.firisbe.securepay.controller;

import com.firisbe.securepay.model.CreditCard;
import com.firisbe.securepay.model.Payment;
import com.firisbe.securepay.service.CreditCardService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/creditCard")
public class CreditCardController {
    private final CreditCardService creditCardService;

    public CreditCardController(CreditCardService creditCardService) {
        this.creditCardService = creditCardService;
    }


    @GetMapping("/allByPaymentDeadlineAsc")
    public ResponseEntity<List<CreditCard>> getAllCreditCardsByPaymentDeadlineAsc() {
        List<CreditCard> creditCards = creditCardService.getAllCreditCardsByPaymentDeadlineAsc();
        return ResponseEntity.status(HttpStatus.OK).body(creditCards);
    }


    @GetMapping("/byCardNumber/{cardNumber}")
    public ResponseEntity<List<Payment>> getPaymentsByCardNumber(@PathVariable String cardNumber) {
        List<Payment> payments = creditCardService.getPaymentsByCardNumber(cardNumber);
        return ResponseEntity.ok(payments);
    }
}