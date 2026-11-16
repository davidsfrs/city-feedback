package com.cityfeedback.backend.usermanagement.infrastructure.web;

import com.cityfeedback.backend.usermanagement.application.UserService;
import com.cityfeedback.backend.usermanagement.application.dto.AuthResponse;
import com.cityfeedback.backend.usermanagement.application.dto.RegisterRequest;
import com.cityfeedback.backend.usermanagement.infrastructure.security.JwtService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import com.cityfeedback.backend.usermanagement.application.dto.LoginRequest;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;
    private final JwtService jwtService;

    public AuthController(UserService userService, JwtService jwtService) {
        this.userService = userService;
        this.jwtService = jwtService;
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public AuthResponse register(@Valid @RequestBody RegisterRequest request) {
        var user = userService.registerCitizen(
                request.firstName(),
                request.lastName(),
                request.email(),
                request.password()
        );

        String token = jwtService.generateToken(
                user.getEmail().value(),
                user.getRole()
        );

        return new AuthResponse(
                token,
                "Bearer",
                user.getId(),
                user.getEmail().value(),
                user.getRole().name()
        );
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public AuthResponse login(@Valid @RequestBody LoginRequest request) {
        var user = userService.login(
                request.email(),
                request.password()
        );

        String token = jwtService.generateToken(
                user.getEmail().value(),
                user.getRole()
        );

        return new AuthResponse(
                token,
                "Bearer",
                user.getId(),
                user.getEmail().value(),
                user.getRole().name()
        );
    }
}