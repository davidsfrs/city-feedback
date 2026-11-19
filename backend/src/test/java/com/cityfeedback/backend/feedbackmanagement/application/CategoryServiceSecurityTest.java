package com.cityfeedback.backend.feedbackmanagement.application;

import com.cityfeedback.backend.feedbackmanagement.domain.FeedbackCategory;
import com.cityfeedback.backend.feedbackmanagement.domain.FeedbackCategoryRepository;
import com.cityfeedback.backend.feedbackmanagement.infrastructure.persistence.InMemoryFeedbackCategoryRepository;
import com.cityfeedback.backend.feedbackmanagement.infrastructure.security.CategoryMethodSecurityConfig;
import com.cityfeedback.backend.usermanagement.domain.UserRole;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CategoryServiceSecurityTest {

    private AnnotationConfigApplicationContext context;
    private CategoryService categoryService;
    private FeedbackCategoryRepository categoryRepository;

    @BeforeEach
    void setUp() {
        context = new AnnotationConfigApplicationContext();

        context.register(
                CategoryMethodSecurityConfig.class,
                TestConfig.class
        );

        context.refresh();

        categoryService = context.getBean(CategoryService.class);
        categoryRepository =
                context.getBean(FeedbackCategoryRepository.class);
    }

    @AfterEach
    void tearDown() {
        SecurityContextHolder.clearContext();
        context.close();
    }

    @Configuration
    static class TestConfig {

        @Bean
        FeedbackCategoryRepository categoryRepository() {
            return new InMemoryFeedbackCategoryRepository();
        }

        @Bean
        CategoryService categoryService(
                FeedbackCategoryRepository repository
        ) {
            return new CategoryService(repository);
        }
    }

    private void authenticateAs(UserRole role) {
        var authentication =
                new UsernamePasswordAuthenticationToken(
                        "test-user",
                        "password",
                        List.of(
                                new SimpleGrantedAuthority(
                                        "ROLE_" + role.name()
                                )
                        )
                );

        SecurityContextHolder.getContext()
                .setAuthentication(authentication);
    }

    @Test
    void shouldAllowAdminToCreateCategory() {
        // Arrange
        authenticateAs(UserRole.ADMIN);

        // Act
        FeedbackCategory category =
                categoryService.createCategory(
                        "Infrastructure",
                        "Feedback about roads"
                );

        // Assert
        assertNotNull(category.getCategoryId());
        assertTrue(
                categoryRepository.findById(category.getCategoryId())
                        .isPresent()
        );
    }

    @Test
    void shouldRejectCitizenCreatingCategory() {
        // Arrange
        authenticateAs(UserRole.CITIZEN);

        // Act & Assert
        assertThrows(AccessDeniedException.class, () ->
                categoryService.createCategory(
                        "Infrastructure",
                        "Feedback about roads"
                )
        );

        assertTrue(categoryRepository.findAll().isEmpty());
    }

    @Test
    void shouldRejectStaffDeactivatingCategory() {
        // Arrange
        FeedbackCategory category = new FeedbackCategory(
                "Infrastructure",
                "Feedback about roads"
        );

        categoryRepository.save(category);
        authenticateAs(UserRole.STAFF);

        // Act & Assert
        assertThrows(AccessDeniedException.class, () ->
                categoryService.deactivateCategory(category.getCategoryId())
        );

        assertTrue(category.isActive());
    }

    @Test
    void shouldRejectCitizenReactivatingCategory() {
        // Arrange
        FeedbackCategory category = new FeedbackCategory(
                "Infrastructure",
                "Feedback about roads"
        );

        category.deactivate();
        categoryRepository.save(category);

        authenticateAs(UserRole.CITIZEN);

        // Act & Assert
        assertThrows(AccessDeniedException.class, () ->
                categoryService.activateCategory(category.getCategoryId())
        );

        assertFalse(category.isActive());
    }

    @Test
    void shouldAllowAdminToManageCategoryLifecycle() {
        // Arrange
        FeedbackCategory category = new FeedbackCategory(
                "Infrastructure",
                "Feedback about roads"
        );

        categoryRepository.save(category);
        authenticateAs(UserRole.ADMIN);

        // Act & Assert
        categoryService.deactivateCategory(category.getCategoryId());
        assertFalse(category.isActive());

        categoryService.activateCategory(category.getCategoryId());
        assertTrue(category.isActive());
    }
}

