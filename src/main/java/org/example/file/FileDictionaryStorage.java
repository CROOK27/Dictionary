package org.example.file;

import org.example.dictionary.Dictionary;
import java.io.*;
import java.nio.file.*;
import java.util.Map;

public class FileDictionaryStorage {

    public static void loadDictionaryFromFile(Dictionary dictionary, String filePath) {
        Path path = Paths.get(filePath);

        if (!Files.exists(path)) {
            System.out.println("Файл не найден: " + filePath);
            System.out.println("Создам новый файл при сохранении.");
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            int loadedCount = 0;
            int errorCount = 0;

            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty() || line.startsWith("#") || line.startsWith("//")) {
                    continue;
                }

                String[] parts = line.split("\\s*-\\s*", 2);
                if (parts.length == 2) {
                    String key = parts[0].trim();
                    String value = parts[1].trim();
                    if (dictionary.addEntry(key, value)) {
                        loadedCount++;
                    } else {
                        errorCount++;
                        System.out.println("Не удалось добавить запись: " + line);
                    }
                } else {
                    errorCount++;
                    System.out.println("Некорректный формат строки: " + line);
                }
            }

            if (loadedCount > 0) {
                System.out.println("Загружено " + loadedCount + " записей из файла: " + filePath);
            }
            if (errorCount > 0) {
                System.out.println("Пропущено " + errorCount + " некорректных записей.");
            }

        } catch (IOException e) {
            System.out.println("Ошибка при загрузке словаря: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void saveDictionaryToFile(Dictionary dictionary, String filePath) {
        try {
            Path path = Paths.get(filePath);
            Path parentDir = path.getParent();

            if (parentDir != null && !Files.exists(parentDir)) {
                Files.createDirectories(parentDir);
                System.out.println("Создана директория: " + parentDir.toAbsolutePath());
            }

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
                Map<String, String> entries = dictionary.getAllEntries();

                if (entries.isEmpty()) {
                    System.out.println("Словарь пуст. Создаю пустой файл.");
                }

                for (Map.Entry<String, String> entry : entries.entrySet()) {
                    writer.write(entry.getKey() + " - " + entry.getValue());
                    writer.newLine();
                }

                System.out.println("Словарь сохранен в файл: " + path.toAbsolutePath());
                System.out.println("Сохранено записей: " + entries.size());
            }
        } catch (IOException e) {
            System.out.println("Ошибка при сохранении словаря в " + filePath);
            System.out.println("Сообщение: " + e.getMessage());
            e.printStackTrace();
        }
    }
}