package com.cityfeedback.backend.usermanagement.application;

import com.cityfeedback.backend.usermanagement.domain.*;
import com.cityfeedback.backend.usermanagement.infrastructure.InMemoryUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {

    private InMemoryUserRepository repository;
    private UserService userService;

    @BeforeEach
    void setUp() {
        repository = new InMemoryUserRepository();

        PasswordHasher passwordHasher =
                rawPassword -> "hashed-" + rawPassword;

        userService = new UserService(repository, passwordHasher);
    }

    @Test
    void shouldRegisterCitizenSuccessfully() {
        User user = userService.registerCitizen(
                "Max",
                "Mustermann",
                "max@example.com",
                "securePassword123"
        );

        assertNotNull(user.getId());
        assertEquals("Max", user.getFirstName());
        assertEquals("Mustermann", user.getLastName());
        assertEquals(
                new EmailAddress("max@example.com"),
                user.getEmail()
        );
        assertEquals(UserRole.CITIZEN, user.getRole());
        assertEquals(AccountStatus.ACTIVE, user.getAccountStatus());
        assertEquals("hashed-securePassword123", user.getPasswordHash());

        assertTrue(repository.findById(user.getId()).isPresent());
    }

    @Test
    void shouldRejectRegistrationWithDuplicateEmail() {
        userService.registerCitizen(
                "Max",
                "Mustermann",
                "max@example.com",
                "securePassword123"
        );

        assertThrows(IllegalArgumentException.class, () ->
                userService.registerCitizen(
                        "Anna",
                        "Musterfrau",
                        "max@example.com",
                        "anotherPassword123"
                )
        );
    }

    @Test
    void shouldRejectBlankPassword() {
        assertThrows(IllegalArgumentException.class, () ->
                userService.registerCitizen(
                        "Max",
                        "Mustermann",
                        "max@example.com",
                        " "
                )
        );
    }
}
