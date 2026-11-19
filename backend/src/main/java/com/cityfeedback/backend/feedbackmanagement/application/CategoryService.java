package com.cityfeedback.backend.feedbackmanagement.application;

import com.cityfeedback.backend.feedbackmanagement.domain.FeedbackCategory;
import com.cityfeedback.backend.feedbackmanagement.domain.FeedbackCategoryRepository;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class CategoryService {

    private final FeedbackCategoryRepository categoryRepository;

    public CategoryService(FeedbackCategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public FeedbackCategory createCategory(
            String name,
            String description
    ) {
        FeedbackCategory category = new FeedbackCategory(
                name,
                description
        );

        categoryRepository.save(category);

        return category;
    }


    public FeedbackCategory getCategoryById(UUID categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(() ->
                        new NoSuchElementException(
                                "Category not found: " + categoryId
                        )
                );
    }

    public List<FeedbackCategory> getAllCategories() {
        return categoryRepository.findAll();
    }


    public void deactivateCategory(UUID categoryId) {
        FeedbackCategory category = getCategoryById(categoryId);

        category.deactivate();

        categoryRepository.save(category);
    }

    public void activateCategory(UUID categoryId) {
        FeedbackCategory category = getCategoryById(categoryId);

        category.activate();

        categoryRepository.save(category);
    }

}

