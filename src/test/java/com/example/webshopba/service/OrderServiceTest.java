package com.example.webshopba.service;

import com.example.webshopba.OrderRequest;
import com.example.webshopba.OrderResponse;
import com.example.webshopba.enums.PaymentMethod;
import com.example.webshopba.model.*;
import com.example.webshopba.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private KundenRepository kundenRepository;

    @Mock
    private CartRepository cartRepository;

    @Mock
    private BestellungsCartRepository bestellungsCartRepository;

    @Mock
    private BestellungsCartItemRepository bestellungsCartItemRepository;

    @Mock
    private CartItemRepository cartItemRepository;

    @InjectMocks
    private OrderService orderService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @Transactional
    void processOrder() {
        Long cartId = 1L;
        Long customerId = 1L;
        Cart cart = new Cart();
        cart.setId(cartId);
        Kunden kunde = new Kunden();
        kunde.setId(customerId);
        cart.setKunde(kunde);
        Product product = new Product();
        product.setId(1L);
        product.setPrice(100.0);
        CartItem cartItem = new CartItem();
        cartItem.setProduct(product);
        cartItem.setQuantity(2);
        cart.setCartItems(Arrays.asList(cartItem));
        OrderRequest orderRequest = new OrderRequest();
        orderRequest.setWarenkorbId(cartId);
        orderRequest.setAddress(new Address()); // Use Address object
        orderRequest.setPaymentMethod(PaymentMethod.CREDIT_CARD);
        orderRequest.setTotal(200.0);
        orderRequest.setCartItems(Arrays.asList(cartItem));

        when(cartRepository.findById(cartId)).thenReturn(Optional.of(cart));
        when(kundenRepository.findByWarenkorb(cart)).thenReturn(kunde);
        Bestellung bestellung = new Bestellung();
        bestellung.setId(1L); // Initialize Bestellung ID
        when(orderRepository.save(any(Bestellung.class))).thenReturn(bestellung);
        when(bestellungsCartRepository.save(any(BestellungsCart.class))).thenReturn(new BestellungsCart());

        OrderResponse response = orderService.processOrder(orderRequest);

        assertNotNull(response);
        assertEquals("SUCCESS", response.getStatus());
        verify(cartRepository, times(1)).findById(cartId);
        verify(kundenRepository, times(1)).findByWarenkorb(cart);
        verify(orderRepository, times(2)).save(any(Bestellung.class));
        verify(bestellungsCartRepository, times(1)).save(any(BestellungsCart.class));
        verify(cartItemRepository, times(1)).deleteByCartId(cartId);
    }

    @Test
    void processOrder_CartNotFound() {
        Long cartId = 1L;
        OrderRequest orderRequest = new OrderRequest();
        orderRequest.setWarenkorbId(cartId);

        when(cartRepository.findById(cartId)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            orderService.processOrder(orderRequest);
        });

        assertEquals("Cart not found", exception.getMessage());
        verify(cartRepository, times(1)).findById(cartId);
    }

    @Test
    @Transactional
    void processOrder_PaymentFailure() {
        Long cartId = 1L;
        Long customerId = 1L;
        Cart cart = new Cart();
        cart.setId(cartId);
        Kunden kunde = new Kunden();
        kunde.setId(customerId);
        cart.setKunde(kunde);
        Product product = new Product();
        product.setId(1L);
        product.setPrice(100.0);
        CartItem cartItem = new CartItem();
        cartItem.setProduct(product);
        cartItem.setQuantity(2);
        cart.setCartItems(Arrays.asList(cartItem));
        OrderRequest orderRequest = new OrderRequest();
        orderRequest.setWarenkorbId(cartId);
        orderRequest.setAddress(new Address()); // Use Address object
        orderRequest.setPaymentMethod(PaymentMethod.CREDIT_CARD);
        orderRequest.setTotal(200.0);
        orderRequest.setCartItems(Arrays.asList(cartItem));

        when(cartRepository.findById(cartId)).thenReturn(Optional.of(cart));
        when(kundenRepository.findByWarenkorb(cart)).thenReturn(kunde);
        Bestellung bestellung = new Bestellung();
        bestellung.setId(1L); // Initialize Bestellung ID
        when(orderRepository.save(any(Bestellung.class))).thenReturn(bestellung);
        when(bestellungsCartRepository.save(any(BestellungsCart.class))).thenReturn(new BestellungsCart());

        // Mock payment failure
        OrderService orderServiceSpy = spy(orderService);
        doReturn(false).when(orderServiceSpy).processPayment(any(PaymentMethod.class));

        OrderResponse response = orderServiceSpy.processOrder(orderRequest);

        assertNotNull(response);
        assertEquals("FAILURE", response.getStatus());
        verify(cartRepository, times(1)).findById(cartId);
        verify(kundenRepository, times(1)).findByWarenkorb(cart);
        verify(orderRepository, times(2)).save(any(Bestellung.class));
        verify(bestellungsCartRepository, times(1)).save(any(BestellungsCart.class));
        verify(cartItemRepository, times(1)).deleteByCartId(cartId);
    }
}
