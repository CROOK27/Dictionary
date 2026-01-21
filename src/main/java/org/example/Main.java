package org.example;

import org.example.dictionary.Dictionary;
import org.example.dictionary.DictionaryFactory;
import org.example.dictionary.DictionaryValidator;
import org.example.dictionary.FourLetterDictionary;
import org.example.file.FileDictionaryStorage;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Scanner;

public class Main {
    private static Dictionary fourLetterDict;
    private static Dictionary fiveDigitDict;
    private static Scanner scanner;

    private static final String FOUR_LETTER_FILE = "dictionaries/four_letter_dict.txt";
    private static final String FIVE_DIGIT_FILE = "dictionaries/five_digit_dict.txt";

    public static void main(String[] args) {
        scanner = new Scanner(System.in);

        fourLetterDict = DictionaryFactory.createDictionary(1);
        fiveDigitDict = DictionaryFactory.createDictionary(2);

        loadDictionary(fourLetterDict, FOUR_LETTER_FILE, "Четырехбуквенный");
        loadDictionary(fiveDigitDict, FIVE_DIGIT_FILE, "Пятицифровой");

        boolean running = true;

        while (running) {
            printMenu();
            int choice = getIntInput("Выберите действие: ");

            switch (choice) {
                case 1 -> viewAllDictionaries();
                case 2 -> workWithDictionary();
                case 3 -> {
                    saveAllDictionaries();
                    running = false;
                }
                default -> System.out.println("Неверный выбор. Попробуйте снова.");
            }
        }

        scanner.close();
        System.out.println("Программа завершена.");
    }

    private static void loadDictionary(Dictionary dictionary, String filePath, String dictName) {
        System.out.println("\n=== Загрузка " + dictName + " словаря ===");

        Path path = Paths.get(filePath);
        if (Files.exists(path)) {
            FileDictionaryStorage.loadDictionaryFromFile(dictionary, filePath);
        } else {
            System.out.println("Файл " + filePath + " не найден.");
            System.out.println("Будет создан новый файл при сохранении.");
        }
    }

    private static void printMenu() {
        System.out.println("\n=== МЕНЮ СЛОВАРЕЙ ===");
        System.out.println("1. Просмотреть все словари");
        System.out.println("2. Работа с конкретным словарем");
        System.out.println("3. Выйти и сохранить");
        System.out.println("=====================");
    }

    private static void viewAllDictionaries() {
        System.out.println("\n=== СЛОВАРЬ 1 (4 латинские буквы) ===");
        printDictionary(fourLetterDict);

        System.out.println("\n=== СЛОВАРЬ 2 (5 цифр) ===");
        printDictionary(fiveDigitDict);
    }

    private static void printDictionary(Dictionary dictionary) {
        Map<String, String> entries = dictionary.getAllEntries();
        if (entries.isEmpty()) {
            System.out.println("Словарь пуст.");
        } else {
            entries.forEach((key, value) ->
                    System.out.println(key + " - " + value));
        }
    }

    private static void workWithDictionary() {
        System.out.println("\nВыберите словарь:");
        System.out.println("1. Словарь с 4-буквенными ключами (латиница)");
        System.out.println("2. Словарь с 5-цифровыми ключами");

        int dictChoice = getIntInput("Ваш выбор: ");

        if (dictChoice == 1) {
            dictionaryOperations(fourLetterDict, FOUR_LETTER_FILE);
        } else if (dictChoice == 2) {
            dictionaryOperations(fiveDigitDict, FIVE_DIGIT_FILE);
        } else {
            System.out.println("Неверный выбор.");
        }
    }

    private static void dictionaryOperations(Dictionary dictionary, String filePath) {
        boolean inDictionaryMenu = true;

        while (inDictionaryMenu) {
            System.out.println("\n=== ОПЕРАЦИИ СО СЛОВАРЕМ ===");
            System.out.println("1. Просмотреть словарь");
            System.out.println("2. Добавить запись");
            System.out.println("3. Удалить запись по ключу");
            System.out.println("4. Найти перевод по ключу");
            System.out.println("5. Вернуться в главное меню");

            int operation = getIntInput("Выберите операцию: ");

            switch (operation) {
                case 1 -> printDictionary(dictionary);
                case 2 -> addEntry(dictionary);
                case 3 -> removeEntry(dictionary);
                case 4 -> searchEntry(dictionary);
                case 5 -> {
                    inDictionaryMenu = false;
                    // Сохраняем при выходе
                    FileDictionaryStorage.saveDictionaryToFile(dictionary, filePath);
                }
                default -> System.out.println("Неверный выбор.");
            }
        }
    }

    private static void addEntry(Dictionary dictionary) {
        System.out.println(DictionaryValidator.getValidationRules(
                dictionary instanceof FourLetterDictionary ? 1 : 2));

        scanner.nextLine();

        System.out.print("Введите ключ: ");
        String key = scanner.nextLine().trim();

        System.out.print("Введите перевод на русский: ");
        String value = scanner.nextLine().trim();

        if (dictionary.addEntry(key, value)) {
            System.out.println("Запись успешно добавлена.");
        } else {
            System.out.println("Ошибка: неверный формат ключа или ключ уже существует.");
        }
    }

    private static void removeEntry(Dictionary dictionary) {
        scanner.nextLine();

        System.out.print("Введите ключ для удаления: ");
        String key = scanner.nextLine().trim();

        if (dictionary.removeEntry(key)) {
            System.out.println("Запись успешно удалена.");
        } else {
            System.out.println("Запись с таким ключом не найдена.");
        }
    }

    private static void searchEntry(Dictionary dictionary) {
        scanner.nextLine();

        System.out.print("Введите ключ для поиска: ");
        String key = scanner.nextLine().trim();

        String result = dictionary.searchByKey(key);

        if (result != null) {
            System.out.println("Перевод: " + result);
        } else {
            System.out.println("Запись с таким ключом не найдена.");
        }
    }

    private static void saveAllDictionaries() {
        FileDictionaryStorage.saveDictionaryToFile(fourLetterDict, FOUR_LETTER_FILE);
        FileDictionaryStorage.saveDictionaryToFile(fiveDigitDict, FIVE_DIGIT_FILE);
        System.out.println("Все словари сохранены.");
    }

    private static int getIntInput(String prompt) {
        System.out.print(prompt);
        while (!scanner.hasNextInt()) {
            System.out.println("Пожалуйста, введите число.");
            scanner.next();
            System.out.print(prompt);
        }
        int result = scanner.nextInt();
        return result;
    }
}