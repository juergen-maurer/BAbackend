package com.example.webshopba.repository;

import com.example.webshopba.model.Cart;
import com.example.webshopba.model.Kunden;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface KundenRepository extends JpaRepository<Kunden, Long> {
    Optional<Kunden> findByEmail(String email);
   Kunden findByWarenkorb(Cart cart);

}
