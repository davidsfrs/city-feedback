package com.cityfeedback.backend.feedbackmanagement.domain;

import java.util.Optional;
import java.util.UUID;

public interface FeedbackCategoryRepository {

    void save(FeedbackCategory category);

    Optional<FeedbackCategory> findById(UUID id);
}

