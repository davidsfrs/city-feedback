package com.cityfeedback.backend.feedbackmanagement.domain;
import lombok.Getter;

@Getter
public class FeedbackCategory {
    private final String name;
    private final String description;

    public FeedbackCategory(String name, String description) {
        this.name = name;
        this.description = description;
    }
}






