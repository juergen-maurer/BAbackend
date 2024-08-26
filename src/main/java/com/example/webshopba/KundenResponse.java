package com.example.webshopba;

import com.example.webshopba.model.Address;
import com.example.webshopba.model.Bestellung;

import java.util.List;

public class KundenResponse {
    private Long id;
    private String email;
    private String firstName;
    private String lastName;
    private Address lastUsedAddress;
    private List<Bestellung> bestellungen;

    // Konstruktoren
    public KundenResponse(Long id, String email, String firstName, String lastName, Address lastUsedAddress, List<Bestellung> bestellungen) {
        this.id = id;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.lastUsedAddress = lastUsedAddress;
        this.bestellungen = bestellungen;
    }

    // Getter und Setter
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public Address getLastUsedAddress() {
        return lastUsedAddress;
    }

    public void setLastUsedAddress(Address lastUsedAddress) {
        this.lastUsedAddress = lastUsedAddress;
    }

    public void setBestellungen(List<Bestellung> bestellungen) {
        this.bestellungen = bestellungen;
    }

    public List<Bestellung> getBestellungen() {
        return bestellungen;
    }
}
