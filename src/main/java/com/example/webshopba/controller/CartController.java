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
    public ResponseEntity<Cart> getWarenkorb(@RequestParam(name = "cartId") Long cartId) {
        try {
            Cart cart = cartService.getWarenkorb(cartId);
            return ResponseEntity.ok(cart);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PostMapping("/add")
    public ResponseEntity<?> addProductToCart(@RequestParam(name = "cartId") Long cartId,@RequestParam(name = "productId") Long productId, @RequestParam(name = "quantity") int quantity) {

        //System.out.println("Produkt-ID: " + productId + ", Menge: " + quantity);
        try {
            Cart updatedCart = cartService.addProductToCart(cartId, productId, quantity);
            return ResponseEntity.ok().body(updatedCart);
        } catch (Exception e) {
            System.out.println("Fehler beim Hinzufügen des Produkts zum Warenkorb: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Fehler beim Hinzufügen des Produkts");
        }
    }


    @DeleteMapping("/clear")
    public ResponseEntity<?> clearCart(@RequestParam(name = "cartId") Long cartId) {
        try {
            Cart updatedCart = cartService.clearCart(cartId);
            return ResponseEntity.ok().body(updatedCart);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error clearing cart");
        }
    }


    @DeleteMapping("/remove/{productId}")
    public ResponseEntity<?> removeProductFromCart(@RequestParam(name = "cartId") Long cartId, @PathVariable Long productId) {
        try {
            Cart updatedCart = cartService.removeProductFromCart(cartId, productId);
            return ResponseEntity.ok(updatedCart);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to remove product from cart: " + e.getMessage());
        }
    }


    @PutMapping("/update")
    public ResponseEntity<?> updateProductInCart(@RequestParam(name = "cartId") Long cartId, @RequestParam(name = "productId") Long productId, @RequestParam(name = "quantity") int quantity) {
        try {
            Cart updatedCart = cartService.updateProductInCart(cartId, productId, quantity);
            return ResponseEntity.ok(updatedCart);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to update product in cart: " + e.getMessage());
        }
    }
}
