package com.kaloyanloboshki.preferenceservice.exceptions;

public class PreferenceNotFoundException extends RuntimeException {

    public PreferenceNotFoundException(String message) {
        super(message);
    }
}
