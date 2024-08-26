package com.example.webshopba.repository;

import com.example.webshopba.model.BestellungsCartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BestellungsCartItemRepository extends JpaRepository<BestellungsCartItem, Long> {
}
