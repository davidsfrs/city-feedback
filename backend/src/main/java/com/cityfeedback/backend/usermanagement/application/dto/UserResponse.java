package com.cityfeedback.backend.usermanagement.application.dto;

import com.cityfeedback.backend.usermanagement.domain.User;

import java.util.UUID;

public record UserResponse(
        UUID id,
        String firstName,
        String lastName,
        String email,
        String role,
        String accountStatus
) {
    public static UserResponse from(User user) {
        return new UserResponse(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail().value(),
                user.getRole().name(),
                user.getAccountStatus().name()
        );
    }
}