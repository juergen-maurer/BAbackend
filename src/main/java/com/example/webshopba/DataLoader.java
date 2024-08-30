package com.example.webshopba;

import com.example.webshopba.enums.ProductCategory;
import com.example.webshopba.model.Product;
import com.example.webshopba.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.*;

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
            Map<ProductCategory, Integer> categoryCountMap = new HashMap<>();
            List<ProductCategory> categories = new ArrayList<>();

            // Add each category multiple times to ensure equal distribution
            for (ProductCategory category : ProductCategory.values()) {
                for (int i = 0; i < 10; i++) { // Assuming 10 products per category
                    categories.add(category);
                }
            }

            // Shuffle the categories list
            Collections.shuffle(categories);

            for (int i = 0; i < categories.size(); i++) {
                Product product = new Product();
                product.setName("Produkt " + (i + 1));
                product.setDescription("Beschreibung für Produkt " + (i + 1));
                product.setPrice(Math.round(Math.random() * 10000) / 100.0); // Zufälliger Preis zwischen 0.00 und 100.00
                ProductCategory category = categories.get(i);
                product.setCategory(category);

                // Update category count
                categoryCountMap.put(category, categoryCountMap.getOrDefault(category, 0) + 1);
                int categoryCount = categoryCountMap.get(category);

                // Set imageUrl
                product.setImageUrl(category.toString() + categoryCount + ".png");

                products.add(product);
                productService.saveProduct(product);
            }
        }
    }

}