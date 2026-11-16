package com.cityfeedback.backend.usermanagement.application;

import com.cityfeedback.backend.usermanagement.domain.EmailAddress;
import com.cityfeedback.backend.usermanagement.domain.User;
import com.cityfeedback.backend.usermanagement.domain.UserRepository;
import com.cityfeedback.backend.usermanagement.domain.UserRole;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceIntegrationTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordHasher passwordHasher;

    @Test
    void shouldRegisterUserWithBCryptHashedPassword() {

        String rawPassword = "SecurePassword123!";
        String email = "integration-" + UUID.randomUUID() + "@example.com";

        User registeredUser = userService.registerCitizen(
                "Max",
                "Mustermann",
                email,
                rawPassword
        );

        User savedUser = userRepository
                .findByEmail(new EmailAddress(email))
                .orElseThrow();

        assertNotNull(registeredUser);
        assertEquals(UserRole.CITIZEN, savedUser.getRole());

        assertNotEquals(rawPassword, savedUser.getPasswordHash());

        assertTrue(passwordHasher.matches(
                rawPassword,
                savedUser.getPasswordHash()
        ));
    }
}