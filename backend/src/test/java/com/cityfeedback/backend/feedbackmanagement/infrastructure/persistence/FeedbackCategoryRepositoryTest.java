package com.cityfeedback.backend.feedbackmanagement.infrastructure.persistence;

import com.cityfeedback.backend.feedbackmanagement.domain.FeedbackCategory;
import com.cityfeedback.backend.feedbackmanagement.domain.FeedbackCategoryRepository;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.UUID;

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


    @Test
    void shouldReturnEmptyForUnknownCategoryId() {
        // Arrange
        FeedbackCategoryRepository repository =
                new InMemoryFeedbackCategoryRepository();

        UUID unknownId = UUID.randomUUID();

        // Act
        Optional<FeedbackCategory> result =
                repository.findById(unknownId);

        // Assert
        assertTrue(result.isEmpty());
    }

    @Test
    void shouldRejectNullCategory() {
        // Arrange
        FeedbackCategoryRepository repository =
                new InMemoryFeedbackCategoryRepository();

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () ->
                repository.save(null)
        );
    }


    @Test
    void shouldRejectDuplicateCategoryName() {
        // Arrange
        FeedbackCategoryRepository repository =
                new InMemoryFeedbackCategoryRepository();

        FeedbackCategory firstCategory = new FeedbackCategory(
                "Infrastructure",
                "Feedback about roads"
        );

        FeedbackCategory secondCategory = new FeedbackCategory(
                "Infrastructure",
                "Feedback about public infrastructure"
        );

        repository.save(firstCategory);

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () ->
                repository.save(secondCategory)
        );
    }


    @Test
    void shouldRejectDuplicateNameIgnoringCase() {
        // Arrange
        FeedbackCategoryRepository repository =
                new InMemoryFeedbackCategoryRepository();

        FeedbackCategory firstCategory = new FeedbackCategory(
                "Infrastructure",
                "Feedback about roads"
        );

        FeedbackCategory secondCategory = new FeedbackCategory(
                "infrastructure",
                "Feedback about public infrastructure"
        );

        repository.save(firstCategory);

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () ->
                repository.save(secondCategory)
        );
    }


    @Test
    void shouldAllowSavingSameCategoryAgain() {
        // Arrange
        FeedbackCategoryRepository repository =
                new InMemoryFeedbackCategoryRepository();

        FeedbackCategory category = new FeedbackCategory(
                "Infrastructure",
                "Feedback about roads"
        );

        repository.save(category);

        // Act & Assert
        assertDoesNotThrow(() ->
                repository.save(category)
        );
    }


}
