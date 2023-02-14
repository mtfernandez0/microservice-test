package com.example.product.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
/*@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor*/
@Data @AllArgsConstructor @NoArgsConstructor @Builder
@Table(name = "category")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;



}
