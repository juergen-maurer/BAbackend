package com.example.webshopba.model;

import com.example.webshopba.enums.PaymentMethod;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.LocalDateTime;
@Entity
public class Bestellung {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private Address address;

    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    @OneToOne(mappedBy = "bestellung", cascade = CascadeType.ALL)
    private BestellungsCart bestellungsCart;

    @ManyToOne
    @JsonIgnore
    private Kunden kunde;

    @Column(nullable = false)
    private double total;
    @Column()
    private LocalDateTime orderDateTime;

    public Bestellung(){
        this.orderDateTime = LocalDateTime.now();
    }

    public LocalDateTime getOrderDateTime() {
        return orderDateTime;
    }

    public void setOrderDateTime(LocalDateTime orderDateTime) {
        this.orderDateTime = orderDateTime;
    }

    public Address getAddress() {
        return address;
    }

    public double getTotal() {
        return total;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public Long getId() {
        return id;
    }




    public void setTotal(double total) {
        this.total = total;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setAddress(Address address) {
        this.address = address;
    }


    public BestellungsCart getBestellungsCart() {
        return bestellungsCart;
    }

    public void setBestellungsCart(BestellungsCart bestellungsCart) {
        this.bestellungsCart = bestellungsCart;
    }

    public Kunden getKunde() {
        return kunde;
    }

    public void setKunde(Kunden kunde) {
        this.kunde = kunde;
    }


}
