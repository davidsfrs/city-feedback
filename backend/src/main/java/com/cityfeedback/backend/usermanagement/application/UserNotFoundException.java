package com.cityfeedback.backend.usermanagement.application;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException() {
        super("User not found");
    }
}