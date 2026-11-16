package com.cityfeedback.backend.usermanagement.infrastructure.web;

import com.cityfeedback.backend.usermanagement.application.UserService;
import com.cityfeedback.backend.usermanagement.application.dto.UpdateOwnProfileRequest;
import com.cityfeedback.backend.usermanagement.application.dto.UserResponse;
import com.cityfeedback.backend.usermanagement.domain.EmailAddress;
import com.cityfeedback.backend.usermanagement.domain.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@Tag(
        name = "User Profile",
        description = "Manage your own user profile"
)
@RestController
@RequestMapping("/api/users")
@SecurityRequirement(name = "bearerAuth")
public class UserProfileController {

    private final UserService userService;

    public UserProfileController(UserService userService) {
        this.userService = userService;
    }

    @Operation(
            summary = "Get own profile",
            description = "Retrieves the profile information of the currently authenticated user."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User profile successfully retrieved"),
            @ApiResponse(responseCode = "401", description = "Authentication required")
    })
    @GetMapping("/me")
    public UserResponse getOwnProfile(Authentication authentication) {
        User user = userService.findByEmail(authentication.getName());

        return UserResponse.from(user);
    }

    @Operation(
            summary = "Update own profile",
            description = "Updates the first name and last name of the currently authenticated user."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User profile successfully updated"),
            @ApiResponse(responseCode = "400", description = "Invalid profile data"),
            @ApiResponse(responseCode = "401", description = "Authentication required")
    })
    @PatchMapping("/me")
    public UserResponse updateOwnProfile(
            Authentication authentication,
            @Valid @RequestBody UpdateOwnProfileRequest request
    ) {
        User user = userService.findByEmail(authentication.getName());

        userService.updateOwnName(
                user.getId(),
                request.firstName(),
                request.lastName()
        );

        User updatedUser = userService.findByEmail(authentication.getName());

        return UserResponse.from(updatedUser);
    }
}
