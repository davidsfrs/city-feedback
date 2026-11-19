package com.cityfeedback.backend.feedbackmanagement.infrastructure.persistence;

import com.cityfeedback.backend.feedbackmanagement.domain.FeedbackCategory;
import com.cityfeedback.backend.feedbackmanagement.domain.FeedbackCategoryRepository;
import org.springframework.stereotype.Repository;
import java.util.*;

@Repository
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

        boolean nameAlreadyExists = categories.values()
                .stream()
                .anyMatch(existingCategory ->
                        existingCategory.getName()
                                .equalsIgnoreCase(category.getName())
                                && !existingCategory.getCategoryId()
                                .equals(category.getCategoryId())
                );

        if (nameAlreadyExists) {
            throw new IllegalArgumentException(
                    "Category name already exists"
            );
        }

        categories.put(category.getCategoryId(), category);
    }

    @Override
    public Optional<FeedbackCategory> findById(UUID id) {
        return Optional.ofNullable(categories.get(id));
    }


    @Override
    public List<FeedbackCategory> findAll() {
        return List.copyOf(categories.values());
    }

}

