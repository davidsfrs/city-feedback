package com.cityfeedback.backend.feedbackmanagement.infrastructure.persistence;

import com.cityfeedback.backend.feedbackmanagement.domain.FeedbackCategory;
import com.cityfeedback.backend.feedbackmanagement.domain.FeedbackCategoryRepository;
import org.junit.jupiter.api.Test;

import java.util.List;
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
    void shouldRejectNullCategoryId() {
        // Arrange
        FeedbackCategoryRepository repository =
                new InMemoryFeedbackCategoryRepository();

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () ->
                repository.findById(null)
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
    void shouldRejectDuplicateCategoryNameWithWhitespace() {
        // Arrange
        FeedbackCategoryRepository repository =
                new InMemoryFeedbackCategoryRepository();

        FeedbackCategory firstCategory = new FeedbackCategory(
                "Infrastructure",
                "Feedback about roads"
        );

        FeedbackCategory duplicateCategory = new FeedbackCategory(
                "  Infrastructure  ",
                "Another description"
        );

        repository.save(firstCategory);

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () ->
                repository.save(duplicateCategory)
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


    @Test
    void shouldReturnAllSavedCategories() {
        // Arrange
        FeedbackCategoryRepository repository =
                new InMemoryFeedbackCategoryRepository();

        FeedbackCategory firstCategory = new FeedbackCategory(
                "Infrastructure",
                "Feedback about roads"
        );

        FeedbackCategory secondCategory = new FeedbackCategory(
                "Environment",
                "Feedback about environmental issues"
        );

        repository.save(firstCategory);
        repository.save(secondCategory);

        // Act
        List<FeedbackCategory> categories = repository.findAll();

        // Assert
        assertEquals(2, categories.size());
        assertTrue(categories.contains(firstCategory));
        assertTrue(categories.contains(secondCategory));
    }


    @Test
    void shouldReturnEmptyListWhenRepositoryIsEmpty() {
        // Arrange
        FeedbackCategoryRepository repository =
                new InMemoryFeedbackCategoryRepository();

        // Act
        List<FeedbackCategory> categories = repository.findAll();

        // Assert
        assertNotNull(categories);
        assertTrue(categories.isEmpty());
    }


    @Test
    void shouldReturnUnmodifiableCategoryList() {
        // Arrange
        FeedbackCategoryRepository repository =
                new InMemoryFeedbackCategoryRepository();

        FeedbackCategory category = new FeedbackCategory(
                "Infrastructure",
                "Feedback about roads"
        );

        repository.save(category);

        List<FeedbackCategory> categories = repository.findAll();

        // Act & Assert
        assertThrows(UnsupportedOperationException.class, () ->
                categories.clear()
        );

        assertEquals(1, repository.findAll().size());
    }
}