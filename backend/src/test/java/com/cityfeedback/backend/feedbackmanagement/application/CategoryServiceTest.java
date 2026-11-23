package com.cityfeedback.backend.feedbackmanagement.application;

import com.cityfeedback.backend.feedbackmanagement.domain.FeedbackCategory;
import com.cityfeedback.backend.feedbackmanagement.domain.FeedbackCategoryRepository;
import com.cityfeedback.backend.feedbackmanagement.infrastructure.persistence.InMemoryFeedbackCategoryRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;
import java.util.List;
import java.util.NoSuchElementException;

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


    @Test
    void shouldGetCategoryById() {
        // Arrange
        FeedbackCategory category = new FeedbackCategory(
                "Infrastructure",
                "Feedback about roads"
        );

        categoryRepository.save(category);

        // Act
        FeedbackCategory result =
                categoryService.getCategoryById(category.getCategoryId());

        // Assert
        assertEquals(category.getCategoryId(), result.getCategoryId());
        assertEquals(category.getName(), result.getName());
        assertEquals(category.getDescription(), result.getDescription());
    }

    @Test
    void shouldThrowWhenCategoryDoesNotExist() {
        // Arrange
        UUID unknownId = UUID.randomUUID();

        // Act & Assert
        assertThrows(NoSuchElementException.class, () ->
                categoryService.getCategoryById(unknownId)
        );
    }

    @Test
    void shouldGetAllCategories() {
        // Arrange
        FeedbackCategory firstCategory = new FeedbackCategory(
                "Infrastructure",
                "Feedback about roads"
        );

        FeedbackCategory secondCategory = new FeedbackCategory(
                "Environment",
                "Feedback about environmental issues"
        );

        categoryRepository.save(firstCategory);
        categoryRepository.save(secondCategory);

        // Act
        List<FeedbackCategory> categories =
                categoryService.getAllCategories();

        // Assert
        assertEquals(2, categories.size());
        assertTrue(categories.contains(firstCategory));
        assertTrue(categories.contains(secondCategory));
    }

    @Test
    void shouldDeactivateCategory() {
        // Arrange
        FeedbackCategory category = new FeedbackCategory(
                "Infrastructure",
                "Feedback about roads"
        );

        categoryRepository.save(category);

        // Act
        categoryService.deactivateCategory(category.getCategoryId());

        // Assert
        FeedbackCategory updatedCategory = categoryRepository
                .findById(category.getCategoryId())
                .orElseThrow();

        assertFalse(updatedCategory.isActive());
    }

    @Test
    void shouldReactivateCategory() {
        // Arrange
        FeedbackCategory category = new FeedbackCategory(
                "Infrastructure",
                "Feedback about roads"
        );

        category.deactivate();
        categoryRepository.save(category);

        // Act
        categoryService.activateCategory(category.getCategoryId());

        // Assert
        FeedbackCategory updatedCategory = categoryRepository
                .findById(category.getCategoryId())
                .orElseThrow();

        assertTrue(updatedCategory.isActive());
    }

    @Test
    void shouldRejectDeactivationOfUnknownCategory() {
        // Arrange
        UUID unknownId = UUID.randomUUID();

        // Act & Assert
        assertThrows(NoSuchElementException.class, () ->
                categoryService.deactivateCategory(unknownId)
        );
    }

    @Test
    void shouldReturnOnlyActiveCategories() {
        // Arrange
        FeedbackCategoryRepository repository =
                new InMemoryFeedbackCategoryRepository();

        CategoryService service = new CategoryService(repository);

        FeedbackCategory activeCategory = new FeedbackCategory(
                "Infrastructure",
                "Feedback about roads"
        );

        FeedbackCategory inactiveCategory = new FeedbackCategory(
                "Environment",
                "Feedback about environmental issues"
        );

        inactiveCategory.deactivate();

        repository.save(activeCategory);
        repository.save(inactiveCategory);

        // Act
        List<FeedbackCategory> result = service.getActiveCategories();

        // Assert
        assertEquals(1, result.size());
        assertEquals("Infrastructure", result.get(0).getName());
    }

    @Test
    void shouldReturnAllCategoriesIncludingInactiveOnes() {
        // Arrange
        FeedbackCategoryRepository repository =
                new InMemoryFeedbackCategoryRepository();

        CategoryService service = new CategoryService(repository);

        FeedbackCategory active = new FeedbackCategory(
                "Infrastructure",
                "Feedback about roads"
        );

        FeedbackCategory inactive = new FeedbackCategory(
                "Environment",
                "Environmental issues"
        );

        inactive.deactivate();

        repository.save(active);
        repository.save(inactive);

        // Act
        List<FeedbackCategory> result = service.getAllCategories();

        // Assert
        assertEquals(2, result.size());
    }
}
