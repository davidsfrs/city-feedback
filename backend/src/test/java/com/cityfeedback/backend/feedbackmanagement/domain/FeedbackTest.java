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


    // 4. Verify automatic UUID generation
    @Test
    void shouldGenerateUuidAutomatically() {
        Feedback feedback = Feedback.createFeedback(
                "Kaputte Straßenlaterne",
                "as geht jetzt schon seit Monaten so. Weserstraße, Ecke Pannierstraße."
        );

        assertNotNull(feedback.getId());
        assertInstanceOf(UUID.class, feedback.getId());
    }

    // 6. Verify initial status SUBMITTED
    @Test
    void shouldHaveSubmittedStatusAfterCreation() {
        Feedback feedback = Feedback.createFeedback(
                "Broken streetlight",
                "The streetlight is not working."
        );

        assertEquals(
                "SUBMITTED",
                feedback.getStatus()
        );
    }

    // 7. Reject null title
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

    // 8. Reject empty title
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



    // 9. Reject blank title
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


    // 10. Reject null description
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

    // 11. Reject empty description
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

    // 12. Reject blank description
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
