package com.example.webshopba.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.List;

@Entity
public class Kunden {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String password;
    private String email;

    private String firstName;


    private String lastName;
    @Embedded
    private Address lastUsedAddress;
    @OneToMany(mappedBy = "kunde", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Bestellung> bestellungen;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private Cart warenkorb;

    // Konstruktoren, Getter und Setter
    public Kunden() {
    }

    // Getter und Setter


    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getWarenkorbId() {
        return warenkorb.getId();
    }

    public void setWarenkorb(Cart warenkorb) {
        this.warenkorb = warenkorb;
    }

    public Long getId() {
        return id;
    }


    public Address getLastUsedAddress() {
        return lastUsedAddress;
    }

    public List<Bestellung> getBestellungen() {
        return bestellungen;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public void setLastUsedAddress(Address lastUsedAddress) {
        this.lastUsedAddress = lastUsedAddress;
    }
}
