package com.example.shopproject.user.exception;

public class UserExistException extends RuntimeException{
    public UserExistException(String message) {
        super(message);
    }
}
