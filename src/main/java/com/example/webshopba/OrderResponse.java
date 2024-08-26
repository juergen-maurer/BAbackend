package com.example.webshopba;

public class OrderResponse {
    private String orderId;
    private String status;
    private String message;

    // Getters and Setters

    public String getMessage() {
        return message;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getStatus() {
        return status;
    }

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
