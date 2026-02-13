package org.example.translation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface TranslationReposytory extends JpaRepository<Translation, UUID> {
    Optional<Translation> findById(UUID id);
    // TO DO FUNCTIONS
}
