package com.example.product.controller;

import com.example.product.entity.Category;
import com.example.product.entity.Product;
import com.example.product.service.ProductService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<List<Product>> listProduct(
            @RequestParam(name = "categoryId", required = false) Long categoryId){
        List<Product> products;

        if (categoryId == null) {
            products = productService.listAllProduct();
            if (products.isEmpty()) return ResponseEntity.noContent().build();
        } else {
            products = productService
                    .findByCategory(Category.builder().id(categoryId).build());
            if (products.isEmpty()) return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(products);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable("id") Long id){
        Product product = productService.getProduct(id);
        if (product == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(product);
    }

    @PostMapping
    public ResponseEntity<Product> createProduct(
            @Valid @RequestBody Product product,
            BindingResult bindingResult){
        if (bindingResult.hasErrors())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, this.formatMessage(bindingResult));

        Product productCreate = productService.createProduct(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(productCreate);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody Product product){
        product.setId(id);
        Product productDB = productService.updateProduct(product);

        if (productDB == null)
            return ResponseEntity.notFound().build();

        return ResponseEntity.status(HttpStatus.OK).body(productDB);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Product> deleteProduct(@PathVariable Long id){
        Product productDB = productService.deleteProduct(id);

        if (productDB == null)
            return ResponseEntity.notFound().build();

        return ResponseEntity.status(HttpStatus.OK).body(productDB);
    }

    @PutMapping(value = "/{id}/stock")
    public ResponseEntity<Product> updateStockProduct(
            @PathVariable Long id,
            @RequestParam(name = "quantity") Double quantity){
        Product product = productService.updateStock(id, quantity);
        if (product == null)
            return ResponseEntity.notFound().build();

        return ResponseEntity.status(HttpStatus.OK).body(product);
    }

    private String formatMessage(BindingResult bindingResult){
        List<Map<String, String>> errors =
                bindingResult.getFieldErrors().stream()
                        .map(err -> {
                            Map<String, String> error = new HashMap<>();
                            error.put(err.getField(), err.getDefaultMessage());
                            return error;
                        }).toList();

        ErrorMessage errorMessage = ErrorMessage.builder()
                .code("01")
                .messages(errors)
                .build();

        ObjectMapper mapper = new ObjectMapper();
        String jsonString = "";
        try {
            jsonString = mapper.writeValueAsString(errorMessage);
        } catch (JsonProcessingException e){
            e.printStackTrace();
        }
        return jsonString;
    }
}
