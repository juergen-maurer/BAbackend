package com.example.webshopba;

public class OrderResponse {
    private String orderId;
    private String status;
    private String message;

    // Getters and Setters




    public void setMessage(String message) {
        this.message = message;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
