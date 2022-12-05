package com.example.shopproject.user.exception;

public class NoEnoughMoneyException extends RuntimeException{
    public NoEnoughMoneyException(String message) {
        super(message);
    }
}
