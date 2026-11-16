package com.cityfeedback.backend.usermanagement.infrastructure.web;

import com.cityfeedback.backend.usermanagement.application.UserService;
import com.cityfeedback.backend.usermanagement.domain.User;
import com.cityfeedback.backend.usermanagement.infrastructure.security.JwtService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.http.MediaType;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;

@SpringBootTest
@AutoConfigureMockMvc
class UserProfileControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtService jwtService;

    @Test
    void shouldReturnOwnProfileWhenAuthenticated() throws Exception {
        User user = userService.registerCitizen(
                "Max",
                "Mustermann",
                "max.profile@example.com",
                "password123"
        );

        String token = jwtService.generateToken(
                user.getEmail().value(),
                user.getRole()
        );

        mockMvc.perform(get("/api/users/me")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(user.getId().toString()))
                .andExpect(jsonPath("$.firstName").value("Max"))
                .andExpect(jsonPath("$.lastName").value("Mustermann"))
                .andExpect(jsonPath("$.email").value("max.profile@example.com"))
                .andExpect(jsonPath("$.passwordHash").doesNotExist());
    }

    @Test
    void shouldReturnUnauthorizedWhenNoTokenIsProvided() throws Exception {
        mockMvc.perform(get("/api/users/me"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void shouldReturnOnlyAuthenticatedUsersOwnProfile() throws Exception {
        User userA = userService.registerCitizen(
                "Max",
                "Mustermann",
                "max.usera@example.com",
                "password123"
        );

        userService.registerCitizen(
                "Anna",
                "Beispiel",
                "anna.userb@example.com",
                "password123"
        );

        String token = jwtService.generateToken(
                userA.getEmail().value(),
                userA.getRole()
        );

        mockMvc.perform(get("/api/users/me")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("max.usera@example.com"))
                .andExpect(jsonPath("$.email").value(
                        org.hamcrest.Matchers.not("anna.userb@example.com")
                ));
    }

    @Test
    void shouldAllowUserToUpdateOwnName() throws Exception {
        User user = userService.registerCitizen(
                "Max",
                "Mustermann",
                "max.update@example.com",
                "password123"
        );

        String token = jwtService.generateToken(
                user.getEmail().value(),
                user.getRole()
        );

        mockMvc.perform(patch("/api/users/me")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                            {
                                "firstName": "Maximilian",
                                "lastName": "Muster"
                            }
                            """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("Maximilian"))
                .andExpect(jsonPath("$.lastName").value("Muster"))
                .andExpect(jsonPath("$.email").value("max.update@example.com"));
    }

    @Test
    void shouldRejectUpdateWhenFirstNameIsBlank() throws Exception {
        User user = userService.registerCitizen(
                "Max",
                "Mustermann",
                "max.blank-test@example.com",
                "password123"
        );

        String token = jwtService.generateToken(
                user.getEmail().value(),
                user.getRole()
        );

        mockMvc.perform(patch("/api/users/me")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                            {
                                "firstName": "",
                                "lastName": "Mustermann"
                            }
                            """))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldRejectUpdateWhenLastNameIsBlank() throws Exception {
        User user = userService.registerCitizen(
                "Max",
                "Mustermann",
                "max.blank-lastname@example.com",
                "password123"
        );

        String token = jwtService.generateToken(
                user.getEmail().value(),
                user.getRole()
        );

        mockMvc.perform(patch("/api/users/me")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                            {
                                "firstName": "Max",
                                "lastName": ""
                            }
                            """))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldKeepOriginalNameWhenUpdateIsInvalid() throws Exception {
        User user = userService.registerCitizen(
                "Max",
                "Mustermann",
                "max.invalid-update@example.com",
                "password123"
        );

        String token = jwtService.generateToken(
                user.getEmail().value(),
                user.getRole()
        );

        mockMvc.perform(patch("/api/users/me")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                            {
                                "firstName": "",
                                "lastName": "Muster"
                            }
                            """))
                .andExpect(status().isBadRequest());

        User unchangedUser = userService.findByEmail(
                user.getEmail().value()
        );

        assertEquals("Max", unchangedUser.getFirstName());
        assertEquals("Mustermann", unchangedUser.getLastName());
    }
}