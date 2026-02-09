package org.example.dictionary;

public class FourLetterDictionary extends LanguageDictionary {
    @Override
    protected boolean isValidKey(String key) {
        if (key == null || key.length() != 4) {
            return false;
        }
        return key.matches("[a-zA-Z]{4}");
    }

    @Override
    public String getDictionaryType() {
        return "Four-letter Latin dictionary";
    }
}