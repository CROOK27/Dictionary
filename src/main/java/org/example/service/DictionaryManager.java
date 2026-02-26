package org.example.service;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.dictionary.DictionaryValidator;
import org.example.dictionary.fivedigitdictionary.FiveDigitDictionaryService;
import org.example.dictionary.fourletterdictionary.FourLetterDictionaryService;
import org.example.translation.TranslationService;

@Data
@NoArgsConstructor
public class DictionaryManager {
    private FourLetterDictionaryService fourLetterDictionaryService;
    private FiveDigitDictionaryService fiveDigitDictionaryService;
    private TranslationService translationService;
    private DictionaryValidator dictionaryValidator;

    public void init() {
        System.out.println("DictionaryManager initialized");
    }

    public void destroy() {
        System.out.println("DictionaryManager destroyed");
    }
}