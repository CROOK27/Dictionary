package org.example.translation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TranslationRepository extends JpaRepository<Translation, UUID> {
    Optional<Translation> findById(UUID id);

    List<Translation> findByFiveDigitDictionaryId(UUID fiveDigitDictionaryId);

    List<Translation> findByFourLetterDictionaryId(UUID fourLetterDictionaryId);

    @Query("SELECT t FROM Translation t WHERE t.fiveDigitDictionary.id = :fiveDigitId AND t.fourLetterDictionary.id = :fourLetterId")
    Optional<Translation> findByDictionaryPair(
            @Param("fiveDigitId") UUID fiveDigitDictionaryId,
            @Param("fourLetterId") UUID fourLetterDictionaryId);

    boolean existsByFiveDigitDictionaryIdAndFourLetterDictionaryId(
            UUID fiveDigitDictionaryId,
            UUID fourLetterDictionaryId);
}