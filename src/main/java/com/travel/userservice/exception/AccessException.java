package com.travel.userservice.exception;

public class AccessException extends RuntimeException {
    public AccessException(String message) {
        super(message);
    }
}