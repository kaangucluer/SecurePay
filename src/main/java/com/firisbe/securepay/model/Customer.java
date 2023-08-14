package com.firisbe.securepay.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
@Table(name = "customers")

public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long customer_id;

    @Column(unique = true)
    private long customerNumber;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(nullable = false, unique = true, length = 11)
    private String tcNo;

    @Column(nullable = false, unique = true, length = 50)
    private String email;

    @Column(nullable = false, unique = true, length = 10)
    private String phoneNumber; //+90 değeri girilmez!!

    @Column(nullable = false, length = 255)
    private String address;

    @OneToMany(mappedBy = "customer", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<CreditCard> creditCards = new ArrayList<>(); //Bir müşterinin birden fazla kredi kartı olabilir.

    @OneToMany(mappedBy = "customer", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Payment> payments = new ArrayList<>(); // Bir müşteri birden fazla ödeme işlemi yapabilir ilişkisi.

}

