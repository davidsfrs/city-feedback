package com.cityfeedback.backend.feedbackmanagement.domain;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertFalse;
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
        assertNotNull(category.getCategoryId());
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
                firstCategory.getCategoryId(),
                secondCategory.getCategoryId()
        );
    }


    @Test
    void shouldRejectNullName() {
        // Arrange
        String name = null;
        String description = "Feedback about infrastructure";

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () ->
                new FeedbackCategory(name, description)
        );
    }

    @Test
    void shouldRejectEmptyName() {
        // Arrange
        String name = "";
        String description = "Feedback about infrastructure";

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () ->
                new FeedbackCategory(name, description)
        );
    }

    @Test
    void shouldRejectBlankName() {
        // Arrange
        String name = "   ";
        String description = "Feedback about infrastructure";

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () ->
                new FeedbackCategory(name, description)
        );
    }


    @Test
    void shouldRejectNullDescription() {
        // Arrange
        String name = "Infrastructure";
        String description = null;

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () ->
                new FeedbackCategory(name, description)
        );
    }

    @Test
    void shouldRejectEmptyDescription() {
        // Arrange
        String name = "Infrastructure";
        String description = "";

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () ->
                new FeedbackCategory(name, description)
        );
    }

    @Test
    void shouldRejectBlankDescription() {
        // Arrange
        String name = "Infrastructure";
        String description = "   ";

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () ->
                new FeedbackCategory(name, description)
        );
    }


    @Test
    void shouldCreateCategoryAsActive() {
        // Arrange & Act
        FeedbackCategory category = new FeedbackCategory(
                "Infrastructure",
                "Feedback about roads and public infrastructure"
        );

        // Assert
        assertTrue(category.isActive());
    }


    @Test
    void shouldDeactivateCategory() {
        // Arrange
        FeedbackCategory category = new FeedbackCategory(
                "Infrastructure",
                "Feedback about roads and public infrastructure"
        );

        // Act
        category.deactivate();

        // Assert
        assertFalse(category.isActive());
    }

    @Test
    void shouldReactivateCategory() {
        // Arrange
        FeedbackCategory category = new FeedbackCategory(
                "Infrastructure",
                "Feedback about roads and public infrastructure"
        );

        category.deactivate();

        // Act
        category.activate();

        // Assert
        assertTrue(category.isActive());
    }


}
