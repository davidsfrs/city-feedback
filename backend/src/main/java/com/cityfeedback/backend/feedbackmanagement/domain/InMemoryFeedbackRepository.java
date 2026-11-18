package com.cityfeedback.backend.feedbackmanagement.domain;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class InMemoryFeedbackRepository implements FeedbackRepository {

    private final Map<UUID, Feedback> feedbacks = new HashMap<>();

    @Override
    public void save(Feedback feedback) {
        feedbacks.put(feedback.getId(), feedback);
    }

    public int getStoredFeedbackCount() {
        return feedbacks.size();
    }
}
