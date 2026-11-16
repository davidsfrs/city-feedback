package com.cityfeedback.backend.usermanagement.infrastructure.security;

import com.cityfeedback.backend.usermanagement.application.BCryptPasswordHasher;
import com.cityfeedback.backend.usermanagement.application.PasswordHasher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PasswordSecurityConfig {

    @Bean
    public PasswordHasher passwordHasher() {
        return new BCryptPasswordHasher();
    }
}