package com.example.webshopba.controller;

import com.example.webshopba.OrderRequest;
import com.example.webshopba.OrderResponse;
import com.example.webshopba.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
public class OrderController {
    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public OrderResponse processCheckout(@RequestBody OrderRequest orderRequest) {
        return orderService.processOrder(orderRequest);
    }
}