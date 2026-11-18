package com.cityfeedback.backend.feedbackmanagement.domain;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class FeedbackTest {

    @Test
    void shouldCreateFeedbackWithValidData() {
        String title = "Kaputte Straßenlaterne";
        String description = "Das geht jetzt schon seit Monaten so. Weserstraße, Ecke Pannierstraße.";

        Feedback feedback = Feedback.createFeedback(
                title,
                description
        );

        assertNotNull(feedback);
    }

    @Test
    void shouldStoreTitleAndDescription() {
        String title = "Kaputte Straßenlaterne";
        String description = "Das geht jetzt schon seit Monaten so. Weserstraße, Ecke Pannierstraße.";

        Feedback feedback = Feedback.createFeedback(
                title,
                description
        );

        assertEquals(title, feedback.getTitel());
        assertEquals(description, feedback.getBeschreibung());
    }

    @Test
    void shouldGenerateUuidAutomatically() {
        Feedback feedback = Feedback.createFeedback(
                "Kaputte Straßenlaterne",
                "as geht jetzt schon seit Monaten so. Weserstraße, Ecke Pannierstraße."
        );

        assertNotNull(feedback.getId());
        assertInstanceOf(UUID.class, feedback.getId());
    }

    @Test
    void shouldHaveSubmittedStatusAfterCreation() {
        Feedback feedback = Feedback.createFeedback(
                "Broken streetlight",
                "The streetlight is not working."
        );

        assertEquals(
                FeedbackStatus.SUBMITTED,
                feedback.getStatus()
        );
    }

    @Test
    void shouldRejectNullTitle() {
        assertThrows(
                IllegalArgumentException.class,
                () -> Feedback.createFeedback(
                        null,
                        "The streetlight is not working."
                )
        );
    }

    @Test
    void shouldRejectEmptyTitle() {
        assertThrows(
                IllegalArgumentException.class,
                () -> Feedback.createFeedback(
                        "",
                        "The streetlight is not working."
                )
        );
    }

    @Test
    void shouldRejectBlankTitle() {
        assertThrows(
                IllegalArgumentException.class,
                () -> Feedback.createFeedback(
                        "   ",
                        "The streetlight is not working."
                )
        );
    }

    @Test
    void shouldRejectNullDescription() {
        assertThrows(
                IllegalArgumentException.class,
                () -> Feedback.createFeedback(
                        "Broken streetlight",
                        null
                )
        );
    }

    @Test
    void shouldRejectEmptyDescription() {
        assertThrows(
                IllegalArgumentException.class,
                () -> Feedback.createFeedback(
                        "Broken streetlight",
                        ""
                )
        );
    }

    @Test
    void shouldRejectBlankDescription() {
        assertThrows(
                IllegalArgumentException.class,
                () -> Feedback.createFeedback(
                        "Broken streetlight",
                        "   "
                )
        );
    }

}
