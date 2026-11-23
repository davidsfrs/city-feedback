package com.cityfeedback.backend.feedbackmanagement.domain;

import lombok.Getter;
import java.util.UUID;


@Getter
public class FeedbackCategory {
    private final UUID categoryId;
    private final String name;
    private final String description;
    private boolean active;

    public FeedbackCategory(String name, String description) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Category name must not be blank");
        }
        if (description == null || description.isBlank()) {
            throw new IllegalArgumentException("Category description must not be blank");
        }

        this.categoryId = UUID.randomUUID();
        this.name = name.trim();
        this.description = description.trim();
        this.active = true;
    }


    public void deactivate() {
        this.active = false;
    }

    public void activate() {
        this.active = true;
    }


    public void validateUsableForNewFeedback() {
        if (!active) {
            throw new IllegalStateException(
                    "Inactive category cannot be used for new feedback"
            );
        }
    }
}






