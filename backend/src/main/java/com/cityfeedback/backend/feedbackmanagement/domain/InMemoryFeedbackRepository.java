package com.cityfeedback.backend.feedbackmanagement.domain;

import java.util.*;

public class InMemoryFeedbackRepository implements FeedbackRepository {

    private final Map<UUID, Feedback> feedbacks = new HashMap<>();

    @Override
    public void save(Feedback feedback) {
        feedbacks.put(feedback.getId(), feedback);
    }

    @Override
    public Optional<Feedback> findById(UUID id) {
        return Optional.ofNullable(feedbacks.get(id));
    }

    @Override
    public List<Feedback> findAll() {
        return new ArrayList<>(feedbacks.values());
    }

    @Override
    public List<Feedback> findByCitizenId(UUID citizenId) {
        return feedbacks.values().stream()
                .filter(feedback -> feedback.getCitizenId().equals(citizenId))
                .toList();
    }
}
