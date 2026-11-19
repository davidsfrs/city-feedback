package com.cityfeedback.backend.feedbackmanagement.application;

import com.cityfeedback.backend.feedbackmanagement.domain.FeedbackCategory;
import com.cityfeedback.backend.feedbackmanagement.domain.FeedbackCategoryRepository;

import org.springframework.stereotype.Service;

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
}

