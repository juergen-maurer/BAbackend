package com.example.webshopba.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;


import java.util.ArrayList;
import java.util.List;


@Entity
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @OneToMany(mappedBy = "cart")
    @JsonManagedReference
    private List<CartItem> cartItems = new ArrayList<>();

    @ManyToOne
    @JsonIgnore
    private Kunden kunde;


    // Getter und Setter für items
    public List<CartItem> getCartItems() {
        return cartItems;
    }

    public void setCartItems(List<CartItem> cartItems) {
        this.cartItems = cartItems;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setKunde(Kunden kunde) {
        this.kunde = kunde;
    }

    public Kunden getKunde() {
        return kunde;
    }
}
    // Konstruktoren, Getter und Setter

