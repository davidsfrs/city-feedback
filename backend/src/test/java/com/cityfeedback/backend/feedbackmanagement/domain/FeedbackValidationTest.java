package com.cityfeedback.backend.feedbackmanagement.domain;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FeedbackValidationTest {

    private final Validator validator =
            Validation.buildDefaultValidatorFactory().getValidator();

    @Test
    void shouldRejectEmptyFeedbackTitle() {
        Feedback feedback = new Feedback("", "Eine Beschreibung");

        var violations = validator.validate(feedback);

        assertFalse(violations.isEmpty());
    }
}