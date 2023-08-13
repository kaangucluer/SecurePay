package com.firisbe.securepay.repository;

import com.firisbe.securepay.model.CreditCard;
import com.firisbe.securepay.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface CreditCardRepository extends JpaRepository <CreditCard,Long> {
    @Query("SELECT c.debtAmount FROM CreditCard c WHERE c.cardNumber = :cardNumber")
    BigDecimal getDebtAmountByCardNumber(@Param("cardNumber") int cardNumber);

    @Query("SELECT p FROM Payment p WHERE p.creditCard.cardNumber = :cardNumber")
    List<Payment> findPaymentsByCardNumber(@Param("cardNumber") Long cardNumber);
    List<CreditCard> findAllByOrderByPaymentDeadlineAsc();

    List<Payment> findByCardNumber(Long cardNumber);
}
