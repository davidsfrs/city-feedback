package com.cityfeedback.backend.feedbackmanagement.domain;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FeedbackCategoryTest {

    @Test
    void shouldCreateCategoryWithValidData() {
        // Arrange
        String name = "Infrastructure";
        String description = "Feedback about roads and public infrastructure";

        // Act
        FeedbackCategory category = new FeedbackCategory(
                name,
                description
        );

        // Assert
        assertEquals(name, category.getName());
        assertEquals(description, category.getDescription());
    }


    @Test
    void shouldGenerateCategoryId() {
        // Arrange & Act
        FeedbackCategory category = new FeedbackCategory(
                "Infrastructure",
                "Feedback about roads and public infrastructure"
        );

        // Assert
        assertNotNull(category.getId());
    }

    @Test
    void shouldGenerateUniqueCategoryIds() {
        // Arrange & Act
        FeedbackCategory firstCategory = new FeedbackCategory(
                "Infrastructure",
                "Feedback about roads and public infrastructure"
        );

        FeedbackCategory secondCategory = new FeedbackCategory(
                "Environment",
                "Feedback about environmental issues"
        );

        // Assert
        assertNotEquals(
                firstCategory.getId(),
                secondCategory.getId()
        );
    }

}
