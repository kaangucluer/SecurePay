package com.firisbe.securepay.service;

import com.firisbe.securepay.model.CreditCard;
import com.firisbe.securepay.model.Payment;
import com.firisbe.securepay.repository.CreditCardRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CreditCardService {
    private final CreditCardRepository creditCardRepository;

    public CreditCardService(CreditCardRepository creditCardRepository) {
        this.creditCardRepository = creditCardRepository;
    }


    public List<CreditCard> getAllCreditCardsByPaymentDeadlineAsc() {
        return creditCardRepository.findAllByOrderByPaymentDeadlineAsc();
    }

    public List<Payment> getPaymentsByCardNumber(String cardNumber) {
        return creditCardRepository.findByCardNumber(Integer.parseInt(cardNumber));
    }
}