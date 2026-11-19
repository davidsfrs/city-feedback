package com.cityfeedback.backend.feedbackmanagement.application;

import com.cityfeedback.backend.feedbackmanagement.domain.FeedbackCategory;
import com.cityfeedback.backend.feedbackmanagement.domain.FeedbackCategoryRepository;
import com.cityfeedback.backend.feedbackmanagement.infrastructure.persistence.InMemoryFeedbackCategoryRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CategoryServiceTest {

    private CategoryService categoryService;
    private FeedbackCategoryRepository categoryRepository;

    @BeforeEach
    void setUp() {
        categoryRepository = new InMemoryFeedbackCategoryRepository();
        categoryService = new CategoryService(categoryRepository);
    }

    @Test
    void shouldCreateCategory() {
        // Arrange
        String name = "Infrastructure";
        String description = "Feedback about roads";

        // Act
        FeedbackCategory category =
                categoryService.createCategory(name, description);

        // Assert
        assertNotNull(category);
        assertEquals(name, category.getName());
        assertEquals(description, category.getDescription());
        assertNotNull(category.getCategoryId());
    }

    @Test
    void shouldPersistCreatedCategory() {
        // Arrange
        String name = "Infrastructure";
        String description = "Feedback about roads";

        // Act
        FeedbackCategory category =
                categoryService.createCategory(name, description);

        // Assert
        assertTrue(
                categoryRepository.findById(category.getCategoryId()).isPresent()
        );
    }

    @Test
    void shouldRejectInvalidCategoryData() {
        // Arrange
        String name = "";
        String description = "Feedback about roads";

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () ->
                categoryService.createCategory(name, description)
        );
    }
}
