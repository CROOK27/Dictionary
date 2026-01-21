package org.example.dictionary;

public class DictionaryValidator {

    public static boolean validateFourLetterKey(String key) {
        return key != null && key.length() == 4 && key.matches("[a-zA-Z]{4}");
    }

    public static boolean validateFiveDigitKey(String key) {
        return key != null && key.length() == 5 && key.matches("\\d{5}");
    }

    public static String getValidationRules(int dictionaryType) {
        return switch (dictionaryType) {
            case 1 -> "Ключ должен содержать 4 латинские буквы (например: 'word', 'test')";
            case 2 -> "Ключ должен содержать 5 цифр (например: '12345', '98765')";
            default -> "Неизвестный тип словаря";
        };
    }
}
