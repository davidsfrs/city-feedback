package com.cityfeedback.backend.usermanagement.application;

@FunctionalInterface
public interface PasswordHasher {

    String hash(String rawPassword);
}
