package com.cityfeedback.backend.usermanagement.application;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BCryptPasswordHasherTest {

    private final BCryptPasswordHasher passwordHasher =
            new BCryptPasswordHasher();

    @Test
    void shouldHashPassword() {
        String rawPassword = "MySecurePassword123";

        String hashedPassword = passwordHasher.hash(rawPassword);

        assertNotNull(hashedPassword);
        assertNotEquals(rawPassword, hashedPassword);
    }

    @Test
    void shouldVerifyCorrectPassword() {
        String rawPassword = "MySecurePassword123";
        String hashedPassword = passwordHasher.hash(rawPassword);

        assertTrue(passwordHasher.matches(rawPassword, hashedPassword));
    }

    @Test
    void shouldRejectIncorrectPassword() {
        String hashedPassword = passwordHasher.hash("CorrectPassword123");

        assertFalse(passwordHasher.matches(
                "WrongPassword123",
                hashedPassword
        ));
    }

    @Test
    void shouldGenerateDifferentHashesForSamePassword() {
        String rawPassword = "MySecurePassword123";

        String firstHash = passwordHasher.hash(rawPassword);
        String secondHash = passwordHasher.hash(rawPassword);

        assertNotEquals(firstHash, secondHash);
    }
}