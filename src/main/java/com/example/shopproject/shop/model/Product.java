package com.example.shopproject.shop.model;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "products")
@Data
@NoArgsConstructor
public class Product {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(name = "name")
    @NotNull
    private String name;

    @Column(name = "description", length = 25)
    @NotNull
    private String description;

    @Column(name = "price")
    @NotNull
    private double price;

    @Column(name = "version")
    @NotNull
    private Long version;

    @Column(name = "quantity")
    private int quantity;

    public Product(String name,
                   String description,
                   double price,
                   Long version,
                   int quantity) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.version = version;
        this.quantity = quantity;
    }
}
