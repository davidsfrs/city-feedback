package com.cityfeedback.backend.usermanagement.application.dto;

import java.util.UUID;

public record AuthResponse(
        String token,
        String tokenType,
        UUID userId,
        String email,
        String role
) {}
