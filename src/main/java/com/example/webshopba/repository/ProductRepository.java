package com.example.webshopba.repository;

import com.example.webshopba.enums.ProductCategory;
import com.example.webshopba.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByCategory(ProductCategory category);

}
