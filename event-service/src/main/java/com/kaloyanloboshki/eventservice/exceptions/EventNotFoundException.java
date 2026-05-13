package com.kaloyanloboshki.eventservice.exceptions;

public class EventNotFoundException extends RuntimeException {

    public EventNotFoundException(String message) {
        super(message);
    }
}
