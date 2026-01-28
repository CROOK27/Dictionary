package org.example.dictionary;

public class FiveDigitDictionary extends LanguageDictionary {

    @Override
    protected boolean isValidKey(String key) {
        if (key == null || key.length() != 5) {
            return false;
        }
        return key.matches("\\d{5}");
    }

    @Override
    public String getDictionaryType() {
        return "Five-digit dictionary";
    }
}