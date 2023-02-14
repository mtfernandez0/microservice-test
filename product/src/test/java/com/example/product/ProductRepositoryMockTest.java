package com.example.product;

import com.example.product.entity.Category;
import com.example.product.entity.Product;
import com.example.product.repository.ProductRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Date;
import java.util.List;

@DataJpaTest
public class ProductRepositoryMockTest {
    @Autowired
    private ProductRepository productRepository;

    @Test
    public void whenFindByCategory_thenReturnListProducts(){
        Product product = Product
                .builder()
                .name("computer")
                .category(Category.builder().id(3L).build())
                .description("")
                .stock(Double.parseDouble("10"))
                .price(Double.parseDouble("19.99"))
                .status("Created")
                .createAt(new Date())
                .build();
        productRepository.save(product);

        List<Product> products = productRepository.findByCategory(product.getCategory());

        System.out.println(products);

        Assertions.assertThat(products.size()).isEqualTo(2);
    }
}