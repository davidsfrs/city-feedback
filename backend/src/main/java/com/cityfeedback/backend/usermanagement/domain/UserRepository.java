package com.cityfeedback.backend.usermanagement.domain;

import java.util.Optional;
import java.util.UUID;
import java.util.List;

public interface UserRepository {

    void save(User user);

    Optional<User> findById(UUID id);

    Optional<User> findByEmail(EmailAddress email);

    boolean existsByEmail(EmailAddress email);

    List<User> findAll();
}
