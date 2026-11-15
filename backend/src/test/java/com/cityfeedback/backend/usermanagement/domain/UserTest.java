package com.cityfeedback.backend.usermanagement.domain;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    void shouldCreateUserWithValidData() {
        EmailAddress email = new EmailAddress("max@example.com");

        User user = new User(
                UUID.randomUUID(),
                "Max",
                "Mustermann",
                email,
                "hashedPassword123",
                UserRole.CITIZEN
        );

        assertEquals("Max", user.getFirstName());
        assertEquals("Mustermann", user.getLastName());
        assertEquals(email, user.getEmail());
        assertEquals(UserRole.CITIZEN, user.getRole());
        assertEquals(AccountStatus.ACTIVE, user.getAccountStatus());
    }

    @Test
    void shouldRejectNullEmail() {
        assertThrows(IllegalArgumentException.class, () ->
                new User(
                        UUID.randomUUID(),
                        "Max",
                        "Mustermann",
                        null,
                        "hashedPassword123",
                        UserRole.CITIZEN
                )
        );
    }

    @Test
    void shouldRejectEmptyFirstName() {
        assertThrows(IllegalArgumentException.class, () ->
                new User(
                        UUID.randomUUID(),
                        "",
                        "Mustermann",
                        new EmailAddress("max@example.com"),
                        "hashedPassword123",
                        UserRole.CITIZEN
                )
        );
    }

    @Test
    void shouldRejectEmptyLastName() {
        assertThrows(IllegalArgumentException.class, () ->
                new User(
                        UUID.randomUUID(),
                        "Max",
                        "",
                        new EmailAddress("max@example.com"),
                        "hashedPassword123",
                        UserRole.CITIZEN
                )
        );
    }

    @Test
    void shouldRejectNullRole() {
        assertThrows(IllegalArgumentException.class, () ->
                new User(
                        UUID.randomUUID(),
                        "Max",
                        "Mustermann",
                        new EmailAddress("max@example.com"),
                        "hashedPassword123",
                        null
                )
        );
    }

    @Test
    void shouldDeactivateUser() {
        User user = new User(
                UUID.randomUUID(),
                "Max",
                "Mustermann",
                new EmailAddress("max@example.com"),
                "hashedPassword123",
                UserRole.CITIZEN
        );

        user.deactivate();

        assertEquals(AccountStatus.INACTIVE, user.getAccountStatus());
    }

    @Test
    void shouldActivateInactiveUser() {
        User user = new User(
                UUID.randomUUID(),
                "Max",
                "Mustermann",
                new EmailAddress("max@example.com"),
                "hashedPassword123",
                UserRole.CITIZEN
        );

        user.deactivate();
        user.activate();

        assertEquals(AccountStatus.ACTIVE, user.getAccountStatus());
    }


    @Test
    void shouldRejectNullId() {
        assertThrows(IllegalArgumentException.class, () ->
                new User(
                        null,
                        "Max",
                        "Mustermann",
                        new EmailAddress("max@example.com"),
                        "hashedPassword123",
                        UserRole.CITIZEN
                )
        );
    }

    @Test
    void shouldRejectBlankPasswordHash() {
        assertThrows(IllegalArgumentException.class, () ->
                new User(
                        UUID.randomUUID(),
                        "Max",
                        "Mustermann",
                        new EmailAddress("max@example.com"),
                        " ",
                        UserRole.CITIZEN
                )
        );
    }

    @Test
    void shouldRejectBlankFirstName() {
        assertThrows(IllegalArgumentException.class, () ->
                new User(
                        UUID.randomUUID(),
                        "   ",
                        "Mustermann",
                        new EmailAddress("max@example.com"),
                        "hashedPassword123",
                        UserRole.CITIZEN
                )
        );
    }
}
