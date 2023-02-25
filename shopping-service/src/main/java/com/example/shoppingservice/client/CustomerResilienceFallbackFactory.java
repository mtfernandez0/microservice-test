package com.example.shoppingservice.client;

import com.example.shoppingservice.model.Customer;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class CustomerResilienceFallbackFactory implements CustomerClient {
    @Override
    public ResponseEntity<Customer> getCustomer(long id) {
        Customer customer =
                Customer.builder()
                        .firstName("none")
                        .lastName("none")
                        .email("none")
                        .photoUrl("none").build();

        return ResponseEntity.ok(customer);
    }
}
