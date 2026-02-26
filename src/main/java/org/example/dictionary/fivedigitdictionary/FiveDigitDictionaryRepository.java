package org.example.dictionary.fivedigitdictionary;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface FiveDigitDictionaryRepository extends JpaRepository<FiveDigitDictionary, UUID> {
    Optional<FiveDigitDictionary> findById(UUID id);
    Optional<FiveDigitDictionary> findByWord(String word);
    boolean existsByWord(String word);
}