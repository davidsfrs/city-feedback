package com.cityfeedback.backend.feedbackmanagement.infrastructure.persistence;

import com.cityfeedback.backend.feedbackmanagement.domain.Feedback;
import com.cityfeedback.backend.feedbackmanagement.domain.FeedbackStatus;
import com.cityfeedback.backend.feedbackmanagement.infrastructure.persistence.InMemoryFeedbackRepository;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class FeedbackRepositoryTest {

    @Test
    void shouldSaveFeedback() {
        InMemoryFeedbackRepository repository = new InMemoryFeedbackRepository();
        UUID citizenId = UUID.randomUUID();
        UUID categoryId = UUID.randomUUID();

        Feedback feedback = new Feedback(
                "Kaputte Straßenlaterne",
                "Das geht jetzt schon seit Monaten so. Weserstraße, Ecke Pannierstraße.",
                citizenId,
                categoryId
        );

        repository.save(feedback);

        assertEquals(1, repository.findAll().size());
    }

    @Test
    void shouldSaveMultipleFeedbacks() {
        InMemoryFeedbackRepository repository = new InMemoryFeedbackRepository();

        UUID citizenId1 = UUID.randomUUID();
        UUID citizenId2 = UUID.randomUUID();
        UUID categoryId1 = UUID.randomUUID();
        UUID categoryId2 = UUID.randomUUID();

        Feedback feedback1 = new Feedback("Titel 1", "Beschreibung 1", citizenId1, categoryId1);
        Feedback feedback2 = new Feedback("Titel 2", "Beschreibung 2", citizenId2, categoryId2);

        repository.save(feedback1);
        repository.save(feedback2);

        assertEquals(2, repository.findAll().size());
    }

    @Test
    void shouldNotCreateDuplicateWhenSavingSameFeedbackTwice() {
        InMemoryFeedbackRepository repository = new InMemoryFeedbackRepository();
        UUID citizenId = UUID.randomUUID();
        UUID categoryId = UUID.randomUUID();

        Feedback feedback = new Feedback(
                "Kaputte Straßenlaterne",
                "Das geht jetzt schon seit Monaten so. Weserstraße, Ecke Pannierstraße.",
                citizenId,
                categoryId
        );

        repository.save(feedback);
        repository.save(feedback);

        assertEquals(1, repository.findAll().size());
    }

    @Test
    void shouldFindFeedbackById() {
        InMemoryFeedbackRepository repository = new InMemoryFeedbackRepository();
        UUID citizenId = UUID.randomUUID();
        UUID categoryId = UUID.randomUUID();

        Feedback feedback = new Feedback(
                "Kaputte Straßenlaterne",
                "Das geht jetzt schon seit Monaten so. Weserstraße, Ecke Pannierstraße.",
                citizenId,
                categoryId
        );

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

        UUID citizenId1 = UUID.randomUUID();
        UUID citizenId2 = UUID.randomUUID();
        UUID categoryId1 = UUID.randomUUID();
        UUID categoryId2 = UUID.randomUUID();

        Feedback feedback1 = new Feedback("Titel 1", "Beschreibung 1", citizenId1, categoryId1);
        Feedback feedback2 = new Feedback("Titel 2", "Beschreibung 2", citizenId2, categoryId2);

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

    @Test
    void shouldUpdateExistingFeedback() {
        InMemoryFeedbackRepository repository = new InMemoryFeedbackRepository();
        UUID citizenId = UUID.randomUUID();
        UUID categoryId = UUID.randomUUID();

        Feedback feedback = new Feedback(
                "Kaputte Straßenlaterne",
                "Das geht jetzt schon seit Monaten so. Weserstraße, Ecke Pannierstraße.",
                citizenId,
                categoryId
        );

        repository.save(feedback);

        feedback.changeStatus(FeedbackStatus.UNDER_REVIEW);
        repository.save(feedback);

        Feedback updatedFeedback = repository
                .findById(feedback.getId())
                .orElseThrow();

        assertEquals(FeedbackStatus.UNDER_REVIEW, updatedFeedback.getStatus());
    }

    @Test
    void shouldNotCreateDuplicateWhenUpdatingFeedback() {
        InMemoryFeedbackRepository repository = new InMemoryFeedbackRepository();
        UUID citizenId = UUID.randomUUID();
        UUID categoryId = UUID.randomUUID();

        Feedback feedback = new Feedback(
                "Kaputte Straßenlaterne",
                "Das geht jetzt schon seit Monaten so. Weserstraße, Ecke Pannierstraße.",
                citizenId,
                categoryId
        );

        repository.save(feedback);

        feedback.changeStatus(FeedbackStatus.UNDER_REVIEW);
        repository.save(feedback);

        assertEquals(1, repository.findAll().size());
    }

    @Test
    void shouldPreserveFeedbackIdWhenUpdatingFeedback() {
        InMemoryFeedbackRepository repository = new InMemoryFeedbackRepository();
        UUID citizenId = UUID.randomUUID();
        UUID categoryId = UUID.randomUUID();

        Feedback feedback = new Feedback(
                "Kaputte Straßenlaterne",
                "Das geht jetzt schon seit Monaten so. Weserstraße, Ecke Pannierstraße.",
                citizenId,
                categoryId
        );
        UUID originalId = feedback.getId();

        repository.save(feedback);

        feedback.changeStatus(FeedbackStatus.UNDER_REVIEW);
        repository.save(feedback);

        Feedback updatedFeedback = repository
                .findById(originalId)
                .orElseThrow();

        assertEquals(originalId, updatedFeedback.getId());
    }

    @Test
    void shouldReplaceExistingFeedbackWithSameId() {
        InMemoryFeedbackRepository repository = new InMemoryFeedbackRepository();
        UUID citizenId = UUID.randomUUID();
        UUID categoryId = UUID.randomUUID();

        Feedback feedback = new Feedback(
                "Kaputte Straßenlaterne",
                "Das geht jetzt schon seit Monaten so. Weserstraße, Ecke Pannierstraße.",
                citizenId,
                categoryId
        );

        repository.save(feedback);

        feedback.changeStatus(FeedbackStatus.UNDER_REVIEW);
        repository.save(feedback);

        assertEquals(1, repository.findAll().size());
        assertEquals(
                FeedbackStatus.UNDER_REVIEW,
                repository.findById(feedback.getId())
                        .orElseThrow()
                        .getStatus()
        );
    }

    @Test
    void shouldFindFeedbacksByCitizenId() {
        InMemoryFeedbackRepository repository = new InMemoryFeedbackRepository();

        UUID citizenId = UUID.randomUUID();
        UUID categoryId = UUID.randomUUID();

        Feedback feedback = new Feedback(
                "Kaputte Straßenlaterne",
                "Das geht jetzt schon seit Monaten so. Weserstraße, Ecke Pannierstraße.",
                citizenId,
                categoryId
        );

        repository.save(feedback);

        List<Feedback> result = repository.findByCitizenId(citizenId);

        assertEquals(1, result.size());
        assertTrue(result.contains(feedback));
    }

    @Test
    void shouldReturnOnlyFeedbacksBelongingToSpecifiedCitizen() {
        InMemoryFeedbackRepository repository = new InMemoryFeedbackRepository();

        UUID citizenId1 = UUID.randomUUID();
        UUID citizenId2 = UUID.randomUUID();
        UUID categoryId1 = UUID.randomUUID();
        UUID categoryId2 = UUID.randomUUID();

        Feedback feedback1 = new Feedback("Titel 1", "Beschreibung 1", citizenId1, categoryId1);
        Feedback feedback2 = new Feedback("Titel 2", "Beschreibung 2", citizenId2, categoryId2);

        repository.save(feedback1);
        repository.save(feedback2);

        List<Feedback> result = repository.findByCitizenId(citizenId1);

        assertEquals(1, result.size());
        assertTrue(result.contains(feedback1));
        assertFalse(result.contains(feedback2));
    }

    @Test
    void shouldReturnEmptyCollectionWhenCitizenHasNoFeedbacks() {
        InMemoryFeedbackRepository repository = new InMemoryFeedbackRepository();

        UUID citizenId = UUID.randomUUID();

        List<Feedback> result = repository.findByCitizenId(citizenId);

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void shouldFindMultipleFeedbacksFromSameCitizen() {
        InMemoryFeedbackRepository repository = new InMemoryFeedbackRepository();

        UUID citizenId = UUID.randomUUID();
        UUID categoryId = UUID.randomUUID();

        Feedback feedback1 = new Feedback("Titel 1", "Beschreibung 1", citizenId, categoryId);
        Feedback feedback2 = new Feedback("Titel 2", "Beschreibung 2", citizenId, categoryId);

        repository.save(feedback1);
        repository.save(feedback2);

        List<Feedback> result = repository.findByCitizenId(citizenId);

        assertEquals(2, result.size());
        assertTrue(result.contains(feedback1));
        assertTrue(result.contains(feedback2));
    }

    @Test
    void shouldRejectNullFeedbackWhenSaving() {
        InMemoryFeedbackRepository repository = new InMemoryFeedbackRepository();

        assertThrows(
                IllegalArgumentException.class,
                () -> repository.save(null)
        );

        assertTrue(repository.findAll().isEmpty());
    }

}
