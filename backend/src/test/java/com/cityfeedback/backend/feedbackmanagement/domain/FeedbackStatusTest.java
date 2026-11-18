package com.cityfeedback.backend.feedbackmanagement.domain;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class FeedbackStatusTest {

    @Test
    void shouldHaveAllDefinedFeedbackStatuses() {
        FeedbackStatus[] statuses = FeedbackStatus.values();

        assertEquals(6, statuses.length);
        assertNotNull(FeedbackStatus.valueOf("SUBMITTED"));
        assertNotNull(FeedbackStatus.valueOf("UNDER_REVIEW"));
        assertNotNull(FeedbackStatus.valueOf("ACCEPTED"));
        assertNotNull(FeedbackStatus.valueOf("REJECTED"));
        assertNotNull(FeedbackStatus.valueOf("IN_PROGRESS"));
        assertNotNull(FeedbackStatus.valueOf("RESOLVED"));
    }

    @Test
    void shouldInitializeFeedbackWithSubmittedStatus() {
        Feedback feedback = new Feedback("Kaputte Straßenlaterne",
                "Das geht jetzt schon seit Monaten so. Weserstraße, Ecke Pannierstraße.",
                UUID.randomUUID());

        assertEquals(FeedbackStatus.SUBMITTED, feedback.getStatus());
    }

    @Test
    void shouldAllowTransitionFromSubmittedToUnderReview() {
        assertTrue(
                FeedbackStatus.SUBMITTED.canTransitionTo(
                        FeedbackStatus.UNDER_REVIEW
                )
        );
    }

    @Test
    void shouldAllowTransitionFromUnderReviewToAccepted() {
        assertTrue(
                FeedbackStatus.UNDER_REVIEW.canTransitionTo(
                        FeedbackStatus.ACCEPTED
                )
        );
    }

    @Test
    void shouldAllowTransitionFromUnderReviewToRejected() {
        assertTrue(
                FeedbackStatus.UNDER_REVIEW.canTransitionTo(
                        FeedbackStatus.REJECTED
                )
        );
    }

    @Test
    void shouldAllowTransitionFromAcceptedToInProgress() {
        assertTrue(
                FeedbackStatus.ACCEPTED.canTransitionTo(
                        FeedbackStatus.IN_PROGRESS
                )
        );
    }

    @Test
    void shouldAllowTransitionFromInProgressToResolved() {
        assertTrue(
                FeedbackStatus.IN_PROGRESS.canTransitionTo(
                        FeedbackStatus.RESOLVED
                )
        );
    }

    @Test
    void shouldRejectTransitionFromSubmittedToAccepted() {
        assertFalse(FeedbackStatus.SUBMITTED.canTransitionTo(FeedbackStatus.ACCEPTED));
    }

    @Test
    void shouldRejectTransitionFromSubmittedToRejected() {
        assertFalse(FeedbackStatus.SUBMITTED.canTransitionTo(FeedbackStatus.REJECTED));
    }

    @Test
    void shouldRejectTransitionFromSubmittedToInProgress() {
        assertFalse(FeedbackStatus.SUBMITTED.canTransitionTo(FeedbackStatus.IN_PROGRESS));
    }

    @Test
    void shouldRejectTransitionFromSubmittedToResolved() {
        assertFalse(FeedbackStatus.SUBMITTED.canTransitionTo(FeedbackStatus.RESOLVED));
    }

    @Test
    void shouldRejectTransitionFromUnderReviewToSubmitted() {
        assertFalse(FeedbackStatus.UNDER_REVIEW.canTransitionTo(FeedbackStatus.SUBMITTED));
    }

    @Test
    void shouldRejectTransitionFromUnderReviewToInProgress() {
        assertFalse(FeedbackStatus.UNDER_REVIEW.canTransitionTo(FeedbackStatus.IN_PROGRESS));
    }

    @Test
    void shouldRejectTransitionFromUnderReviewToResolved() {
        assertFalse(FeedbackStatus.UNDER_REVIEW.canTransitionTo(FeedbackStatus.RESOLVED));
    }

    @Test
    void shouldRejectTransitionFromAcceptedToSubmitted() {
        assertFalse(FeedbackStatus.ACCEPTED.canTransitionTo(FeedbackStatus.SUBMITTED));
    }

    @Test
    void shouldRejectTransitionFromAcceptedToUnderReview() {
        assertFalse(FeedbackStatus.ACCEPTED.canTransitionTo(FeedbackStatus.UNDER_REVIEW));
    }

    @Test
    void shouldRejectTransitionFromAcceptedToRejected() {
        assertFalse(FeedbackStatus.ACCEPTED.canTransitionTo(FeedbackStatus.REJECTED));
    }

    @Test
    void shouldRejectTransitionFromAcceptedToResolved() {
        assertFalse(FeedbackStatus.ACCEPTED.canTransitionTo(FeedbackStatus.RESOLVED));
    }

    @Test
    void shouldRejectTransitionFromRejectedToSubmitted() {
        assertFalse(FeedbackStatus.REJECTED.canTransitionTo(FeedbackStatus.SUBMITTED));
    }

    @Test
    void shouldRejectTransitionFromRejectedToUnderReview() {
        assertFalse(FeedbackStatus.REJECTED.canTransitionTo(FeedbackStatus.UNDER_REVIEW));
    }

    @Test
    void shouldRejectTransitionFromRejectedToAccepted() {
        assertFalse(FeedbackStatus.REJECTED.canTransitionTo(FeedbackStatus.ACCEPTED));
    }

    @Test
    void shouldRejectTransitionFromRejectedToInProgress() {
        assertFalse(FeedbackStatus.REJECTED.canTransitionTo(FeedbackStatus.IN_PROGRESS));
    }

    @Test
    void shouldRejectTransitionFromRejectedToResolved() {
        assertFalse(FeedbackStatus.REJECTED.canTransitionTo(FeedbackStatus.RESOLVED));
    }

    @Test
    void shouldRejectTransitionFromInProgressToSubmitted() {
        assertFalse(FeedbackStatus.IN_PROGRESS.canTransitionTo(FeedbackStatus.SUBMITTED));
    }

    @Test
    void shouldRejectTransitionFromInProgressToUnderReview() {
        assertFalse(FeedbackStatus.IN_PROGRESS.canTransitionTo(FeedbackStatus.UNDER_REVIEW));
    }

    @Test
    void shouldRejectTransitionFromInProgressToAccepted() {
        assertFalse(FeedbackStatus.IN_PROGRESS.canTransitionTo(FeedbackStatus.ACCEPTED));
    }

    @Test
    void shouldRejectTransitionFromInProgressToRejected() {
        assertFalse(FeedbackStatus.IN_PROGRESS.canTransitionTo(FeedbackStatus.REJECTED));
    }

    @Test
    void shouldRejectTransitionFromResolvedToSubmitted() {
        assertFalse(FeedbackStatus.RESOLVED.canTransitionTo(FeedbackStatus.SUBMITTED));
    }

    @Test
    void shouldRejectTransitionFromResolvedToUnderReview() {
        assertFalse(FeedbackStatus.RESOLVED.canTransitionTo(FeedbackStatus.UNDER_REVIEW));
    }

    @Test
    void shouldRejectTransitionFromResolvedToAccepted() {
        assertFalse(FeedbackStatus.RESOLVED.canTransitionTo(FeedbackStatus.ACCEPTED));
    }

    @Test
    void shouldRejectTransitionFromResolvedToRejected() {
        assertFalse(FeedbackStatus.RESOLVED.canTransitionTo(FeedbackStatus.REJECTED));
    }

    @Test
    void shouldRejectTransitionFromResolvedToInProgress() {
        assertFalse(FeedbackStatus.RESOLVED.canTransitionTo(FeedbackStatus.IN_PROGRESS));
    }

    @Test
    void shouldRejectTransitionToNullStatus() {
        for (FeedbackStatus status : FeedbackStatus.values()) {
            assertFalse(status.canTransitionTo(null));
        }
    }

    @Test
    void shouldRejectTransitionToSameStatus() {
        for (FeedbackStatus status : FeedbackStatus.values()) {
            assertFalse(status.canTransitionTo(status));
        }
    }

    @Test
void shouldUpdateModificationTimestampOnStatusChange() {
        Feedback feedback = new Feedback("Kaputte Straßenlaterne",
                "Das geht jetzt schon seit Monaten so. Weserstraße, Ecke Pannierstraße.",
                UUID.randomUUID());

    LocalDateTime originalTimestamp = feedback.getAktualisiertAm();

    feedback.changeStatus(FeedbackStatus.UNDER_REVIEW);

    assertFalse(
        feedback.getAktualisiertAm().isBefore(originalTimestamp)
    );
}

@Test
void shouldNotUpdateModificationTimestampOnInvalidStatusChange() {
    Feedback feedback = new Feedback("Kaputte Straßenlaterne",
            "Das geht jetzt schon seit Monaten so. Weserstraße, Ecke Pannierstraße.",
            UUID.randomUUID());

    LocalDateTime originalTimestamp = feedback.getAktualisiertAm();

    assertThrows(
        IllegalStateException.class,
        () -> feedback.changeStatus(FeedbackStatus.RESOLVED)
    );

    assertEquals(
        originalTimestamp,
        feedback.getAktualisiertAm()
    );
}

@Test
void shouldPreserveCreationTimestampOnStatusChange() {
    Feedback feedback = new Feedback("Kaputte Straßenlaterne",
            "Das geht jetzt schon seit Monaten so. Weserstraße, Ecke Pannierstraße.",
            UUID.randomUUID());

    LocalDateTime originalCreationTimestamp = feedback.getErstelltAm();

    feedback.changeStatus(FeedbackStatus.UNDER_REVIEW);

    assertEquals(
        originalCreationTimestamp,
        feedback.getErstelltAm()
    );
}

    @Test
    void shouldPreserveCurrentStatusOnInvalidTransition() {
        Feedback feedback = new Feedback("Kaputte Straßenlaterne",
                "Das geht jetzt schon seit Monaten so. Weserstraße, Ecke Pannierstraße.",
                UUID.randomUUID());

        FeedbackStatus originalStatus = feedback.getStatus();

        assertThrows(
                IllegalStateException.class,
                () -> feedback.changeStatus(FeedbackStatus.RESOLVED)
        );

        assertEquals(originalStatus, feedback.getStatus());
    }

}
