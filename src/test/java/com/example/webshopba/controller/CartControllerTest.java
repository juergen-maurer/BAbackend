package com.example.webshopba.controller;

import com.example.webshopba.WebshopBaApplication;
import com.example.webshopba.model.Cart;
import com.example.webshopba.service.CartService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


public class CartControllerTest {

    @Autowired
     private MockMvc mockMvc;

    @MockBean
    private CartService cartService;

    @Test
    void testGetWarenkorb() throws Exception {
        Long cartId = 1L;
        Cart cart = new Cart();
        cart.setId(cartId);

        when(cartService.getWarenkorb(cartId)).thenReturn(cart);

        mockMvc.perform(get("/api/cart/get")
                        .param("cartId", cartId.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(cartId));
    }

    @Test
    void testAddProductToCart() throws Exception {
        Long cartId = 1L;
        Long productId = 1L;
        int quantity = 2;
        Cart cart = new Cart();
        cart.setId(cartId);

        when(cartService.addProductToCart(cartId, productId, quantity)).thenReturn(cart);

        mockMvc.perform(post("/api/cart/add")
                        .param("cartId", cartId.toString())
                        .param("productId", productId.toString())
                        .param("quantity", String.valueOf(quantity)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(cartId));
    }

    @Test
    void testClearCart() throws Exception {
        Long cartId = 1L;
        Cart cart = new Cart();
        cart.setId(cartId);

        when(cartService.clearCart(cartId)).thenReturn(cart);

        mockMvc.perform(delete("/api/cart/clear")
                        .param("cartId", cartId.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(cartId));
    }

    @Test
    void testRemoveProductFromCart() throws Exception {
        Long cartId = 1L;
        Long productId = 1L;
        Cart cart = new Cart();
        cart.setId(cartId);

        when(cartService.removeProductFromCart(cartId, productId)).thenReturn(cart);

        mockMvc.perform(delete("/api/cart/remove/{productId}", productId)
                        .param("cartId", cartId.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(cartId));
    }

    @Test
    void testUpdateProductInCart() throws Exception {
        Long cartId = 1L;
        Long productId = 1L;
        int quantity = 2;
        Cart cart = new Cart();
        cart.setId(cartId);

        when(cartService.updateProductInCart(cartId, productId, quantity)).thenReturn(cart);

        mockMvc.perform(put("/api/cart/update")
                        .param("cartId", cartId.toString())
                        .param("productId", productId.toString())
                        .param("quantity", String.valueOf(quantity)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(cartId));
    }
}