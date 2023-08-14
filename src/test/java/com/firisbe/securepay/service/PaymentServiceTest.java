package com.firisbe.securepay.service;

import com.firisbe.securepay.model.CreditCard;
import com.firisbe.securepay.model.Payment;
import com.firisbe.securepay.repository.CreditCardRepository;
import com.firisbe.securepay.repository.LogRepository;
import com.firisbe.securepay.repository.PaymentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PaymentServiceTest {

    @Mock
    private PaymentRepository paymentRepository;

    @Mock
    private CreditCardRepository creditCardRepository;

    @Mock
    private LogRepository logRepository;

    private PaymentService paymentService;

    @BeforeEach
    public void setUp() {
        paymentService = new PaymentService(paymentRepository, logRepository);
    }

    @Test
    public void savePayment_shouldSavePayment() {
        // Given
        Payment payment = new Payment();
        payment.setPaymentAmount(new BigDecimal(100));
        CreditCard creditCard = new CreditCard();
        creditCard.setDebtAmount(new BigDecimal(1000));
        // Check if credit card is null
        if (payment.getCreditCard() == null) {
            throw new IllegalArgumentException("Credit card is missing.");
        }
        when(creditCardRepository.findById(1L)).thenReturn(java.util.Optional.of(creditCard));

        // When
        Payment savedPayment = paymentService.savePayment(payment);

        // Then
        assertNotNull(savedPayment);
        assertEquals(payment.getPaymentAmount(), savedPayment.getPaymentAmount());
        assertEquals(creditCard.getDebtAmount().subtract(payment.getPaymentAmount()), savedPayment.getCreditCard().getDebtAmount());

        // Test null credit card
        payment.setCreditCard(null);
    }

    @Test
    public void savePayment_shouldThrowExceptionWhenCreditCardIsNull() {
        // Given
        Payment payment = new Payment();
        payment.setPaymentAmount(new BigDecimal(100));

        // When
        assertThrows(IllegalArgumentException.class, () -> paymentService.savePayment(payment));
    }

    @Test
    public void savePayment_shouldThrowExceptionWhenPaymentAmountIsZeroAndDebtAmountIsZero() {
        // Given
        Payment payment = new Payment();
        payment.setPaymentAmount(new BigDecimal(0));
        payment.setCreditCard(new CreditCard());
        payment.getCreditCard().setDebtAmount(new BigDecimal(0));

        // When
        assertThrows(IllegalArgumentException.class, () -> paymentService.savePayment(payment));
    }

    @Test
    public void savePayment_shouldThrowExceptionWhenPaymentRepositoryThrowsException() {
        // Given
        Payment payment = new Payment();
        payment.setPaymentAmount(new BigDecimal(100));
        CreditCard creditCard = new CreditCard();
        creditCard.setDebtAmount(new BigDecimal(1000));
        when(creditCardRepository.findById(1L)).thenReturn(java.util.Optional.of(creditCard));
        doThrow(new IllegalArgumentException()).when(paymentRepository).save(payment);

        // When
        assertThrows(NullPointerException.class, () -> paymentService.savePayment(payment));
    }
}
