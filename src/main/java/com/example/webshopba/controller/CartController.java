package com.example.webshopba.controller;

import com.example.webshopba.model.Cart;
import com.example.webshopba.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
public class CartController {
    private final CartService cartService;

    @Autowired
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping("/get")
    public Cart getWarenkorb() {
        return cartService.getWarenkorb();
    }

    @PostMapping("/add")
    public ResponseEntity<?> addProductToCart(@RequestParam(name = "productId") Long productId, @RequestParam(name = "quantity") int quantity) {
        System.out.println("Produkt-ID: " + productId + ", Menge: " + quantity);
        try {
            Cart updatedCart = cartService.addProductToCart(productId, quantity);
            return ResponseEntity.ok().body(updatedCart);
        } catch (Exception e) {
            System.out.println("Fehler beim Hinzufügen des Produkts zum Warenkorb: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Fehler beim Hinzufügen des Produkts");
        }
    }
    @DeleteMapping("/clear")
    public ResponseEntity<?> clearCart() {
        try {
            Cart updatedCart = cartService.clearCart();
            return ResponseEntity.ok().body(updatedCart);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error clearing cart");
        }
    }

    @DeleteMapping("/remove/{productId}")
    public ResponseEntity<?> removeProductFromCart(@PathVariable Long productId) {
        try {
            Cart updatedCart = cartService.removeProductFromCart(productId);
            return ResponseEntity.ok(updatedCart);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to remove product from cart: " + e.getMessage());
        }
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateProductInCart(@RequestParam(name = "productId") Long productId, @RequestParam(name = "quantity") int quantity) {
        try {
            Cart updatedCart = cartService.updateProductInCart(productId, quantity);
            return ResponseEntity.ok(updatedCart);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to update product in cart: " + e.getMessage());
        }
    }
}
