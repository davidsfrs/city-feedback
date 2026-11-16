package com.cityfeedback.backend.usermanagement.application.dto;

import jakarta.validation.constraints.NotBlank;

public record UpdateOwnProfileRequest(
        @NotBlank(message = "First name is required")
        String firstName,

        @NotBlank(message = "Last name is required")
        String lastName
) {}