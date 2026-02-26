package org.example.dictionary.fivedigitdictionary;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class FiveDigitDictionaryService {

    private final FiveDigitDictionaryRepository fiveDigitDictionaryRepository;

    public List<FiveDigitDictionary> getAll() {
        return fiveDigitDictionaryRepository.findAll();
    }

    public Optional<FiveDigitDictionary> getFiveDigitDictionaryId(UUID id) {
        return fiveDigitDictionaryRepository.findById(id);
    }

    public Optional<FiveDigitDictionary> getByWord(String word) {
        return fiveDigitDictionaryRepository.findByWord(word);
    }

    public FiveDigitDictionary create(FiveDigitDictionaryRequest request) {
        validateRequest(request);

        if (fiveDigitDictionaryRepository.existsByWord(request.getWord())) {
            throw new IllegalStateException("Word already exists: " + request.getWord());
        }

        FiveDigitDictionary dictionary = FiveDigitDictionary.builder()
                .word(request.getWord())
                .build();

        return fiveDigitDictionaryRepository.save(dictionary);
    }

    public FiveDigitDictionary update(UUID id, FiveDigitDictionaryRequest request) {
        FiveDigitDictionary existing = fiveDigitDictionaryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Five Digit Dictionary not found with id: " + id));

        validateRequest(request);

        existing.setWord(request.getWord());

        return fiveDigitDictionaryRepository.save(existing);
    }

    public void delete(UUID id) {
        if (!fiveDigitDictionaryRepository.existsById(id)) {
            throw new EntityNotFoundException("Five Digit Dictionary not found with id: " + id);
        }
        fiveDigitDictionaryRepository.deleteById(id);
    }

    private void validateRequest(FiveDigitDictionaryRequest request) {
        if (request.getWord() == null || request.getWord().trim().isEmpty()) {
            throw new IllegalArgumentException("Word cannot be empty");
        }
    }
}