package com.firisbe.securepay.service;

import com.firisbe.securepay.model.CreditCard;
import com.firisbe.securepay.model.Customer;
import com.firisbe.securepay.model.Log;
import com.firisbe.securepay.model.Payment;
import com.firisbe.securepay.repository.CreditCardRepository;
import com.firisbe.securepay.repository.CustomerRepository;
import com.firisbe.securepay.repository.LogRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CreditCardService {
    private final CreditCardRepository creditCardRepository;
    private final LogRepository logRepository;
    private final CustomerRepository customerRepository;


    public CreditCardService(CreditCardRepository creditCardRepository, LogRepository logRepository, CustomerRepository customerRepository) {
        this.creditCardRepository = creditCardRepository;
        this.logRepository = logRepository;
        this.customerRepository = customerRepository;
    }

    public CreditCard addCreditCard(CreditCard creditCard) {
        try {
            Customer savedCustomer = creditCard.getCustomer();
            if (savedCustomer != null) {
                savedCustomer = customerRepository.save(savedCustomer);
                creditCard.setCustomer(savedCustomer);
            }
            return creditCardRepository.save(creditCard);
        } catch (DataIntegrityViolationException ex) {
            String errorMessage = "Error: creditCard already exists.";
            Log log = new Log();
            log.setTimestamp(LocalDateTime.now());
            log.setLevel("ccv_cardNumber ERROR");
            log.setMessage(errorMessage);
            logRepository.save(log);
            throw new IllegalArgumentException(errorMessage, ex);
        }
    }

    public List<CreditCard> getAllCreditCardsByPaymentDeadlineAsc() {
        return creditCardRepository.findAllByOrderByPaymentDeadlineAsc();
    }

    public List<Payment> getPaymentsByCardNumber(Long cardNumber) {
        return creditCardRepository.findPaymentsByCardNumber(cardNumber);
    }
}