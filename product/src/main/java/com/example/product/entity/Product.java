package com.example.product.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.util.Date;

@Entity
@Data
@AllArgsConstructor @NoArgsConstructor @Builder
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "Name must not be empty.")
    private String name;
    private String description;

    @Positive(message = "Stock must be positive.")
    private Double stock;
    private Double price;
    private String status;

    @Column(name = "create_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createAt;

    @NotNull(message = "Category must not be empty.")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    @JsonIgnoreProperties("hibernateLazyInitializer")
    private Category category;
}
