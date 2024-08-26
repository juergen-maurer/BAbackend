package com.example.webshopba.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.util.List;

@Entity
public class BestellungsCart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JsonIgnore
    private Bestellung bestellung;

    @OneToMany(mappedBy = "bestellungsCart", cascade = CascadeType.ALL)
    private List<BestellungsCartItem> bestellungsCartItems;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Bestellung getBestellung() {
        return bestellung;
    }

    public void setBestellung(Bestellung bestellung) {
        this.bestellung = bestellung;
    }

    public List<BestellungsCartItem> getBestellungsCartItems() {
        return bestellungsCartItems;
    }

    public void setBestellungsCartItems(List<BestellungsCartItem> bestellungsCartItems) {
        this.bestellungsCartItems = bestellungsCartItems;
    }
}