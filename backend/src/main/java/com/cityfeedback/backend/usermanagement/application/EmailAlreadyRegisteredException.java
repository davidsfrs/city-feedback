package com.cityfeedback.backend.usermanagement.application;

public class EmailAlreadyRegisteredException extends RuntimeException {

    public EmailAlreadyRegisteredException() {
        super("Email address is already registered");
    }
}