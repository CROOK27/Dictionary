package org.example.dictionary.fourletterdictionary;

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
public class FourLetterDictionaryService {

    private final FourLetterDictionaryRepository fourLetterDictionaryRepository;

    public List<FourLetterDictionary> getAll() {
        return fourLetterDictionaryRepository.findAll();
    }

    public Optional<FourLetterDictionary> getFourLetterDictionaryId(UUID id) {
        return fourLetterDictionaryRepository.findById(id);
    }

    public Optional<FourLetterDictionary> getByWord(String word) {
        return fourLetterDictionaryRepository.findByWord(word);
    }

    public FourLetterDictionary create(FourLetterDictionaryRequest request) {
        validateRequest(request);

        if (fourLetterDictionaryRepository.existsByWord(request.getWord())) {
            throw new IllegalStateException("Word already exists: " + request.getWord());
        }

        FourLetterDictionary dictionary = FourLetterDictionary.builder()
                .word(request.getWord())
                .build();

        return fourLetterDictionaryRepository.save(dictionary);
    }

    public FourLetterDictionary update(UUID id, FourLetterDictionaryRequest request) {
        FourLetterDictionary existing = fourLetterDictionaryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Four Letter Dictionary not found with id: " + id));

        validateRequest(request);

        existing.setWord(request.getWord());

        return fourLetterDictionaryRepository.save(existing);
    }

    public void delete(UUID id) {
        if (!fourLetterDictionaryRepository.existsById(id)) {
            throw new EntityNotFoundException("Four Letter Dictionary not found with id: " + id);
        }
        fourLetterDictionaryRepository.deleteById(id);
    }

    private void validateRequest(FourLetterDictionaryRequest request) {
        if (request.getWord() == null || request.getWord().trim().isEmpty()) {
            throw new IllegalArgumentException("Word cannot be empty");
        }
    }
}