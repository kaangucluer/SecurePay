package com.firisbe.securepay.controller;

import com.firisbe.securepay.model.CreditCard;
import com.firisbe.securepay.model.Payment;
import com.firisbe.securepay.service.CreditCardService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/creditCard")
public class CreditCardController {
    private final CreditCardService creditCardService;

    public CreditCardController(CreditCardService creditCardService) {
        this.creditCardService = creditCardService;
    }

    @PostMapping("/add")
    public ResponseEntity<CreditCard> addCreditCard(@RequestBody CreditCard creditCard) {
        try {
            CreditCard savedCreditCard = creditCardService.addCreditCard(creditCard);
            return new ResponseEntity<>(savedCreditCard, HttpStatus.CREATED);
        } catch (IllegalArgumentException ex) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


    @GetMapping("/allByPaymentDeadlineAsc")
    public ResponseEntity<List<CreditCard>> getAllCreditCardsByPaymentDeadlineAsc() {
        List<CreditCard> creditCards = creditCardService.getAllCreditCardsByPaymentDeadlineAsc();
        return ResponseEntity.status(HttpStatus.OK).body(creditCards);
    }


    @GetMapping("/byCardNumber/{cardNumber}")
    public ResponseEntity<List<Payment>> getPaymentsByCardNumber(@PathVariable Long cardNumber) {
        List<Payment> payments = creditCardService.getPaymentsByCardNumber(Long.valueOf(cardNumber));
        return ResponseEntity.ok(payments);
    }
}