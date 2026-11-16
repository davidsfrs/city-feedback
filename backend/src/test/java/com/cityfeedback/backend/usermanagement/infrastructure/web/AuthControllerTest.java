package com.cityfeedback.backend.usermanagement.infrastructure.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.http.MediaType;

@SpringBootTest
@AutoConfigureMockMvc
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldRegisterCitizenSuccessfully() throws Exception {
        String email = "test-" + java.util.UUID.randomUUID() + "@example.com";

        String requestBody = """
        {
            "firstName": "Max",
            "lastName": "Mustermann",
            "email": "%s",
            "password": "SecurePassword123"
        }
        """.formatted(email);

        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.email").value(email))
                .andExpect(jsonPath("$.role").value("CITIZEN"))
                .andExpect(jsonPath("$.token").isNotEmpty());
    }

    @Test
    void shouldLoginSuccessfully() throws Exception {
        String email = "login-" + java.util.UUID.randomUUID() + "@example.com";
        String password = "SecurePassword123";

        // Zuerst einen Benutzer registrieren
        String registerBody = """
        {
            "firstName": "Max",
            "lastName": "Mustermann",
            "email": "%s",
            "password": "%s"
        }
        """.formatted(email, password);

        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(registerBody))
                .andExpect(status().isCreated());

        // Danach mit denselben Zugangsdaten anmelden
        String loginBody = """
        {
            "email": "%s",
            "password": "%s"
        }
        """.formatted(email, password);

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(loginBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").isNotEmpty())
                .andExpect(jsonPath("$.tokenType").value("Bearer"))
                .andExpect(jsonPath("$.email").value(email))
                .andExpect(jsonPath("$.role").value("CITIZEN"));
    }

    @Test
    void shouldRejectLoginWithIncorrectPassword() throws Exception {
        String email = "wrong-password-" + java.util.UUID.randomUUID() + "@example.com";

        // Benutzer registrieren
        String registerBody = """
        {
            "firstName": "Max",
            "lastName": "Mustermann",
            "email": "%s",
            "password": "CorrectPassword123"
        }
        """.formatted(email);

        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(registerBody))
                .andExpect(status().isCreated());

        // Login mit falschem Passwort versuchen
        String loginBody = """
        {
            "email": "%s",
            "password": "WrongPassword123"
        }
        """.formatted(email);

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(loginBody))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void shouldRejectRegistrationWithDuplicateEmail() throws Exception {
        String email = "duplicate-" + java.util.UUID.randomUUID() + "@example.com";

        String requestBody = """
        {
            "firstName": "Max",
            "lastName": "Mustermann",
            "email": "%s",
            "password": "SecurePassword123"
        }
        """.formatted(email);

        // Erste Registrierung muss erfolgreich sein
        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isCreated());

        // Zweite Registrierung mit derselben E-Mail muss abgelehnt werden
        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isConflict());
    }

    @Test
    void shouldRejectRegistrationWithInvalidEmail() throws Exception {
        String requestBody = """
        {
            "firstName": "Max",
            "lastName": "Mustermann",
            "email": "max@",
            "password": "SecurePassword123"
        }
        """;

        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldRejectRegistrationWithTooShortPassword() throws Exception {
        String requestBody = """
        {
            "firstName": "Max",
            "lastName": "Mustermann",
            "email": "short-password@example.com",
            "password": "12345"
        }
        """;

        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldRejectRegistrationWithoutFirstName() throws Exception {
        String requestBody = """
        {
            "firstName": "",
            "lastName": "Mustermann",
            "email": "missing-name@example.com",
            "password": "SecurePassword123"
        }
        """;

        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isBadRequest());
    }
}
