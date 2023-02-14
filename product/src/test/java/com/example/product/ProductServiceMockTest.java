package com.example.product;

import com.example.product.entity.Category;
import com.example.product.entity.Product;
import com.example.product.repository.ProductRepository;
import com.example.product.service.ProductService;
import com.example.product.service.ProductServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class ProductServiceMockTest {
    @Mock
    private ProductRepository productRepository;

    private ProductService productService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        productService = new ProductServiceImpl(productRepository);
        Product product = Product
                .builder()
                .id(1L)
                .name("computer")
                .category(Category.builder().id(1L).build())
                .stock(Double.parseDouble("5"))
                .price(Double.parseDouble("19.99"))
                .build();

        Mockito.when(productRepository.findById(1L))
                .thenReturn(Optional.of(product));

        Mockito.when(productRepository.save(product))
                .thenReturn(product);
    }

    @Test
    void whenValidGetId_ThenReturnProduct() {
        Product product = productService.getProduct(1L);
        Assertions.assertEquals(product.getName(), "computer");
    }

    @Test
    void whenValidateUpdateStock_ThenReturnNewStock() {
        Product product = productService.updateStock(1L, Double.parseDouble("8"));
        Assertions.assertEquals(product.getStock(), Double.parseDouble("13"));
    }

}