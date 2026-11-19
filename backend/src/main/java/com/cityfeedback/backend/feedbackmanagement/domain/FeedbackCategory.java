package com.cityfeedback.backend.feedbackmanagement.domain;
import lombok.Getter;
import java.util.UUID;


@Getter
public class FeedbackCategory {
    private final String name;
    private final String description;
    private final UUID id;

    public FeedbackCategory(String name, String description) {

        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException(
                    "Category name must not be null or blank"
            );
        }


        if (description == null || description.isBlank()) {
            throw new IllegalArgumentException(
                    "Category description must not be null or blank"
            );
        }


        this.id = UUID.randomUUID();
        this.name = name;
        this.description = description;
    }
}






