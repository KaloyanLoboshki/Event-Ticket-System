package com.kaloyanloboshki.eventservice.exceptions;

public class InsufficientSeatsException extends RuntimeException {
    public InsufficientSeatsException(String message) {
        super(message);
    }
}
