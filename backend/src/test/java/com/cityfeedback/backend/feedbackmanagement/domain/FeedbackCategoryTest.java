package com.cityfeedback.backend.feedbackmanagement.domain;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
}
