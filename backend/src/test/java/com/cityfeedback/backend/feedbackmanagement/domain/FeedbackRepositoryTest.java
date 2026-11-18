package com.cityfeedback.backend.feedbackmanagement.domain;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class FeedbackRepositoryTest {

    @Test
    void shouldSaveFeedback() {
        InMemoryFeedbackRepository repository = new InMemoryFeedbackRepository();
        Feedback feedback = new Feedback("Titel", "Beschreibung");

        repository.save(feedback);

        assertEquals(1, repository.findAll().size());
    }

    @Test
    void shouldSaveMultipleFeedbacks() {
        InMemoryFeedbackRepository repository = new InMemoryFeedbackRepository();

        Feedback feedback1 = new Feedback("Titel 1", "Beschreibung 1");
        Feedback feedback2 = new Feedback("Titel 2", "Beschreibung 2");

        repository.save(feedback1);
        repository.save(feedback2);

        assertEquals(2, repository.findAll().size());
    }

    @Test
    void shouldNotCreateDuplicateWhenSavingSameFeedbackTwice() {
        InMemoryFeedbackRepository repository = new InMemoryFeedbackRepository();
        Feedback feedback = new Feedback("Titel", "Beschreibung");

        repository.save(feedback);
        repository.save(feedback);

        assertEquals(1, repository.findAll().size());
    }

    @Test
    void shouldFindFeedbackById() {
        InMemoryFeedbackRepository repository = new InMemoryFeedbackRepository();
        Feedback feedback = new Feedback("Titel", "Beschreibung");

        repository.save(feedback);

        Optional<Feedback> result = repository.findById(feedback.getId());

        assertTrue(result.isPresent());
        assertSame(feedback, result.get());
    }

    @Test
    void shouldReturnEmptyWhenFeedbackIdDoesNotExist() {
        InMemoryFeedbackRepository repository = new InMemoryFeedbackRepository();

        Optional<Feedback> result = repository.findById(UUID.randomUUID());

        assertTrue(result.isEmpty());
    }



    @Test
    void shouldFindAllSavedFeedbacks() {
        InMemoryFeedbackRepository repository = new InMemoryFeedbackRepository();

        Feedback feedback1 = new Feedback("Titel 1", "Beschreibung 1");
        Feedback feedback2 = new Feedback("Titel 2", "Beschreibung 2");

        repository.save(feedback1);
        repository.save(feedback2);

        List<Feedback> result = repository.findAll();

        assertEquals(2, result.size());
        assertTrue(result.contains(feedback1));
        assertTrue(result.contains(feedback2));
    }

    @Test
    void shouldReturnEmptyCollectionWhenNoFeedbacksExist() {
        InMemoryFeedbackRepository repository = new InMemoryFeedbackRepository();

        List<Feedback> result = repository.findAll();

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

}
