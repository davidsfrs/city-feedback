package com.cityfeedback.backend.feedbackmanagement.domain;

public enum FeedbackStatus {
    SUBMITTED,
    UNDER_REVIEW,
    ACCEPTED,
    REJECTED,
    IN_PROGRESS,
    RESOLVED;

    public boolean canTransitionTo(FeedbackStatus newStatus) {

        return switch (this) {
            case SUBMITTED ->
                    newStatus == UNDER_REVIEW;

            case UNDER_REVIEW ->
                    newStatus == ACCEPTED ||
                            newStatus == REJECTED;

            case ACCEPTED ->
                    newStatus == IN_PROGRESS;

            case IN_PROGRESS ->
                    newStatus == RESOLVED;

            case REJECTED, RESOLVED ->
                    false;
        };
    }
}
