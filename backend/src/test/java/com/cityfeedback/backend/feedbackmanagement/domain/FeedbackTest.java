package com.cityfeedback.backend.feedbackmanagement.domain;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class FeedbackTest {

    // 1. Create valid feedback
    @Test
    void shouldCreateFeedbackWithValidData() {
        String title = "Broken streetlight";
        String description = "The streetlight is not working.";
        UUID citizenId = UUID.randomUUID();
        UUID categoryId = UUID.randomUUID();

        Feedback feedback = Feedback.create(
                title,
                description,
                citizenId,
                categoryId
        );

        assertNotNull(feedback);
    }

    // 2. Verify title and description
    @Test
    void shouldStoreTitleAndDescription() {
        String title = "Broken streetlight";
        String description = "The streetlight is not working.";

        Feedback feedback = Feedback.create(
                title,
                description,
                UUID.randomUUID(),
                UUID.randomUUID()
        );

        assertEquals(title, feedback.getTitle());
        assertEquals(description, feedback.getDescription());
    }

    // 3. Verify citizen and category
    @Test
    void shouldStoreCitizenAndCategoryIds() {
        UUID citizenId = UUID.randomUUID();
        UUID categoryId = UUID.randomUUID();

        Feedback feedback = Feedback.create(
                "Broken streetlight",
                "The streetlight is not working.",
                citizenId,
                categoryId
        );

        assertEquals(citizenId, feedback.getCitizenId());
        assertEquals(categoryId, feedback.getCategoryId());
    }

    // 4. Verify automatic UUID generation
    @Test
    void shouldGenerateUuidAutomatically() {
        Feedback feedback = Feedback.create(
                "Broken streetlight",
                "The streetlight is not working.",
                UUID.randomUUID(),
                UUID.randomUUID()
        );

        assertNotNull(feedback.getId());
        assertInstanceOf(UUID.class, feedback.getId());
    }

    // 5. Verify UUID uniqueness
    @Test
    void shouldGenerateDifferentIdsForDifferentFeedbacks() {
        Feedback firstFeedback = Feedback.create(
                "Broken streetlight",
                "The streetlight is not working.",
                UUID.randomUUID(),
                UUID.randomUUID()
        );

        Feedback secondFeedback = Feedback.create(
                "Damaged sidewalk",
                "The sidewalk has several cracks.",
                UUID.randomUUID(),
                UUID.randomUUID()
        );

        assertNotEquals(
                firstFeedback.getId(),
                secondFeedback.getId()
        );
    }

    // 6. Verify initial status SUBMITTED
    @Test
    void shouldHaveSubmittedStatusAfterCreation() {
        Feedback feedback = Feedback.create(
                "Broken streetlight",
                "The streetlight is not working.",
                UUID.randomUUID(),
                UUID.randomUUID()
        );

        assertEquals(
                FeedbackStatus.SUBMITTED,
                feedback.getStatus()
        );
    }

    // 7. Reject null title
    @Test
    void shouldRejectNullTitle() {
        assertThrows(
                IllegalArgumentException.class,
                () -> Feedback.create(
                        null,
                        "The streetlight is not working.",
                        UUID.randomUUID(),
                        UUID.randomUUID()
                )
        );
    }

    // 8. Reject empty title
    @Test
    void shouldRejectEmptyTitle() {
        assertThrows(
                IllegalArgumentException.class,
                () -> Feedback.create(
                        "",
                        "The streetlight is not working.",
                        UUID.randomUUID(),
                        UUID.randomUUID()
                )
        );
    }

    // 9. Reject blank title
    @Test
    void shouldRejectBlankTitle() {
        assertThrows(
                IllegalArgumentException.class,
                () -> Feedback.create(
                        "   ",
                        "The streetlight is not working.",
                        UUID.randomUUID(),
                        UUID.randomUUID()
                )
        );
    }

    // 10. Reject null description
    @Test
    void shouldRejectNullDescription() {
        assertThrows(
                IllegalArgumentException.class,
                () -> Feedback.create(
                        "Broken streetlight",
                        null,
                        UUID.randomUUID(),
                        UUID.randomUUID()
                )
        );
    }

    // 11. Reject empty description
    @Test
    void shouldRejectEmptyDescription() {
        assertThrows(
                IllegalArgumentException.class,
                () -> Feedback.create(
                        "Broken streetlight",
                        "",
                        UUID.randomUUID(),
                        UUID.randomUUID()
                )
        );
    }

    // 12. Reject blank description
    @Test
    void shouldRejectBlankDescription() {
        assertThrows(
                IllegalArgumentException.class,
                () -> Feedback.create(
                        "Broken streetlight",
                        "   ",
                        UUID.randomUUID(),
                        UUID.randomUUID()
                )
        );
    }

    // 13. Reject null citizen ID
    @Test
    void shouldRejectNullCitizenId() {
        assertThrows(
                IllegalArgumentException.class,
                () -> Feedback.create(
                        "Broken streetlight",
                        "The streetlight is not working.",
                        null,
                        UUID.randomUUID()
                )
        );
    }

    // 14. Reject null category ID
    @Test
    void shouldRejectNullCategoryId() {
        assertThrows(
                IllegalArgumentException.class,
                () -> Feedback.create(
                        "Broken streetlight",
                        "The streetlight is not working.",
                        UUID.randomUUID(),
                        null
                )
        );
    }
}
