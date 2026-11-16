package com.cityfeedback.backend.usermanagement.infrastructure.web;

import com.cityfeedback.backend.usermanagement.application.UserService;
import com.cityfeedback.backend.usermanagement.application.dto.UpdateOwnProfileRequest;
import com.cityfeedback.backend.usermanagement.application.dto.UserResponse;
import com.cityfeedback.backend.usermanagement.domain.EmailAddress;
import com.cityfeedback.backend.usermanagement.domain.User;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserProfileController {

    private final UserService userService;

    public UserProfileController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/me")
    public UserResponse getOwnProfile(Authentication authentication) {
        User user = userService.findByEmail(authentication.getName());

        return UserResponse.from(user);
    }

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
