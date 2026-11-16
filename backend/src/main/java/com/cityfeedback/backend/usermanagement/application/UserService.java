package com.cityfeedback.backend.usermanagement.application;

import com.cityfeedback.backend.usermanagement.domain.EmailAddress;
import com.cityfeedback.backend.usermanagement.domain.User;
import com.cityfeedback.backend.usermanagement.domain.UserRepository;
import com.cityfeedback.backend.usermanagement.domain.UserRole;
import com.cityfeedback.backend.usermanagement.domain.AccountStatus;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.List;

@Service
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
            throw new EmailAlreadyRegisteredException();
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
                .orElseThrow(InvalidCredentialsException::new);

        boolean passwordMatches = passwordHasher.matches(
                rawPassword,
                user.getPasswordHash()
        );

        if (!passwordMatches
                || user.getAccountStatus() != AccountStatus.ACTIVE) {
            throw new InvalidCredentialsException();
        }

        return user;
    }

    public void deactivateUser(UUID userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);

        user.deactivate();

        userRepository.save(user);
    }

    public void activateUser(UUID userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);

        user.activate();

        userRepository.save(user);
    }

    public void changeUserRole(UUID userId, UserRole newRole) {
        User user = userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);

        user.changeRole(newRole);

        userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}