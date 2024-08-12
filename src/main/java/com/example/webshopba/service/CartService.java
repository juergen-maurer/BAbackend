package com.example.webshopba.service;

import com.example.webshopba.model.Cart;
import com.example.webshopba.model.CartItem;
import com.example.webshopba.model.Product;
import com.example.webshopba.repository.CartItemRepository;
import com.example.webshopba.repository.CartRepository;
import com.example.webshopba.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class CartService {
    //private static final Logger logger = LoggerFactory.getLogger(CartService.class);
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;

    private final CartItemRepository cartItemRepository;

    @Autowired
    public CartService(CartRepository cartRepository, ProductRepository productRepository, CartItemRepository cartItemRepository) {
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
        this.cartItemRepository = cartItemRepository;
    }


    public Cart addProductToCart(Long cartId, Long productId, int quantity) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        Cart cart = getWarenkorb(cartId); // Verwendet die vorhandene Methode, um den aktuellen Warenkorb zu erhalten


        Optional<CartItem> existingCartItem = cartItemRepository.findByCartIdAndProductId(cart.getId(), productId);

        if (existingCartItem.isPresent()) {
            CartItem cartItem = existingCartItem.get();
            cartItem.setQuantity(cartItem.getQuantity() + quantity); // Menge erhöhen
            cartItemRepository.save(cartItem); // Aktualisiertes CartItem speichern
        } else {
            CartItem newCartItem = new CartItem();
            newCartItem.setCart(cart);
            newCartItem.setProduct(product);
            newCartItem.setQuantity(quantity);
            cartItemRepository.save(newCartItem); // Neues CartItem speichern
            cart.getCartItems().add(newCartItem); // Neues CartItem zum Warenkorb hinzufügen
        }

        return cartRepository.save(cart); // Aktualisierten Warenkorb speichern
    }
    public Cart getWarenkorb(Long cartId) {
        return cartRepository.findById(cartId).orElseThrow(() -> new EntityNotFoundException("Cart not found with id: " + cartId));
    }

    @Transactional
    public Cart clearCart(Long cartId) {
        Cart cart = getWarenkorb(cartId); // Annahme, dass diese Methode den aktuellen Warenkorb des Benutzers zurückgibt
        cartItemRepository.deleteByCartId(cart.getId()); // Löscht alle CartItems, die zum Warenkorb gehören
        return cartRepository.save(cart); // Speichert den aktualisierten Warenkorb in der Datenbank
        //urn cart; // Gibt den unveränderten Warenkorb zurück, da die CartItems direkt in der Datenbank gelöscht wurden
    }
    @Transactional
    public Cart removeProductFromCart(Long cartId, Long productId) {
        Cart cart = getWarenkorb(cartId); // Annahme, dass diese Methode den aktuellen Warenkorb des Benutzers zurückgibt
        Optional<CartItem> cartItemOptional = cart.getCartItems().stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst();

        if (cartItemOptional.isPresent()) {
            CartItem cartItemToRemove = cartItemOptional.get();
            cart.getCartItems().remove(cartItemToRemove); // Entfernt das CartItem aus der Liste
            cartItemRepository.delete(cartItemToRemove); // Löscht das CartItem aus der Datenbank
        }

        return cartRepository.save(cart); // Speichert den aktualisierten Warenkorb in der Datenbank
    }

    public Cart updateProductInCart(Long cartId, Long productId, int quantity) {
        Cart cart = getWarenkorb(cartId); // Annahme, dass diese Methode den aktuellen Warenkorb des Benutzers zurückgibt
        Optional<CartItem> cartItemOptional = cart.getCartItems().stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst();

        if (cartItemOptional.isPresent()) {
            CartItem cartItemToUpdate = cartItemOptional.get();
            cartItemToUpdate.setQuantity(quantity); // Setzt die Menge des CartItems
            cartItemRepository.save(cartItemToUpdate); // Speichert das aktualisierte CartItem in der Datenbank
        }

        return cartRepository.save(cart); // Speichert den aktualisierten Warenkorb in der Datenbank
    }
}
