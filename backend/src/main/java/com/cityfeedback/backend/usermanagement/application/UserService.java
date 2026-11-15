package com.cityfeedback.backend.usermanagement.application;

import com.cityfeedback.backend.usermanagement.domain.EmailAddress;
import com.cityfeedback.backend.usermanagement.domain.User;
import com.cityfeedback.backend.usermanagement.domain.UserRepository;
import com.cityfeedback.backend.usermanagement.domain.UserRole;
import com.cityfeedback.backend.usermanagement.domain.AccountStatus;

import java.util.UUID;

public class UserService {

    private final UserRepository userRepository;
    private final PasswordHasher passwordHasher;

    public UserService(
            UserRepository userRepository,
            PasswordHasher passwordHasher
    ) {
        this.userRepository = userRepository;
        this.passwordHasher = passwordHasher;
    }

    public User registerCitizen(
            String firstName,
            String lastName,
            String email,
            String rawPassword
    ) {
        if (rawPassword == null || rawPassword.isBlank()) {
            throw new IllegalArgumentException(
                    "Password must not be empty"
            );
        }

        EmailAddress emailAddress = new EmailAddress(email);

        if (userRepository.existsByEmail(emailAddress)) {
            throw new IllegalArgumentException(
                    "Email address is already registered"
            );
        }

        String passwordHash = passwordHasher.hash(rawPassword);

        User user = new User(
                UUID.randomUUID(),
                firstName,
                lastName,
                emailAddress,
                passwordHash,
                UserRole.CITIZEN
        );

        userRepository.save(user);

        return user;
    }

    public User login(String email, String rawPassword) {

        if (email == null || email.isBlank()
                || rawPassword == null || rawPassword.isBlank()) {
            throw new IllegalArgumentException(
                    "Email and password must not be empty"
            );
        }

        EmailAddress emailAddress = new EmailAddress(email);

        User user = userRepository.findByEmail(emailAddress)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Invalid email or password"
                        )
                );

        if (user.getAccountStatus() != AccountStatus.ACTIVE) {
            throw new IllegalArgumentException(
                    "Account is inactive"
            );
        }

        if (!passwordHasher.matches(rawPassword, user.getPasswordHash())) {
            throw new IllegalArgumentException(
                    "Invalid email or password"
            );
        }

        return user;
    }

    public void deactivateUser(UUID userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new IllegalArgumentException("User not found")
                );

        user.deactivate();

        userRepository.save(user);
    }

    public void activateUser(UUID userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new IllegalArgumentException("User not found")
                );

        user.activate();

        userRepository.save(user);
    }
}