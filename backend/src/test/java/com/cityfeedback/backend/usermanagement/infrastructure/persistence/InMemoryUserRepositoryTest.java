package com.cityfeedback.backend.usermanagement.infrastructure.persistence;

import com.cityfeedback.backend.usermanagement.domain.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryUserRepositoryTest {

    private InMemoryUserRepository repository;
    private User user;

    @BeforeEach
    void setUp() {
        repository = new InMemoryUserRepository();

        user = new User(
                UUID.randomUUID(),
                "Max",
                "Mustermann",
                new EmailAddress("max@example.com"),
                "hashedPassword123",
                UserRole.CITIZEN
        );
    }

    @Test
    void shouldSaveUser() {
        repository.save(user);

        assertTrue(repository.existsByEmail(user.getEmail()));
    }

    @Test
    void shouldFindUserById() {
        repository.save(user);

        assertTrue(repository.findById(user.getId()).isPresent());
    }

    @Test
    void shouldFindUserByEmail() {
        repository.save(user);

        assertTrue(repository.findByEmail(user.getEmail()).isPresent());
    }

    @Test
    void shouldReturnEmptyWhenUserDoesNotExist() {
        assertTrue(repository.findById(UUID.randomUUID()).isEmpty());
    }

    @Test
    void shouldReturnFalseWhenEmailDoesNotExist() {
        assertFalse(repository.existsByEmail(
                new EmailAddress("unknown@example.com")
        ));
    }

    @Test
    void shouldRejectDuplicateEmail() {
        repository.save(user);

        User anotherUser = new User(
                UUID.randomUUID(),
                "Anna",
                "Musterfrau",
                new EmailAddress("max@example.com"),
                "anotherHashedPassword",
                UserRole.CITIZEN
        );

        assertThrows(IllegalArgumentException.class,
                () -> repository.save(anotherUser));
    }

    @Test
    void shouldUpdateExistingUserWithSameId() {
        repository.save(user);

        User updatedUser = new User(
                user.getId(),
                "Maximilian",
                "Mustermann",
                user.getEmail(),
                "hashedPassword123",
                UserRole.CITIZEN
        );

        repository.save(updatedUser);

        assertEquals(
                "Maximilian",
                repository.findById(user.getId()).orElseThrow().getFirstName()
        );
    }

    @Test
    void shouldRejectNullUser() {
        assertThrows(
                IllegalArgumentException.class,
                () -> repository.save(null)
        );
    }
}