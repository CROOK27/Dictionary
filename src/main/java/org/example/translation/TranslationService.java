package org.example.translation;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.dictionary.fivedigitdictionary.FiveDigitDictionary;
import org.example.dictionary.fivedigitdictionary.FiveDigitDictionaryRepository;
import org.example.dictionary.fourletterdictionary.FourLetterDictionary;
import org.example.dictionary.fourletterdictionary.FourLetterDictionaryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class TranslationService {

    private final TranslationRepository translationRepository;
    private final FiveDigitDictionaryRepository fiveDigitDictionaryRepository;
    private final FourLetterDictionaryRepository fourLetterDictionaryRepository;

    public List<Translation> getAllTranslations() {
        return translationRepository.findAll();
    }

    public Optional<Translation> getTranslationById(UUID id) {
        return translationRepository.findById(id);
    }

    public List<Translation> getTranslationsByFiveDigitDictionary(UUID fiveDigitDictionaryId) {
        return translationRepository.findByFiveDigitDictionaryId(fiveDigitDictionaryId);
    }

    public List<Translation> getTranslationsByFourLetterDictionary(UUID fourLetterDictionaryId) {
        return translationRepository.findByFourLetterDictionaryId(fourLetterDictionaryId);
    }

    public Optional<Translation> getTranslationByDictionaryPair(UUID fiveDigitDictId, UUID fourLetterDictId) {
        return translationRepository.findByDictionaryPair(fiveDigitDictId, fourLetterDictId);
    }

    public Translation createTranslation(TranslationRequest request) {
        validateTranslationRequest(request);

        FiveDigitDictionary fiveDigitDict = getFiveDigitDictionaryById(request.getFiveDigitDictionaryId());
        FourLetterDictionary fourLetterDict = getFourLetterDictionaryById(request.getFourLetterDictionaryId());

        if (translationRepository.existsByFiveDigitDictionaryIdAndFourLetterDictionaryId(
                request.getFiveDigitDictionaryId(),
                request.getFourLetterDictionaryId())) {
            throw new IllegalStateException("Translation already exists for this dictionary pair");
        }

        Translation translation = Translation.builder()
                .translatedWord(request.getTranslatedWord())
                .fiveDigitDictionary(fiveDigitDict)
                .fourLetterDictionary(fourLetterDict)
                .build();

        return translationRepository.save(translation);
    }

    public Translation updateTranslation(UUID id, TranslationRequest request) {
        Translation existingTranslation = translationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Translation not found with id: " + id));

        validateTranslationRequest(request);

        FiveDigitDictionary fiveDigitDict = getFiveDigitDictionaryById(request.getFiveDigitDictionaryId());
        FourLetterDictionary fourLetterDict = getFourLetterDictionaryById(request.getFourLetterDictionaryId());

        existingTranslation.setTranslatedWord(request.getTranslatedWord());
        existingTranslation.setFiveDigitDictionary(fiveDigitDict);
        existingTranslation.setFourLetterDictionary(fourLetterDict);

        return translationRepository.save(existingTranslation);
    }

    public void deleteTranslation(UUID id) {
        if (!translationRepository.existsById(id)) {
            throw new EntityNotFoundException("Translation not found with id: " + id);
        }
        translationRepository.deleteById(id);
    }

    private void validateTranslationRequest(TranslationRequest request) {
        if (request.getTranslatedWord() == null || request.getTranslatedWord().trim().isEmpty()) {
            throw new IllegalArgumentException("Translated word cannot be empty");
        }
        if (request.getFiveDigitDictionaryId() == null) {
            throw new IllegalArgumentException("Five Digit Dictionary ID cannot be null");
        }
        if (request.getFourLetterDictionaryId() == null) {
            throw new IllegalArgumentException("Four Letter Dictionary ID cannot be null");
        }
    }

    private FiveDigitDictionary getFiveDigitDictionaryById(UUID id) {
        return fiveDigitDictionaryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Five Digit Dictionary not found with id: " + id));
    }

    private FourLetterDictionary getFourLetterDictionaryById(UUID id) {
        return fourLetterDictionaryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Four Letter Dictionary not found with id: " + id));
    }
}