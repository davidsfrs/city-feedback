package com.cityfeedback.backend.usermanagement.domain;

import java.util.UUID;

public class User {

    private final UUID id;
    private String firstName;
    private String lastName;
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
        this.firstName = validateName(firstName, "First name");
        this.lastName = validateName(lastName, "Last name");
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

    public void updateName(String firstName, String lastName) {
        String validatedFirstName = validateName(firstName, "First name");
        String validatedLastName = validateName(lastName, "Last name");

        this.firstName = validatedFirstName;
        this.lastName = validatedLastName;
    }

    private String validateName(String name, String fieldName) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException(
                    fieldName + " must not be empty"
            );
        }

        return name.trim();
    }
}