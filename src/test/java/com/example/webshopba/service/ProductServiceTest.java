package com.example.webshopba.service;

import com.example.webshopba.enums.ProductCategory;
import com.example.webshopba.model.Product;
import com.example.webshopba.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllProducts() {
        Product product1 = new Product();
        Product product2 = new Product();
        when(productRepository.findAll()).thenReturn(Arrays.asList(product1, product2));

        List<Product> products = productService.getAllProducts();

        assertNotNull(products);
        assertEquals(2, products.size());
        verify(productRepository, times(1)).findAll();
    }

    @Test
    void getProductById() {
        Long productId = 1L;
        Product product = new Product();
        product.setId(productId);
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        Product foundProduct = productService.getProductById(productId);

        assertNotNull(foundProduct);
        assertEquals(productId, foundProduct.getId());
        verify(productRepository, times(1)).findById(productId);
    }

    @Test
    void getProductById_NotFound() {
        Long productId = 1L;
        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            productService.getProductById(productId);
        });

        assertEquals("Product not found", exception.getMessage());
        verify(productRepository, times(1)).findById(productId);
    }

    @Test
    void saveProduct() {
        Product product = new Product();
        when(productRepository.save(product)).thenReturn(product);

        Product savedProduct = productService.saveProduct(product);

        assertNotNull(savedProduct);
        verify(productRepository, times(1)).save(product);
    }

    @Test
    void deleteProduct() {
        Long productId = 1L;
        doNothing().when(productRepository).deleteById(productId);

        productService.deleteProduct(productId);

        verify(productRepository, times(1)).deleteById(productId);
    }

    @Test
    void countProducts() {
        long count = 5L;
        when(productRepository.count()).thenReturn(count);

        long productCount = productService.countProducts();

        assertEquals(count, productCount);
        verify(productRepository, times(1)).count();
    }

    @Test
    void getProductsByCategory() {
        ProductCategory category = ProductCategory.ELECTRONICS;
        Product product1 = new Product();
        Product product2 = new Product();
        when(productRepository.findByCategory(category)).thenReturn(Arrays.asList(product1, product2));

        List<Product> products = productService.getProductsByCategory(category);

        assertNotNull(products);
        assertEquals(2, products.size());
        verify(productRepository, times(1)).findByCategory(category);
    }

    @Test
    void deleteAllProducts() {
        doNothing().when(productRepository).deleteAll();

        productService.deleteAllProducts();

        verify(productRepository, times(1)).deleteAll();
    }
}