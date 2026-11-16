package com.cityfeedback.backend.usermanagement.infrastructure.web;

import com.cityfeedback.backend.usermanagement.application.UserService;
import com.cityfeedback.backend.usermanagement.application.dto.AuthResponse;
import com.cityfeedback.backend.usermanagement.application.dto.RegisterRequest;
import com.cityfeedback.backend.usermanagement.infrastructure.security.JwtService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import com.cityfeedback.backend.usermanagement.application.dto.LoginRequest;

@Tag(
        name = "Authentication",
        description = "User registration and authentication"
)
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;
    private final JwtService jwtService;

    public AuthController(UserService userService, JwtService jwtService) {
        this.userService = userService;
        this.jwtService = jwtService;
    }

    @Operation(
            summary = "Register a new citizen",
            description = "Creates a new citizen account and returns an authentication token."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "User successfully registered"),
            @ApiResponse(responseCode = "409", description = "Email already registered"),
            @ApiResponse(responseCode = "400", description = "Invalid request")
    })
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

    @Operation(
            summary = "Login",
            description = "Authenticates a user with email and password and returns a JWT."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Login successful"),
            @ApiResponse(responseCode = "401", description = "Invalid credentials"),
            @ApiResponse(responseCode = "400", description = "Invalid request")
    })
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