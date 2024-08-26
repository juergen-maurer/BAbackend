package com.example.webshopba.model;

public class Address {
    private String street;
    private String city;
    private String postalCode;
    private String country;
    private String houseNumber;

    private String addressfirstName;
    private String addresslastName;


    // Getters and Setters


    public String getAddresslastName() {
        return addresslastName;
    }

    public String getAddressfirstName() {
        return addressfirstName;
    }


    public void setAddresslastName(String addresslastName) {
        this.addresslastName = addresslastName;
    }

    public void setAddressfirstName(String addressfirstName) {
        this.addressfirstName = addressfirstName;
    }

    public String getStreet() {
        return street;
    }

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public void setCity(String city) {
        this.city = city;
    }


    public void setCountry(String country) {
        this.country = country;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }
}
