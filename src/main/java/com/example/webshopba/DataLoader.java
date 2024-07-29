package com.example.webshopba;

import com.example.webshopba.enums.ProductCategory;
import com.example.webshopba.model.Product;
import com.example.webshopba.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DataLoader implements CommandLineRunner {

    private final ProductService productService;

    @Autowired
    public DataLoader(ProductService productService) {
        this.productService = productService;
    }

    @Override
    public void run(String... args) throws Exception {
        long count = productService.countProducts();
        if (count == 0) {
            List<Product> products = new ArrayList<>();
            for (int i = 1; i <= 20; i++) {
                Product product = new Product();
                product.setName("Produkt " + i);
                product.setDescription("Beschreibung für Produkt " + i);
                product.setPrice(Math.round(Math.random() * 10000) / 100.0); // Zufälliger Preis zwischen 0.00 und 100.00
                product.setCategory(ProductCategory.values()[i % ProductCategory.values().length]); // Zufällige Kategorie
                products.add(product);
                productService.saveProduct(product);
            }
        }
    }

}