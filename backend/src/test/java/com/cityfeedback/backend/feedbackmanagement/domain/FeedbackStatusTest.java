package com.cityfeedback.backend.feedbackmanagement.domain;

import org.junit.jupiter.api.Test;

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
        Feedback feedback = Feedback.createFeedback(
                "Defekte Straßenlaterne",
                "Die Straßenlaterne funktioniert nicht."
        );

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
/*
    @Test
    void shouldRejectTransitionFromSubmittedToAccepted() {
    }

    @Test
    void shouldRejectTransitionFromSubmittedToRejected() {
    }

    @Test
    void shouldRejectTransitionFromSubmittedToInProgress() {
    }

    @Test
    void shouldRejectTransitionFromSubmittedToResolved() {
    }

    @Test
    void shouldRejectTransitionFromUnderReviewToSubmitted() {
    }

    @Test
    void shouldRejectTransitionFromUnderReviewToInProgress() {
    }

    @Test
    void shouldRejectTransitionFromUnderReviewToResolved() {
    }

    @Test
    void shouldRejectTransitionFromAcceptedToSubmitted() {
    }

    @Test
    void shouldRejectTransitionFromAcceptedToUnderReview() {
    }

    @Test
    void shouldRejectTransitionFromAcceptedToRejected() {
    }

    @Test
    void shouldRejectTransitionFromAcceptedToResolved() {
    }

    @Test
    void shouldRejectTransitionFromRejectedToSubmitted() {
    }

    @Test
    void shouldRejectTransitionFromRejectedToUnderReview() {
    }

    @Test
    void shouldRejectTransitionFromRejectedToAccepted() {
    }

    @Test
    void shouldRejectTransitionFromRejectedToInProgress() {
    }

    @Test
    void shouldRejectTransitionFromRejectedToResolved() {
    }

    @Test
    void shouldRejectTransitionFromInProgressToSubmitted() {
    }

    @Test
    void shouldRejectTransitionFromInProgressToUnderReview() {
    }

    @Test
    void shouldRejectTransitionFromInProgressToAccepted() {
    }

    @Test
    void shouldRejectTransitionFromInProgressToRejected() {
    }

    @Test
    void shouldRejectTransitionFromResolvedToSubmitted() {
    }

    @Test
    void shouldRejectTransitionFromResolvedToUnderReview() {
    }

    @Test
    void shouldRejectTransitionFromResolvedToAccepted() {
    }

    @Test
    void shouldRejectTransitionFromResolvedToRejected() {
    }

    @Test
    void shouldRejectTransitionFromResolvedToInProgress() {
    }

    @Test
    void shouldRejectTransitionToNullStatus() {
    }

    @Test
    void shouldRejectTransitionToSameStatus() {
    }

    @Test
    void shouldUpdateModificationTimestampOnStatusChange() {
    }

    @Test
    void shouldNotUpdateModificationTimestampOnInvalidStatusChange() {
    }

    @Test
    void shouldPreserveCreationTimestampOnStatusChange() {
    }

    @Test
    void shouldPreserveCurrentStatusOnInvalidTransition() {
    }
*/
}
