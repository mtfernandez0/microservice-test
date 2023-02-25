package com.example.shoppingservice.client;

import com.example.shoppingservice.model.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "product-service", path = "/products")
public interface ProductClient {
    @GetMapping(value = "/{id}")
    ResponseEntity<Product> getProduct(@PathVariable("id") Long id);

    @PutMapping(value = "/{id}/stock")
    ResponseEntity<Product> updateStockProduct(
            @PathVariable Long id,
            @RequestParam(name = "quantity") Double quantity);
}
