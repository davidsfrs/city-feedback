package com.cityfeedback.backend.usermanagement.application.dto;

import com.cityfeedback.backend.usermanagement.domain.UserRole;
import jakarta.validation.constraints.NotNull;

public record ChangeUserRoleRequest(
        @NotNull(message = "Role is required")
        UserRole role
) {}