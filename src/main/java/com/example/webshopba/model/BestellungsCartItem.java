package com.example.webshopba.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
public class BestellungsCartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "bestellungs_cart_id")
    @JsonIgnore
    private BestellungsCart bestellungsCart;

    // Other fields, getters, and setters
    @ManyToOne
    private Product product;
    private int quantity;
    private double price;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BestellungsCart getBestellungsCart() {
        return bestellungsCart;
    }

    public void setBestellungsCart(BestellungsCart bestellungsCart) {
        this.bestellungsCart = bestellungsCart;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
