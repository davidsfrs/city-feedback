package com.cityfeedback.backend.usermanagement.infrastructure.web;

import com.cityfeedback.backend.usermanagement.application.UserService;
import com.cityfeedback.backend.usermanagement.domain.User;
import com.cityfeedback.backend.usermanagement.domain.UserRole;
import com.cityfeedback.backend.usermanagement.infrastructure.security.JwtService;
import static org.hamcrest.Matchers.hasItems;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import java.util.UUID;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AdminUserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtService jwtService;

    @Test
    void shouldAllowAdminToChangeUserRole() throws Exception {
        // Einen Bürger registrieren
        User citizen = userService.registerCitizen(
                "Max",
                "Mustermann",
                "role-target@example.com",
                "securePassword123"
        );

        // Einen zweiten Benutzer als Administrator vorbereiten
        User admin = userService.registerCitizen(
                "Admin",
                "User",
                "role-admin@example.com",
                "securePassword123"
        );

        userService.changeUserRole(admin.getId(), UserRole.ADMIN);

        // JWT für den Administrator erzeugen
        String token = jwtService.generateToken(
                admin.getEmail().value(),
                admin.getRole()
        );

        // Rolle des Bürgers ändern
        mockMvc.perform(patch("/api/admin/users/" + citizen.getId() + "/role")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "role": "STAFF"
                                }
                                """))
                .andExpect(status().isNoContent());
    }

    @Test
    void shouldForbidCitizenFromChangingUserRole() throws Exception {
        // Bürger, dessen Rolle geändert werden soll
        User targetUser = userService.registerCitizen(
                "Lisa",
                "Musterfrau",
                "lisa@example.com",
                "securePassword123"
        );

        // Ein normaler Bürger als angemeldeter Benutzer
        User citizen = userService.registerCitizen(
                "Max",
                "Mustermann",
                "max@example.com",
                "securePassword123"
        );

        String token = jwtService.generateToken(
                citizen.getEmail().value(),
                citizen.getRole()
        );

        mockMvc.perform(patch("/api/admin/users/" + targetUser.getId() + "/role")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                            {
                                "role": "STAFF"
                            }
                            """))
                .andExpect(status().isForbidden());
    }

    @Test
    void shouldReturnNotFoundWhenChangingRoleOfUnknownUser() throws Exception {
        User admin = userService.registerCitizen(
                "Admin",
                "User",
                "unknown-test-admin@example.com",
                "securePassword123"
        );

        userService.changeUserRole(admin.getId(), UserRole.ADMIN);

        String token = jwtService.generateToken(
                admin.getEmail().value(),
                admin.getRole()
        );

        UUID unknownUserId = UUID.randomUUID();

        mockMvc.perform(patch("/api/admin/users/" + unknownUserId + "/role")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                            {
                                "role": "STAFF"
                            }
                            """))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldReturnAllUsersWithoutPasswordHashes() throws Exception {
        User citizen = userService.registerCitizen(
                "Max",
                "Mustermann",
                "overview-max@example.com",
                "securePassword123"
        );

        User admin = userService.registerCitizen(
                "Admin",
                "User",
                "overview-admin@example.com",
                "securePassword123"
        );

        userService.changeUserRole(admin.getId(), UserRole.ADMIN);

        String token = jwtService.generateToken(
                admin.getEmail().value(),
                admin.getRole()
        );

        mockMvc.perform(get("/api/admin/users")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*].email",
                        hasItems("overview-max@example.com", "overview-admin@example.com")))
                .andExpect(jsonPath("$[*].passwordHash").doesNotExist());
    }

    @Test
    void shouldForbidCitizenFromViewingAllUsers() throws Exception {
        User citizen = userService.registerCitizen(
                "Max",
                "Mustermann",
                "overview-access-citizen@example.com",
                "securePassword123"
        );

        String token = jwtService.generateToken(
                citizen.getEmail().value(),
                citizen.getRole()
        );

        mockMvc.perform(get("/api/admin/users")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isForbidden());
    }

    @Test
    void shouldReturnUnauthorizedWhenNoTokenIsProvided() throws Exception {
        mockMvc.perform(get("/api/admin/users"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void shouldAllowAdminToDeactivateUser() throws Exception {
        User citizen = userService.registerCitizen(
                "Max",
                "Mustermann",
                "deactivate-target@example.com",
                "securePassword123"
        );

        User admin = userService.registerCitizen(
                "Admin",
                "User",
                "deactivate-admin@example.com",
                "securePassword123"
        );

        userService.changeUserRole(admin.getId(), UserRole.ADMIN);

        String token = jwtService.generateToken(
                admin.getEmail().value(),
                UserRole.ADMIN
        );

        mockMvc.perform(patch(
                        "/api/admin/users/" + citizen.getId() + "/deactivate")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isNoContent());
    }

    @Test
    void shouldAllowAdminToActivateUser() throws Exception {
        User citizen = userService.registerCitizen(
                "Max",
                "Mustermann",
                "activate-target@example.com",
                "securePassword123"
        );

        userService.deactivateUser(citizen.getId());

        User admin = userService.registerCitizen(
                "Admin",
                "User",
                "activate-admin@example.com",
                "securePassword123"
        );

        userService.changeUserRole(admin.getId(), UserRole.ADMIN);

        String token = jwtService.generateToken(
                admin.getEmail().value(),
                UserRole.ADMIN
        );

        mockMvc.perform(patch(
                        "/api/admin/users/" + citizen.getId() + "/activate")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isNoContent());
    }

    @Test
    void shouldForbidCitizenFromDeactivatingUser() throws Exception {
        User citizen = userService.registerCitizen(
                "Max",
                "Mustermann",
                "deactivate-forbidden@example.com",
                "securePassword123"
        );

        User target = userService.registerCitizen(
                "Lisa",
                "Musterfrau",
                "deactivate-target-citizen@example.com",
                "securePassword123"
        );

        String token = jwtService.generateToken(
                citizen.getEmail().value(),
                citizen.getRole()
        );

        mockMvc.perform(patch(
                        "/api/admin/users/" + target.getId() + "/deactivate")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isForbidden());
    }

    @Test
    void shouldForbidCitizenFromActivatingUser() throws Exception {
        User citizen = userService.registerCitizen(
                "Max",
                "Mustermann",
                "activate-forbidden-citizen@example.com",
                "securePassword123"
        );

        User target = userService.registerCitizen(
                "Lisa",
                "Musterfrau",
                "activate-forbidden-target@example.com",
                "securePassword123"
        );

        String token = jwtService.generateToken(
                citizen.getEmail().value(),
                citizen.getRole()
        );

        mockMvc.perform(patch(
                        "/api/admin/users/" + target.getId() + "/activate")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isForbidden());
    }
}