package com.example.webshopba;

import com.example.webshopba.enums.ProductCategory;
import com.example.webshopba.model.Product;
import com.example.webshopba.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DataLoader implements CommandLineRunner {

    private final ProductService productService;
    private final JdbcTemplate jdbcTemplate;


    @Autowired
    public DataLoader(ProductService productService, JdbcTemplate jdbcTemplate) {
        this.productService = productService;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void run(String... args) throws Exception {
        /*productService.deleteAllProducts();
        jdbcTemplate.execute("ALTER SEQUENCE product_id_seq RESTART WITH 1");*/

        long count = productService.countProducts();
        if (count == 0) {
            List<Product> products = new ArrayList<>();
            for (int i = 1; i <= 40; i++) {
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