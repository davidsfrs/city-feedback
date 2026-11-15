
package com.cityfeedback.backend.usermanagement.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EmailAddressTest {

    // Happy Path

    @Test
    void shouldAcceptValidEmailAddress() {
        EmailAddress email =
                new EmailAddress("max@example.com");

        assertEquals("max@example.com", email.getValue());
    }

    @Test
    void shouldNormalizeEmailAddressToLowerCase() {
        EmailAddress email =
                new EmailAddress("Max@Example.com");

        assertEquals("max@example.com", email.getValue());
    }

    // Negative Tests

    @Test
    void shouldRejectEmailWithoutAtSign() {
        assertThrows(IllegalArgumentException.class,
                () -> new EmailAddress("maxexample.com"));
    }

    @Test
    void shouldRejectEmailWithoutDomain() {
        assertThrows(IllegalArgumentException.class,
                () -> new EmailAddress("max@"));
    }

    @Test
    void shouldRejectEmailWithoutLocalPart() {
        assertThrows(IllegalArgumentException.class,
                () -> new EmailAddress("@example.com"));
    }

    @Test
    void shouldRejectEmptyEmail() {
        assertThrows(IllegalArgumentException.class,
                () -> new EmailAddress(""));
    }

    @Test
    void shouldRejectNullEmail() {
        assertThrows(IllegalArgumentException.class,
                () -> new EmailAddress(null));
    }

    // Edge Cases

    @Test
    void shouldRejectEmailContainingOnlyWhitespace() {
        assertThrows(IllegalArgumentException.class,
                () -> new EmailAddress("   "));
    }

    @Test
    void shouldRejectEmailWithoutDomainExtension() {
        assertThrows(IllegalArgumentException.class,
                () -> new EmailAddress("max@example"));
    }

    @Test
    void shouldRejectEmailWithMultipleAtSigns() {
        assertThrows(IllegalArgumentException.class,
                () -> new EmailAddress("max@@example.com"));
    }

    @Test
    void shouldRejectEmailContainingWhitespace() {
        assertThrows(IllegalArgumentException.class,
                () -> new EmailAddress("max mustermann@example.com"));
    }
}