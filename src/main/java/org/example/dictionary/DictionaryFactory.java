package org.example.dictionary;

public class DictionaryFactory {

    public static Dictionary createDictionary(int type) {
        return switch (type) {
            case 1 -> new FourLetterDictionary();
            case 2 -> new FiveDigitDictionary();
            default -> throw new IllegalArgumentException("Неизвестный тип словаря: " + type);
        };
    }
}