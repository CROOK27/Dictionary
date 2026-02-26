package org.example.dictionary;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@NoArgsConstructor
@Component
public class DictionaryValidator {
    private int minWordLength = 1;
    private int maxWordLength = 50;

    public boolean validateWord(String word) {
        return word != null &&
                word.length() >= minWordLength &&
                word.length() <= maxWordLength;
    }

    public boolean validateDictionaryPair(Object dict1, Object dict2) {
        return dict1 != null && dict2 != null;
    }
}