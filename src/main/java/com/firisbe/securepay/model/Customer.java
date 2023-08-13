package com.firisbe.securepay.model;

import io.swagger.annotations.ApiModel;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;
 /*
    Tanımladığım tablo sutunlarıma length değeri vererek programıma script yazılmasını önlüyorum.
    tcNo ve phoneNumber değişkenlerimi Uygulamamı Türkiye için düşünerek yaptım ve lenght değeri verdim.
 */
@Entity
@Getter
@Setter
@ToString
@Table(name = "customers")
@ApiModel(value="User Api Doc",description="Model")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long customer_id;

    @Column(nullable = false, unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long customerNumber;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(nullable = false, unique = true, length = 11)
    private String tcNo;

    @Column(nullable = false, unique = true, length = 50)
    private String email;

    @Column(nullable = false, length = 10)
    private int phoneNumber; //+90 değeri girilmez!!

    @Column(nullable = false, length = 255)
    private String address;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private List<CreditCard> creditCards = new ArrayList<>(); //Bir müşterinin birden fazla kredi kartı olabilir.

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private List<Payment> payments = new ArrayList<>(); // Bir müşteri birden fazla ödeme işlemi yapabilir ilişkisi.

}

