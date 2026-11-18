package com.cityfeedback.backend.feedbackmanagement.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FeedbackRepositoryTest {

    @Test
    void shouldSaveFeedback() {
        InMemoryFeedbackRepository repository = new InMemoryFeedbackRepository();
        Feedback feedback = new Feedback("Titel", "Beschreibung");

        repository.save(feedback);

        assertEquals(1, repository.getStoredFeedbackCount());
    }

    @Test
    void shouldSaveMultipleFeedbacks() {
        InMemoryFeedbackRepository repository = new InMemoryFeedbackRepository();

        Feedback feedback1 = new Feedback("Titel 1", "Beschreibung 1");
        Feedback feedback2 = new Feedback("Titel 2", "Beschreibung 2");

        repository.save(feedback1);
        repository.save(feedback2);

        assertEquals(2, repository.getStoredFeedbackCount());
    }

    @Test
    void shouldNotCreateDuplicateWhenSavingSameFeedbackTwice() {
        InMemoryFeedbackRepository repository = new InMemoryFeedbackRepository();
        Feedback feedback = new Feedback("Titel", "Beschreibung");

        repository.save(feedback);
        repository.save(feedback);

        assertEquals(1, repository.getStoredFeedbackCount());
    }

}
