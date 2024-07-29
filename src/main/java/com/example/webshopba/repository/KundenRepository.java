package com.example.webshopba.repository;

import com.example.webshopba.model.Kunden;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KundenRepository extends JpaRepository<Kunden, Long> {
    Kunden findByUsername(String username);
}
