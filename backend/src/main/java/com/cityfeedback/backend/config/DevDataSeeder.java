package com.cityfeedback.backend.config;

import com.cityfeedback.backend.usermanagement.application.exception.UserNotFoundException;
import com.cityfeedback.backend.usermanagement.domain.User;
import com.cityfeedback.backend.usermanagement.domain.UserRole;
import com.cityfeedback.backend.usermanagement.application.UserService;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("dev")
public class DevDataSeeder {

    @Value("${CITYFEEDBACK_DEV_ADMIN_PASSWORD}")
    private String adminPassword;

    @Bean
    CommandLineRunner seedAdmin(UserService userService) {
        return args -> {
            String email = "admin@cityfeedback.local";

            try {
                User admin = userService.findByEmail(email);

                if (admin.getRole() != UserRole.ADMIN) {
                    userService.changeUserRole(
                            admin.getId(),
                            UserRole.ADMIN
                    );
                }

                System.out.println("Development admin is ready.");

            } catch (UserNotFoundException exception) {
                if (adminPassword == null || adminPassword.isBlank()) {
                    throw new IllegalStateException(
                            "CITYFEEDBACK_DEV_ADMIN_PASSWORD is not configured."
                    );
                }

                User admin = userService.registerCitizen(
                        "Dev",
                        "Admin",
                        email,
                        adminPassword
                );

                userService.changeUserRole(
                        admin.getId(),
                        UserRole.ADMIN
                );

                System.out.println("Development admin created.");
            }
        };
    }
}
