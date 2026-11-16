package com.cityfeedback.backend.usermanagement.infrastructure.web;

import com.cityfeedback.backend.usermanagement.application.UserService;
import com.cityfeedback.backend.usermanagement.application.dto.ChangeUserRoleRequest;
import com.cityfeedback.backend.usermanagement.application.dto.UserResponse;
import org.springframework.http.ResponseEntity;
import java.util.List;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;

@RestController
@RequestMapping("/api/admin/users")
public class AdminUserController {

    private final UserService userService;

    public AdminUserController(UserService userService) {
        this.userService = userService;
    }

    @PatchMapping("/{userId}/role")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void changeUserRole(
            @PathVariable UUID userId,
            @Valid @RequestBody ChangeUserRoleRequest request
    ) {
        userService.changeUserRole(userId, request.role());
    }

    @GetMapping
    public List<UserResponse> getAllUsers() {
        return userService.getAllUsers()
                .stream()
                .map(UserResponse::from)
                .toList();
    }

    @PatchMapping("/{userId}/deactivate")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deactivateUser(@PathVariable UUID userId) {
        userService.deactivateUser(userId);
    }

    @PatchMapping("/{userId}/activate")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void activateUser(@PathVariable UUID userId) {
        userService.activateUser(userId);
    }
}