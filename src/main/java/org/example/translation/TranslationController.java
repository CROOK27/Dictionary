package org.example.translation;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/translations")
@RequiredArgsConstructor
public class TranslationController {

    private final TranslationService translationService;

    @GetMapping
    public ResponseEntity<List<Translation>> getAllTranslations() {
        return ResponseEntity.ok(translationService.getAllTranslations());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Translation> getTranslationById(@PathVariable UUID id) {
        return translationService.getTranslationById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/five-digit/{dictionaryId}")
    public ResponseEntity<List<Translation>> getTranslationsByFiveDigitDictionary(@PathVariable UUID dictionaryId) {
        return ResponseEntity.ok(translationService.getTranslationsByFiveDigitDictionary(dictionaryId));
    }

    @GetMapping("/four-letter/{dictionaryId}")
    public ResponseEntity<List<Translation>> getTranslationsByFourLetterDictionary(@PathVariable UUID dictionaryId) {
        return ResponseEntity.ok(translationService.getTranslationsByFourLetterDictionary(dictionaryId));
    }

    @GetMapping("/pair")
    public ResponseEntity<Translation> getTranslationByPair(
            @RequestParam UUID fiveDigitDictId,
            @RequestParam UUID fourLetterDictId) {
        return translationService.getTranslationByDictionaryPair(fiveDigitDictId, fourLetterDictId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Translation> createTranslation(@RequestBody TranslationRequest request) {
        return new ResponseEntity<>(translationService.createTranslation(request), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Translation> updateTranslation(@PathVariable UUID id, @RequestBody TranslationRequest request) {
        return ResponseEntity.ok(translationService.updateTranslation(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTranslation(@PathVariable UUID id) {
        translationService.deleteTranslation(id);
        return ResponseEntity.noContent().build();
    }
}