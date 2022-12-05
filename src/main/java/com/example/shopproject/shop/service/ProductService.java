package com.example.shopproject.shop.service;

import com.example.shopproject.shop.model.Product;
import java.util.List;

public interface ProductService {

    Product save(Product product);

    Product update(Product newProduct, long id);

    Product delete(long id);

    Product findById(long id);

    Product findByName(String name);

    List<Product> findAll();
}
