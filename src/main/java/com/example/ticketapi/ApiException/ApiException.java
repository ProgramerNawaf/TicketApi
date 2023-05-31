package com.example.ticketapi.ApiException;


public class ApiException extends RuntimeException {
    public ApiException(String message) {
        super(message);
    }
}
