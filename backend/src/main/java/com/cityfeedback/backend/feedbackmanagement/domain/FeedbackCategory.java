package com.cityfeedback.backend.feedbackmanagement.domain;
import lombok.Getter;
import java.util.UUID;


@Getter
public class FeedbackCategory {
    private final String name;
    private final String description;
    private final UUID id;

    public FeedbackCategory(String name, String description) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.description = description;
    }
}






