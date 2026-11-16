package com.cityfeedback.backend.usermanagement.domain;

import java.util.UUID;

public class User {

    private final UUID id;
    private final String firstName;
    private final String lastName;
    private final EmailAddress email;
    private final String passwordHash;
    private UserRole role;
    private AccountStatus accountStatus;

    public User(
            UUID id,
            String firstName,
            String lastName,
            EmailAddress email,
            String passwordHash,
            UserRole role
    ) {
        if (id == null) {
            throw new IllegalArgumentException("ID must not be null");
        }
        if (firstName == null || firstName.isBlank()) {
            throw new IllegalArgumentException("First name must not be empty");
        }
        if (lastName == null || lastName.isBlank()) {
            throw new IllegalArgumentException("Last name must not be empty");
        }
        if (email == null) {
            throw new IllegalArgumentException("Email must not be null");
        }
        if (passwordHash == null || passwordHash.isBlank()) {
            throw new IllegalArgumentException("Password hash must not be empty");
        }
        if (role == null) {
            throw new IllegalArgumentException("Role must not be null");
        }

        this.id = id;
        this.firstName = firstName.trim();
        this.lastName = lastName.trim();
        this.email = email;
        this.passwordHash = passwordHash;
        this.role = role;
        this.accountStatus = AccountStatus.ACTIVE;
    }

    public UUID getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public EmailAddress getEmail() {
        return email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public UserRole getRole() {
        return role;
    }

    public AccountStatus getAccountStatus() {
        return accountStatus;
    }

    public void deactivate() {
        this.accountStatus = AccountStatus.INACTIVE;
    }

    public void activate() {
        this.accountStatus = AccountStatus.ACTIVE;
    }

    public void changeRole(UserRole newRole) {
        if (newRole == null) {
            throw new IllegalArgumentException("Role must not be null");
        }

        this.role = newRole;
    }
}