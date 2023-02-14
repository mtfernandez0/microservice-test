package com.example.customerservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.io.Serializable;

@Data
@Entity
@Table(name="tbl_customers")
public class Customer implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "DNI cannot be empty.")
    @Size( min = 8 , max = 8, message = "DNI size is 8 numbers.")
    @Column(name = "number_id" , unique = true ,length = 8, nullable = false)
    private String numberID;

    @NotEmpty(message = "Name must not be empty.")
    @Column(name="first_name", nullable=false)
    private String firstName;

    @NotEmpty(message = "Last name must not be empty.")
    @Column(name="last_name", nullable=false)
    private String lastName;

    @NotEmpty(message = "Email must not be empty.")
    @Email(message = "It is not a valid format of email.")
    @Column(unique=true, nullable=false)
    private String email;

    @Column(name="photo_url")
    private String photoUrl;


    @NotNull(message = "Region cannot be empty.")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "region_id")
    @JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
    private Region region;

    private String state;
}
