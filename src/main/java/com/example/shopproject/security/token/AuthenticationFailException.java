package com.example.shopproject.security.token;

public class AuthenticationFailException extends RuntimeException{
    public AuthenticationFailException(String message) {
        super(message);
    }
}
