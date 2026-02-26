package org.example.dictionary.fourletterdictionary;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface FourLetterDictionaryRepository extends JpaRepository<FourLetterDictionary, UUID> {
    Optional<FourLetterDictionary> findById(UUID id);
    Optional<FourLetterDictionary> findByWord(String word);
    boolean existsByWord(String word);
}