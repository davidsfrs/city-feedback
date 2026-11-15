package com.cityfeedback.backend.usermanagement.infrastructure;

import com.cityfeedback.backend.usermanagement.domain.EmailAddress;
import com.cityfeedback.backend.usermanagement.domain.User;
import com.cityfeedback.backend.usermanagement.domain.UserRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class InMemoryUserRepository implements UserRepository {

    private final Map<UUID, User> users = new HashMap<>();

    @Override
    public void save(User user) {

        if (user == null) {
            throw new IllegalArgumentException(
                    "User must not be null"
            );
        }

        boolean emailAlreadyExists = users.values()
                .stream()
                .anyMatch(existingUser ->
                        existingUser.getEmail().equals(user.getEmail())
                                && !existingUser.getId().equals(user.getId())
                );

        if (emailAlreadyExists) {
            throw new IllegalArgumentException(
                    "Email address is already registered"
            );
        }

        users.put(user.getId(), user);
    }

    @Override
    public Optional<User> findById(UUID id) {
        return Optional.ofNullable(users.get(id));
    }

    @Override
    public Optional<User> findByEmail(EmailAddress email) {
        return users.values()
                .stream()
                .filter(user -> user.getEmail().equals(email))
                .findFirst();
    }

    @Override
    public boolean existsByEmail(EmailAddress email) {
        return users.values()
                .stream()
                .anyMatch(user -> user.getEmail().equals(email));
    }
}
