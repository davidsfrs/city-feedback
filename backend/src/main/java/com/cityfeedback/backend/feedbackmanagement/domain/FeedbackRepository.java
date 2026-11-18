package com.cityfeedback.backend.feedbackmanagement.domain;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface FeedbackRepository {

    void save(Feedback feedback);

    Optional<Feedback> findById(UUID id);

    List<Feedback> findAll();
}