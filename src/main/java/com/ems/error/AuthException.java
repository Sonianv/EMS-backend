package com.ems.error;

public class AuthException extends RuntimeException {
    public AuthException(String message) {
        super(message);
    }
}
