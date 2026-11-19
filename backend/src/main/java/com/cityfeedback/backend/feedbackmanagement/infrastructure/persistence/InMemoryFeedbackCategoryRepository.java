package com.cityfeedback.backend.feedbackmanagement.infrastructure.persistence;

import com.cityfeedback.backend.feedbackmanagement.domain.FeedbackCategory;
import com.cityfeedback.backend.feedbackmanagement.domain.FeedbackCategoryRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class InMemoryFeedbackCategoryRepository
        implements FeedbackCategoryRepository {

    private final Map<UUID, FeedbackCategory> categories =
            new HashMap<>();

    @Override
    public void save(FeedbackCategory category) {
        if (category == null) {
            throw new IllegalArgumentException(
                    "Feedback category must not be null"
            );
        }

        categories.put(category.getCategoryId(), category);
    }

    @Override
    public Optional<FeedbackCategory> findById(UUID id) {
        return Optional.ofNullable(categories.get(id));
    }
}

