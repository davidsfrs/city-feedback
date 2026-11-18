package com.cityfeedback.backend.feedbackmanagement.domain;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class FeedbackTest {

    @Test
    void shouldCreateFeedbackWithValidData() {
        String title = "Kaputte Straßenlaterne";
        String description = "Das geht jetzt schon seit Monaten so. Weserstraße, Ecke Pannierstraße.";

        Feedback feedback = new Feedback(
                title,
                description,
                UUID.randomUUID()
        );

        assertNotNull(feedback);
    }

    @Test
    void shouldStoreTitleAndDescription() {
        String title = "Kaputte Straßenlaterne";
        String description = "Das geht jetzt schon seit Monaten so. Weserstraße, Ecke Pannierstraße.";

        Feedback feedback = new Feedback(
                title,
                description,
                UUID.randomUUID()
        );

        assertEquals(title, feedback.getTitel());
        assertEquals(description, feedback.getBeschreibung());
    }

    @Test
    void shouldGenerateUuidAutomatically() {
        String title = "Kaputte Straßenlaterne";
        String description = "Das geht jetzt schon seit Monaten so. Weserstraße, Ecke Pannierstraße.";

        Feedback feedback = new Feedback(
                title,
                description,
                UUID.randomUUID()
        );

        assertNotNull(feedback.getId());
        assertInstanceOf(UUID.class, feedback.getId());
    }

    @Test
    void shouldHaveSubmittedStatusAfterCreation() {
        String title = "Kaputte Straßenlaterne";
        String description = "Das geht jetzt schon seit Monaten so. Weserstraße, Ecke Pannierstraße.";

        Feedback feedback = new Feedback(
                title,
                description,
                UUID.randomUUID()
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
                () -> new Feedback(
                        null,
                        "The streetlight is not working.",
                        UUID.randomUUID()
                )
        );
    }

    @Test
    void shouldRejectEmptyTitle() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Feedback(
                        "",
                        "The streetlight is not working.",
                        UUID.randomUUID()
                )
        );
    }

    @Test
    void shouldRejectBlankTitle() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Feedback(
                        "   ",
                        "The streetlight is not working.",
                        UUID.randomUUID()
                )
        );
    }

    @Test
    void shouldRejectNullDescription() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Feedback(
                        "Broken streetlight",
                        null,
                        UUID.randomUUID()
                )
        );
    }

    @Test
    void shouldRejectEmptyDescription() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Feedback(
                        "Broken streetlight",
                        "",
                        UUID.randomUUID()
                )
        );
    }

    @Test
    void shouldRejectBlankDescription() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Feedback(
                        "Broken streetlight",
                        "   ",
                        UUID.randomUUID()
                )
        );
    }

}
