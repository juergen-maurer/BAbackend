package com.example.webshopba.repository;

import com.example.webshopba.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {
    Optional<Cart> findById(Long Id);

}