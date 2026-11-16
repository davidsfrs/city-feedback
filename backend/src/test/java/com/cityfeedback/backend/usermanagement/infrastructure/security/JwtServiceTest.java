
package com.cityfeedback.backend.usermanagement.infrastructure.security;

import com.cityfeedback.backend.usermanagement.domain.UserRole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Base64;

import static org.junit.jupiter.api.Assertions.*;

class JwtServiceTest {

    private JwtService jwtService;

    @BeforeEach
    void setUp() {
        String secret = Base64.getEncoder().encodeToString(
                "this-is-a-very-long-secret-key-for-cityfeedback-123456"
                        .getBytes()
        );

        jwtService = new JwtService(secret, 3600000);
    }

    @Test
    void shouldGenerateValidToken() {
        String token = jwtService.generateToken(
                "citizen@example.com",
                UserRole.CITIZEN
        );

        assertNotNull(token);
        assertTrue(jwtService.isTokenValid(
                token, "citizen@example.com"
        ));
    }

    @Test
    void shouldExtractEmailFromToken() {
        String token = jwtService.generateToken(
                "citizen@example.com",
                UserRole.CITIZEN
        );

        assertEquals(
                "citizen@example.com",
                jwtService.extractEmail(token)
        );
    }

    @Test
    void shouldRejectTokenForDifferentEmail() {
        String token = jwtService.generateToken(
                "citizen@example.com",
                UserRole.CITIZEN
        );

        assertFalse(jwtService.isTokenValid(
                token, "admin@example.com"
        ));
    }

    @Test
    void shouldRejectInvalidToken() {
        assertFalse(jwtService.isTokenValid(
                "invalid.token.value",
                "citizen@example.com"
        ));
    }

    @Test
    void shouldIncludeUserRoleInToken() {
        String token = jwtService.generateToken(
                "admin@example.com",
                UserRole.ADMIN
        );

        assertTrue(jwtService.isTokenValid(
                token, "admin@example.com"
        ));
    }
}
