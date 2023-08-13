package com.firisbe.securepay.service;

import com.firisbe.securepay.model.CreditCard;
import com.firisbe.securepay.model.Log;
import com.firisbe.securepay.model.Payment;
import com.firisbe.securepay.repository.CreditCardRepository;
import com.firisbe.securepay.repository.LogRepository;
import com.firisbe.securepay.repository.PaymentRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class PaymentService {
    private final PaymentRepository paymentRepository;
    private final LogRepository logRepository;

    public Payment savePayment(Payment payment) {
        BigDecimal paymentAmount = payment.getPaymentAmount();
        CreditCard creditCard = payment.getCreditCard();
        if (creditCard == null) {
            String errorMessage = "Error: Credit Card is missing.";
            Log log = new Log();
            log.setTimestamp(LocalDateTime.now());
            log.setLevel("Payment-Debt ERROR");
            log.setMessage(errorMessage);
            logRepository.save(log);
            throw new IllegalArgumentException(errorMessage);
        }
        if (paymentAmount.compareTo(BigDecimal.ZERO) <= 0 && creditCard.getDebtAmount().compareTo(BigDecimal.ZERO) <= 0) {
            String errorMessage = "Error: Payment and Debt amount is invalid for zero debt.";
            Log log = new Log();
            log.setTimestamp(LocalDateTime.now());
            log.setLevel("Payment-Debt ERROR");
            log.setMessage(errorMessage);
            logRepository.save(log);
            throw new IllegalArgumentException(errorMessage);
        }
        BigDecimal newDebtAmount = creditCard.getDebtAmount().subtract(paymentAmount);
        creditCard.setDebtAmount(newDebtAmount);

        LocalDate paymentDate = LocalDate.now();
        payment.setPaymentDate(paymentDate);

        return paymentRepository.save(payment);
    }

    public List<Payment> getAllPayment(){
        return paymentRepository.findAll();
    }



    public PaymentService(PaymentRepository paymentRepository, LogRepository logRepository) {
        this.paymentRepository = paymentRepository;
        this.logRepository = logRepository;
    }
}
