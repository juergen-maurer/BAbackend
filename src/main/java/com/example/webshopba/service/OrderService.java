package com.example.webshopba.service;

import com.example.webshopba.OrderRequest;
import com.example.webshopba.OrderResponse;
import com.example.webshopba.enums.PaymentMethod;
import com.example.webshopba.model.*;
import com.example.webshopba.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final KundenRepository kundenRepository;
    private final CartRepository cartRepository;
    private final BestellungsCartRepository bestellungsCartRepository;

    private final BestellungsCartRepository bestellungsCartItemRepository;
    private final CartItemRepository cartItemRepository;




    @Autowired
    public OrderService(CartItemRepository cartItemRepository ,CartRepository cartRepository ,BestellungsCartRepository bestellungsCartItemRepository, KundenRepository kundenRepository, BestellungsCartRepository bestellungsCartRepository, OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
        this.kundenRepository = kundenRepository;
        this.bestellungsCartRepository = bestellungsCartRepository;
        this.bestellungsCartItemRepository=bestellungsCartItemRepository;
    }

    @Transactional
    public OrderResponse processOrder(OrderRequest orderRequest) {
        Cart cart = cartRepository.findById(orderRequest.getWarenkorbId()).orElseThrow(() -> new RuntimeException("Cart not found"));
        Kunden kunde = kundenRepository.findByWarenkorb(cart);

        // Create and save the order
        Bestellung customerOrder = new Bestellung();
        customerOrder.setAddress(orderRequest.getAddress());
        customerOrder.setPaymentMethod(orderRequest.getPaymentMethod());
        customerOrder.setTotal(orderRequest.getTotal());
        customerOrder.setKunde(kunde);
        //orderRepository.save(customerOrder);
        customerOrder = orderRepository.save(customerOrder); // Ensure the saved order is returned with ID


        // Create and save the BestellungsCart
        BestellungsCart bestellungsCart = new BestellungsCart();
        bestellungsCart.setBestellung(customerOrder);
        bestellungsCart.setBestellungsCartItems(cart.getCartItems().stream().map(cartItem -> {
            BestellungsCartItem bestellungsCartItem = new BestellungsCartItem();
            bestellungsCartItem.setBestellungsCart(bestellungsCart);
            bestellungsCartItem.setProduct(cartItem.getProduct());
            bestellungsCartItem.setQuantity(cartItem.getQuantity());
            bestellungsCartItem.setPrice(cartItem.getProduct().getPrice());
            return bestellungsCartItem;

        }).collect(Collectors.toList()));
        bestellungsCartRepository.save(bestellungsCart);

        customerOrder.setBestellungsCart(bestellungsCart);
        System.out.println(bestellungsCart);
        this.orderRepository.save(customerOrder);

        kunde.setLastUsedAddress(orderRequest.getAddress());

        // Clear customer's cart
        cartItemRepository.deleteByCartId(cart.getId());

        // Process payment (mock implementation)
        boolean paymentSuccess = processPayment(orderRequest.getPaymentMethod());

        // Update inventory (mock implementation)
        //updateInventory(orderRequest.getCartItems());

        // Create response
        OrderResponse response = new OrderResponse();
        response.setOrderId(customerOrder.getId().toString());
        response.setStatus(paymentSuccess ? "SUCCESS" : "FAILURE");
        response.setMessage(paymentSuccess ? "Order placed successfully" : "Payment failed");

        return response;
    }

    public boolean processPayment(PaymentMethod paymentMethod) {
        // Mock payment processing logic
        return true;
    }

    public void updateInventory(List<CartItem> cartItems) {
        // Mock inventory update logic
    }
}