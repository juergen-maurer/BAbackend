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

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public void setCartItems(List<CartItem> cartItems) {
        this.cartItems = cartItems;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public void setWarenkorbId(Long warenkorbId) {
        this.warenkorbId = warenkorbId;
    }
}