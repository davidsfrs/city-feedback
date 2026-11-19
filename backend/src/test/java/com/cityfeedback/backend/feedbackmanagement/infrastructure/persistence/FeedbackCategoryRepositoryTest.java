package com.cityfeedback.backend.feedbackmanagement.infrastructure.persistence;

import com.cityfeedback.backend.feedbackmanagement.domain.FeedbackCategory;
import com.cityfeedback.backend.feedbackmanagement.domain.FeedbackCategoryRepository;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryFeedbackCategoryRepositoryTest {

    @Test
    void shouldSaveAndFindCategoryById() {
        // Arrange
        FeedbackCategoryRepository repository =
                new InMemoryFeedbackCategoryRepository();

        FeedbackCategory category = new FeedbackCategory(
                "Infrastructure",
                "Feedback about infrastructure"
        );

        // Act
        repository.save(category);

        Optional<FeedbackCategory> result =
                repository.findById(category.getCategoryId());

        // Assert
        assertTrue(result.isPresent());
        assertEquals(category.getCategoryId(), result.get().getCategoryId());
        assertEquals(category.getName(), result.get().getName());
        assertEquals(
                category.getDescription(),
                result.get().getDescription()
        );
    }
}
