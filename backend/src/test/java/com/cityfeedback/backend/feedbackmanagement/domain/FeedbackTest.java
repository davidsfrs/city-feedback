package com.cityfeedback.backend.feedbackmanagement.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FeedbackTest {

    @Test
    void shouldCreateFeedbackCorrectly() {
        Feedback feedback = new Feedback(
                "Schlagloch in der Straße",
                "Großes Schlagloch vor dem Rathaus"
        );

        assertEquals("Schlagloch in der Straße", feedback.getTitle());
        assertEquals(
                "Großes Schlagloch vor dem Rathaus",
                feedback.getDescription()
        );
    }
}