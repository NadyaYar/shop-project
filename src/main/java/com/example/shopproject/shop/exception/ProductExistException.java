package com.example.shopproject.shop.exception;

public class ProductExistException extends RuntimeException{
    public ProductExistException(String message) {
        super(message);
    }
}
