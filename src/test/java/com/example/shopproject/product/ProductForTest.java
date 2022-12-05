package com.example.shopproject.product;

import com.example.shopproject.shop.model.Product;

public class ProductForTest {
    public static Product productForTest() {
        Product product = new Product();
        product.setId(99L);
        product.setName("phone");
        product.setPrice(12);
        product.setDescription("new");
        product.setVersion(2L);
        return product;
    }
}
