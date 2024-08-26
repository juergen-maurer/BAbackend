package com.example.webshopba;

import com.example.webshopba.enums.PaymentMethod;
import com.example.webshopba.model.Address;
import com.example.webshopba.model.CartItem;

import java.util.List;

public class OrderRequest {
    private Long warenkorbId;
    private Address address;
    private PaymentMethod paymentMethod;
    private List<CartItem> cartItems;
    private double total;

    // Getters and Setters

    public List<CartItem> getCartItems() {
        return cartItems;
    }

    public Address getAddress() {
        return address;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public double getTotal() {
        return total;
    }
    public Long getWarenkorbId() {
        return warenkorbId;
    }

}