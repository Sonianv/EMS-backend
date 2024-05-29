package com.ems.error;

public class MonthClosedException extends RuntimeException {
    public MonthClosedException(String message) {
        super(message);
    }
}
