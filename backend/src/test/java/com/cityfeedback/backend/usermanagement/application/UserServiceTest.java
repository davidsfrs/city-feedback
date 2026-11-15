package com.cityfeedback.backend.usermanagement.application;

import com.cityfeedback.backend.usermanagement.domain.*;
import com.cityfeedback.backend.usermanagement.infrastructure.InMemoryUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {

    private InMemoryUserRepository repository;
    private UserService userService;

    @BeforeEach
    void setUp() {
        repository = new InMemoryUserRepository();

        PasswordHasher passwordHasher = new PasswordHasher() {

            @Override
            public String hash(String rawPassword) {
                return "hashed-" + rawPassword;
            }

            @Override
            public boolean matches(String rawPassword, String passwordHash) {
                return passwordHash.equals("hashed-" + rawPassword);
            }
        };

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

    @Test
    void shouldDeactivateUserSuccessfully() {
        User user = userService.registerCitizen(
                "Max",
                "Mustermann",
                "max@example.com",
                "securePassword123"
        );

        userService.deactivateUser(user.getId());

        assertEquals(AccountStatus.INACTIVE, user.getAccountStatus());
    }

    @Test
    void shouldThrowExceptionWhenDeactivatingUnknownUser() {
        UUID unknownUserId = UUID.randomUUID();

        assertThrows(IllegalArgumentException.class, () ->
                userService.deactivateUser(unknownUserId)
        );
    }

    @Test
    void shouldActivateUserSuccessfully() {
        User user = userService.registerCitizen(
                "Max",
                "Mustermann",
                "max@example.com",
                "securePassword123"
        );

        userService.deactivateUser(user.getId());

        userService.activateUser(user.getId());

        assertEquals(AccountStatus.ACTIVE, user.getAccountStatus());
    }

    @Test
    void shouldThrowExceptionWhenActivatingUnknownUser() {
        UUID unknownUserId = UUID.randomUUID();

        assertThrows(IllegalArgumentException.class, () ->
                userService.activateUser(unknownUserId)
        );
    }

    @Test
    void shouldLoginSuccessfully() {
        userService.registerCitizen(
                "Max",
                "Mustermann",
                "max@example.com",
                "securePassword123"
        );

        User loggedInUser = userService.login(
                "max@example.com",
                "securePassword123"
        );

        assertEquals("max@example.com", loggedInUser.getEmail().value());
        assertEquals(UserRole.CITIZEN, loggedInUser.getRole());
    }

    @Test
    void shouldRejectLoginWithWrongPassword() {
        userService.registerCitizen(
                "Max",
                "Mustermann",
                "max@example.com",
                "securePassword123"
        );

        assertThrows(IllegalArgumentException.class, () ->
                userService.login("max@example.com", "wrongPassword")
        );
    }

    @Test
    void shouldRejectLoginForUnknownEmail() {
        assertThrows(IllegalArgumentException.class, () ->
                userService.login("unknown@example.com", "somePassword")
        );
    }

    @Test
    void shouldRejectLoginForInactiveUser() {
        User user = userService.registerCitizen(
                "Max",
                "Mustermann",
                "max@example.com",
                "securePassword123"
        );

        userService.deactivateUser(user.getId());

        assertThrows(IllegalArgumentException.class, () ->
                userService.login(
                        "max@example.com",
                        "securePassword123"
                )
        );
    }

    @Test
    void shouldRejectLoginWithBlankPassword() {
        assertThrows(IllegalArgumentException.class, () ->
                userService.login("max@example.com", " ")
        );
    }

    @Test
    void shouldReturnSameErrorForInactiveAccount() {
        User user = userService.registerCitizen(
                "Max",
                "Mustermann",
                "max@example.com",
                "securePassword123"
        );

        userService.deactivateUser(user.getId());

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> userService.login(
                        "max@example.com",
                        "securePassword123"
                )
        );

        assertEquals(
                "Invalid email or password",
                exception.getMessage()
        );
    }
}