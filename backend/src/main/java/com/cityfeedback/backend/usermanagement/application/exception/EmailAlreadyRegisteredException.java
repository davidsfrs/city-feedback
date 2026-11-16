package com.cityfeedback.backend.usermanagement.application.exception;

public class EmailAlreadyRegisteredException extends RuntimeException {

    public EmailAlreadyRegisteredException() {
        super("Email address is already registered");
    }
}