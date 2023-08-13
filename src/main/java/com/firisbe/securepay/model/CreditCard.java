package com.firisbe.securepay.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@ToString
@Table(name = "credit_cards")
public class CreditCard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long creditCard_id;

    @Column(nullable = false, unique = true, length = 16)
    private Long cardNumber;

    @Column
    private LocalDate expirationDate;

    @Column(nullable = false, length = 3)
    private String cvv;

    @Column
    private LocalDate paymentDeadline;

    @Column
    private BigDecimal debtAmount;

    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @OneToOne(mappedBy = "creditCard")
    private Payment payment;

}

