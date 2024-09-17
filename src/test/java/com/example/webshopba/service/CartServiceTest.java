package com.example.webshopba.service;

import com.example.webshopba.model.Cart;
import com.example.webshopba.model.CartItem;
import com.example.webshopba.model.Product;
import com.example.webshopba.repository.CartItemRepository;
import com.example.webshopba.repository.CartRepository;
import com.example.webshopba.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CartServiceTest {

    @Mock
    private CartRepository cartRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private CartItemRepository cartItemRepository;

    @InjectMocks
    private CartService cartService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void addProductToCart() {
        Long cartId = 1L;
        Long productId = 1L;
        int quantity = 2;

        Product product = new Product();
        product.setId(productId);
        Cart cart = new Cart();
        cart.setId(cartId);
        CartItem cartItem = new CartItem();
        cartItem.setProduct(product);
        cartItem.setQuantity(1);
        cart.getCartItems().add(cartItem);

        when(productRepository.findById(productId)).thenReturn(Optional.of(product));
        when(cartRepository.findById(cartId)).thenReturn(Optional.of(cart));
        when(cartItemRepository.findByCartIdAndProductId(cartId, productId)).thenReturn(Optional.of(cartItem));
        when(cartItemRepository.save(any(CartItem.class))).thenReturn(cartItem);
        when(cartRepository.save(any(Cart.class))).thenReturn(cart);

        Cart updatedCart = cartService.addProductToCart(cartId, productId, quantity);

        assertNotNull(updatedCart);
        assertEquals(3, cartItem.getQuantity());
        verify(cartItemRepository, times(1)).save(cartItem);
        verify(cartRepository, times(1)).save(cart);
    }

    @Test
    void getWarenkorb() {
        Long cartId = 1L;
        Cart cart = new Cart();

        when(cartRepository.findById(cartId)).thenReturn(Optional.of(cart));

        Cart foundCart = cartService.getWarenkorb(cartId);

        assertNotNull(foundCart);
        verify(cartRepository, times(1)).findById(cartId);
    }

    @Test
    void getWarenkorb_NotFound() {
        Long cartId = 1L;

        when(cartRepository.findById(cartId)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            cartService.getWarenkorb(cartId);
        });

        assertEquals("Cart not found with id: " + cartId, exception.getMessage());
    }

    @Test
    void clearCart() {
        Long cartId = 1L;
        Cart cart = new Cart();
        cart.setId(cartId);

        when(cartRepository.findById(cartId)).thenReturn(Optional.of(cart));
        doNothing().when(cartItemRepository).deleteByCartId(cartId);
        when(cartRepository.save(any(Cart.class))).thenReturn(cart);

        Cart clearedCart = cartService.clearCart(cartId);

        assertNotNull(clearedCart);
        assertTrue(clearedCart.getCartItems().isEmpty());
        verify(cartItemRepository, times(1)).deleteByCartId(cartId);
        verify(cartRepository, times(1)).save(cart);
    }

    @Test
    void removeProductFromCart() {
        Long cartId = 1L;
        Long productId = 1L;
        Cart cart = new Cart();
        cart.setId(cartId);
        Product product = new Product();
        product.setId(productId);
        CartItem cartItem = new CartItem();
        cartItem.setProduct(product);
        cart.getCartItems().add(cartItem);

        when(cartRepository.findById(cartId)).thenReturn(Optional.of(cart));
        when(cartItemRepository.findByCartIdAndProductId(cartId, productId)).thenReturn(Optional.of(cartItem));
        doNothing().when(cartItemRepository).delete(cartItem);
        when(cartRepository.save(any(Cart.class))).thenReturn(cart);

        Cart updatedCart = cartService.removeProductFromCart(cartId, productId);

        assertNotNull(updatedCart);
        assertTrue(cart.getCartItems().isEmpty());
        verify(cartItemRepository, times(1)).delete(cartItem);
        verify(cartRepository, times(1)).save(cart);
    }

    @Test
    void updateProductInCart() {
        Long cartId = 1L;
        Long productId = 1L;
        int quantity = 5;
        Cart cart = new Cart();
        Product product = new Product();
        product.setId(productId);
        CartItem cartItem = new CartItem();
        cartItem.setProduct(product);
        cart.getCartItems().add(cartItem);

        when(cartRepository.findById(cartId)).thenReturn(Optional.of(cart));
        when(cartItemRepository.findByCartIdAndProductId(cartId, productId)).thenReturn(Optional.of(cartItem));
        when(cartItemRepository.save(any(CartItem.class))).thenReturn(cartItem);
        when(cartRepository.save(any(Cart.class))).thenReturn(cart);

        Cart updatedCart = cartService.updateProductInCart(cartId, productId, quantity);

        assertNotNull(updatedCart);
        assertEquals(quantity, cartItem.getQuantity());
        verify(cartItemRepository, times(1)).save(cartItem);
        verify(cartRepository, times(1)).save(cart);
    }
}