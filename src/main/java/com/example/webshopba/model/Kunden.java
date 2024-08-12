package com.example.webshopba.model;

import jakarta.persistence.*;

@Entity
public class Kunden {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String password;
    private String email;
    private String firstName;
    private String lastName;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Cart warenkorb;

    // Konstruktoren, Getter und Setter
    public Kunden() {
    }

    public Kunden(String password, String email, String firstName, String lastName) {

        this.password = password;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
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
}
