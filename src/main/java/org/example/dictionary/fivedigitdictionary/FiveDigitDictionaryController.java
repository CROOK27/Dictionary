package org.example.dictionary.fivedigitdictionary;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/five-digit-dictionary")
@RequiredArgsConstructor
public class FiveDigitDictionaryController {

    private final FiveDigitDictionaryService fiveDigitDictionaryService;

    @GetMapping
    public ResponseEntity<List<FiveDigitDictionary>> getAll() {
        return ResponseEntity.ok(fiveDigitDictionaryService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<FiveDigitDictionary> getById(@PathVariable UUID id) {
        return fiveDigitDictionaryService.getFiveDigitDictionaryId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/word/{word}")
    public ResponseEntity<FiveDigitDictionary> getByWord(@PathVariable String word) {
        return fiveDigitDictionaryService.getByWord(word)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<FiveDigitDictionary> create(@RequestBody FiveDigitDictionaryRequest request) {
        return new ResponseEntity<>(fiveDigitDictionaryService.create(request), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<FiveDigitDictionary> update(@PathVariable UUID id, @RequestBody FiveDigitDictionaryRequest request) {
        return ResponseEntity.ok(fiveDigitDictionaryService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        fiveDigitDictionaryService.delete(id);
        return ResponseEntity.noContent().build();
    }
}