package org.example.dictionary.fourletterdictionary;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/four-letter-dictionary")
@RequiredArgsConstructor
public class FourLetterDictionaryController {

    private final FourLetterDictionaryService fourLetterDictionaryService;

    @GetMapping
    public ResponseEntity<List<FourLetterDictionary>> getAll() {
        return ResponseEntity.ok(fourLetterDictionaryService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<FourLetterDictionary> getById(@PathVariable UUID id) {
        return fourLetterDictionaryService.getFourLetterDictionaryId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/word/{word}")
    public ResponseEntity<FourLetterDictionary> getByWord(@PathVariable String word) {
        return fourLetterDictionaryService.getByWord(word)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<FourLetterDictionary> create(@RequestBody FourLetterDictionaryRequest request) {
        return new ResponseEntity<>(fourLetterDictionaryService.create(request), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<FourLetterDictionary> update(@PathVariable UUID id, @RequestBody FourLetterDictionaryRequest request) {
        return ResponseEntity.ok(fourLetterDictionaryService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        fourLetterDictionaryService.delete(id);
        return ResponseEntity.noContent().build();
    }
}