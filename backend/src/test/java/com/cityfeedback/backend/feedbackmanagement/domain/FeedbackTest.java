package com.cityfeedback.backend.feedbackmanagement.domain;

import com.cityfeedback.backend.feedbackmanagement.domain.Feedback;
import com.cityfeedback.backend.feedbackmanagement.domain.FeedbackStatus;
import org.junit.jupiter.api.Assertions;
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
                UUID.randomUUID(),
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
                UUID.randomUUID(),
                UUID.randomUUID()
        );

        assertEquals(title, feedback.getTitle());
        assertEquals(description, feedback.getDescription());
    }

    @Test
    void shouldGenerateUuidAutomatically() {
        String title = "Kaputte Straßenlaterne";
        String description = "Das geht jetzt schon seit Monaten so. Weserstraße, Ecke Pannierstraße.";

        Feedback feedback = new Feedback(
                title,
                description,
                UUID.randomUUID(),
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
                UUID.randomUUID(),
                UUID.randomUUID()
        );

        Assertions.assertEquals(
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
                        UUID.randomUUID(),
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
                        UUID.randomUUID(),
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
                        UUID.randomUUID(),
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
                        UUID.randomUUID(),
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
                        UUID.randomUUID(),
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
                        UUID.randomUUID(),
                        UUID.randomUUID()
                )
        );
    }

    @Test
    void shouldStoreCategoryId() {
        UUID citizenId = UUID.randomUUID();
        UUID categoryId = UUID.randomUUID();

        Feedback feedback = new Feedback(
                "Titel",
                "Beschreibung",
                citizenId,
                categoryId
        );

        assertEquals(categoryId, feedback.getCategoryId());
    }

    @Test
    void shouldRejectNullCategoryId() {
        UUID citizenId = UUID.randomUUID();

        assertThrows(
                IllegalArgumentException.class,
                () -> new Feedback(
                        "Titel",
                        "Beschreibung",
                        citizenId,
                        null
                )
        );
    }

    @Test
    void shouldRejectNullCitizenId() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Feedback(
                        "Titel",
                        "Beschreibung",
                        null,
                        UUID.randomUUID()
                )
        );
    }

}
