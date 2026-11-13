package com.cityfeedback.backend.feedbackmanagement.domain;

import jakarta.validation.constraints.NotBlank;

public class Feedback {

    @NotBlank(message = "Title must not be empty")
    private String title;

    private String description;

    public Feedback(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }
}