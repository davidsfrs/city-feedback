package com.cityfeedback.backend.feedbackmanagement.domain;

import lombok.Getter;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
public class Feedback {


        private final UUID id;
        private String titel;
        private String beschreibung;
        private final LocalDateTime erstelltAm;
        private LocalDateTime aktualisiertAm;
        private FeedbackStatus status;
        private final UUID citizenId;

        public Feedback(String titel, String beschreibung, UUID citizenId) {

                if (titel == null || titel.isBlank()) {
                        throw new IllegalArgumentException(
                                "Titel must not be null or blank"
                        );
                }

                if (beschreibung == null || beschreibung.isBlank()) {
                        throw new IllegalArgumentException(
                                "Beschreibung must not be null or blank"
                        );
                }

                if (citizenId == null) {
                        throw new IllegalArgumentException(
                                "Citizen ID must not be null"
                        );
                }


                this.id = UUID.randomUUID();
                this.titel = titel;
                this.beschreibung = beschreibung;
                this.citizenId = citizenId;
                this.erstelltAm = LocalDateTime.now();
                this.aktualisiertAm = this.erstelltAm;
                this.status = FeedbackStatus.SUBMITTED;
        }

        public void changeStatus(FeedbackStatus newStatus) {

                if (!status.canTransitionTo(newStatus)) {
                        throw new IllegalStateException(
                                "Invalid status transition from "
                                        + status + " to " + newStatus
                        );
                }

                this.status = newStatus;
                this.aktualisiertAm = LocalDateTime.now();
        }
}