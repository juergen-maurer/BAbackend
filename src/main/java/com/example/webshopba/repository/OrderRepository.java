package com.example.webshopba.repository;

import com.example.webshopba.model.Bestellung;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Bestellung, Long> {
}
