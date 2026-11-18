package com.cityfeedback.backend.feedbackmanagement.domain;

import lombok.Getter;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
public class Feedback {

        private final UUID id;
        private String titel;
        private String beschreibung;
        private LocalDateTime erstelltAm;
        private LocalDateTime aktualisiertAm;
        private String status;

        public Feedback(String titel, String beschreibung) {
                this.id = UUID.randomUUID();
                this.titel = titel;
                this.beschreibung = beschreibung;
                this.erstelltAm = LocalDateTime.now();
                this.aktualisiertAm = this.erstelltAm;
                this.status = "SUBMITTED";
        }

        public static Feedback createFeedback(String titel, String beschreibung) {
                if (titel == null || titel.isBlank()) {
                        throw new IllegalArgumentException(
                                "Titel darf nicht leer sein"
                        );
                }
                if (beschreibung == null || beschreibung.isBlank()) {
                        throw new IllegalArgumentException(
                                "Beschreibung darf nicht leer sein"
                        );
                }
                return new Feedback(titel, beschreibung);
        }
}