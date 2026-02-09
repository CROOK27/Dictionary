package org.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import java.util.Scanner;

public class DictionaryManager {
    @Autowired
    private DictionaryService fourLetterDictionaryService;

    @Autowired
    private DictionaryService fiveDigitDictionaryService;

    private Scanner scanner;

    private String fourLetterFilePath;
    private String fiveDigitFilePath;

    public void start() {
        scanner = new Scanner(System.in);

        System.out.println("=== ДИКТОРСЕРВИС ===");
        System.out.println("=============================\n");

        getFilePathsFromUser();

        System.out.println("\n=== ПУТИ К ФАЙЛАМ ===");
        System.out.println("Словарь 1 (4 буквы): " + fourLetterFilePath);
        System.out.println("Словарь 2 (5 цифр):  " + fiveDigitFilePath);
        System.out.println("=====================\n");

        fourLetterDictionaryService.setFilePath(fourLetterFilePath);
        fiveDigitDictionaryService.setFilePath(fiveDigitFilePath);

        fourLetterDictionaryService.loadDictionary();
        fiveDigitDictionaryService.loadDictionary();

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

    // НОВЫЙ МЕТОД: Получение путей от пользователя
    private void getFilePathsFromUser() {
        System.out.println("Введите пути к файлам словарей");
        System.out.println("=============================\n");

        System.out.println("Примеры путей:");
        System.out.println("1. C:\\Users\\Имя\\dictionaries\\four_letter_dict.txt");
        System.out.println("2. /home/user/dictionaries/four_letter_dict.txt");
        System.out.println("3. dictionaries/four_letter_dict.txt (относительный путь)");
        System.out.println();

        if (scanner.hasNextLine()) {
            scanner.nextLine();
        }

        while (true) {
            System.out.print("Введите путь к файлу для словаря с 4-буквенными ключами: ");
            fourLetterFilePath = scanner.nextLine().trim();

            if (!fourLetterFilePath.isEmpty()) {
                break;
            }
            System.out.println("Путь не может быть пустым. Попробуйте снова.");
        }

        while (true) {
            System.out.print("Введите путь к файлу для словаря с 5-цифровыми ключами: ");
            fiveDigitFilePath = scanner.nextLine().trim();

            if (!fiveDigitFilePath.isEmpty()) {
                break;
            }
            System.out.println("Путь не может быть пустым. Попробуйте снова.");
        }
    }

    private void printMenu() {
        System.out.println("\n=== МЕНЮ СЛОВАРЕЙ ===");
        System.out.println("1. Просмотреть все словари");
        System.out.println("2. Работа с конкретным словарем");
        System.out.println("3. Выйти и сохранить");
        System.out.println("=====================");
    }

    private void viewAllDictionaries() {
        System.out.println("\n=== СЛОВАРЬ 1 (4 латинские буквы) ===");
        System.out.println("Файл: " + fourLetterFilePath);
        fourLetterDictionaryService.printDictionary();

        System.out.println("\n=== СЛОВАРЬ 2 (5 цифр) ===");
        System.out.println("Файл: " + fiveDigitFilePath);
        fiveDigitDictionaryService.printDictionary();
    }

    private void workWithDictionary() {
        System.out.println("\nВыберите словарь:");
        System.out.println("1. Словарь с 4-буквенными ключами (латиница)");
        System.out.println("   Файл: " + fourLetterFilePath);
        System.out.println("2. Словарь с 5-цифровыми ключами");
        System.out.println("   Файл: " + fiveDigitFilePath);

        int choice = getIntInput("\nВаш выбор: ");

        if (choice == 1) {
            dictionaryOperations(fourLetterDictionaryService, fourLetterFilePath);
        } else if (choice == 2) {
            dictionaryOperations(fiveDigitDictionaryService, fiveDigitFilePath);
        } else {
            System.out.println("Неверный выбор.");
        }
    }

    private void dictionaryOperations(DictionaryService dictionaryService, String filePath) {
        boolean inMenu = true;

        while (inMenu) {
            System.out.println("\n=== ОПЕРАЦИИ СО СЛОВАРЕМ ===");
            System.out.println("Файл: " + filePath);
            System.out.println("1. Просмотреть словарь");
            System.out.println("2. Добавить запись");
            System.out.println("3. Удалить запись по ключу");
            System.out.println("4. Найти перевод по ключу");
            System.out.println("5. Вернуться в главное меню");

            int operation = getIntInput("Выберите операцию: ");

            switch (operation) {
                case 1 -> dictionaryService.printDictionary();
                case 2 -> addEntry(dictionaryService);
                case 3 -> removeEntry(dictionaryService);
                case 4 -> searchEntry(dictionaryService);
                case 5 -> {
                    inMenu = false;
                    dictionaryService.saveDictionary();
                }
                default -> System.out.println("Неверный выбор.");
            }
        }
    }

    private void addEntry(DictionaryService service) {
        scanner.nextLine();

        System.out.print("Введите ключ: ");
        String key = scanner.nextLine().trim();

        System.out.print("Введите перевод на русский: ");
        String value = scanner.nextLine().trim();

        if (service.getDictionary().addEntry(key, value)) {
            System.out.println("Запись успешно добавлена.");
        } else {
            System.out.println("Ошибка: неверный формат ключа или ключ уже существует.");
        }
    }

    private void removeEntry(DictionaryService service) {
        scanner.nextLine();
        System.out.print("Введите ключ для удаления: ");
        String key = scanner.nextLine().trim();

        if (service.getDictionary().removeEntry(key)) {
            System.out.println("Запись успешно удалена.");
        } else {
            System.out.println("Запись с таким ключом не найдена.");
        }
    }

    private void searchEntry(DictionaryService service) {
        scanner.nextLine();
        System.out.print("Введите ключ для поиска: ");
        String key = scanner.nextLine().trim();

        String result = service.getDictionary().searchByKey(key);

        if (result != null) {
            System.out.println("Перевод: " + result);
        } else {
            System.out.println("Запись с таким ключом не найдена.");
        }
    }

    private void saveAllDictionaries() {
        System.out.println("\n=== СОХРАНЕНИЕ ФАЙЛОВ ===");
        System.out.println("Сохраняю в файлы:");
        System.out.println("1. " + fourLetterFilePath);
        System.out.println("2. " + fiveDigitFilePath);

        fourLetterDictionaryService.saveDictionary();
        fiveDigitDictionaryService.saveDictionary();
        System.out.println("Все словари сохранены.");
    }

    private int getIntInput(String prompt) {
        System.out.print(prompt);
        while (!scanner.hasNextInt()) {
            System.out.println("Введите число.");
            scanner.next();
            System.out.print(prompt);
        }
        int result = scanner.nextInt();
        return result;
    }
}