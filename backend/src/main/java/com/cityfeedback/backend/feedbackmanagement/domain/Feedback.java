package com.cityfeedback.backend.feedbackmanagement.domain;

import lombok.Getter;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
public class Feedback {


        private final UUID id;
        private String title;
        private String description;
        private final LocalDateTime createdAt;
        private LocalDateTime updatedAt;
        private FeedbackStatus status;
        private final UUID citizenId;
        private final UUID categoryId;

        public Feedback(String title, String description, UUID citizenId, UUID categoryId) {

                if (title == null || title.isBlank()) {
                        throw new IllegalArgumentException(
                                "Titel must not be null or blank"
                        );
                }

                if (description == null || description.isBlank()) {
                        throw new IllegalArgumentException(
                                "Beschreibung must not be null or blank"
                        );
                }

                if (citizenId == null) {
                        throw new IllegalArgumentException(
                                "Citizen ID must not be null"
                        );
                }

                if (categoryId == null) {
                        throw new IllegalArgumentException("Category ID must not be null");
                }

                this.id = UUID.randomUUID();
                this.title = title;
                this.description = description;
                this.citizenId = citizenId;
                this.categoryId = categoryId;
                this.createdAt = LocalDateTime.now();
                this.updatedAt = this.createdAt;
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
                this.updatedAt = LocalDateTime.now();
        }
}