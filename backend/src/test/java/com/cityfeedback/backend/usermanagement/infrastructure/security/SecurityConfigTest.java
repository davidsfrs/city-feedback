package com.cityfeedback.backend.usermanagement.infrastructure.security;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.security.web.SecurityFilterChain;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@SpringBootTest
@AutoConfigureMockMvc
class SecurityConfigTest {

    @Autowired
    private SecurityFilterChain securityFilterChain;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldLoadSecurityFilterChain() {
        assertNotNull(securityFilterChain);
    }

    @Test
    void citizenShouldReceiveForbiddenWhenAccessingAdminEndpoint()
            throws Exception {

        mockMvc.perform(get("/api/admin/users")
                        .with(user("citizen@example.com")
                                .roles("CITIZEN")))
                .andExpect(status().isForbidden());
    }

    @Test
    void shouldReturnUnauthorizedWhenAccessingAdminEndpointWithoutToken()
            throws Exception {

        mockMvc.perform(get("/api/admin/users"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void adminShouldReceiveOkWhenAccessingAdminEndpoint() throws Exception {
        mockMvc.perform(get("/api/admin/users")
                        .with(user("admin@example.com").roles("ADMIN")))
                .andExpect(status().isOk());
    }
}