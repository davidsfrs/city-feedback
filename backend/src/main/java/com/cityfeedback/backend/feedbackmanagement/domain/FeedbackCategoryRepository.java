package com.cityfeedback.backend.feedbackmanagement.domain;

import java.util.Optional;
import java.util.UUID;
import java.util.List;

public interface FeedbackCategoryRepository {

    void save(FeedbackCategory category);

    Optional<FeedbackCategory> findById(UUID id);

    List<FeedbackCategory> findAll();
}

