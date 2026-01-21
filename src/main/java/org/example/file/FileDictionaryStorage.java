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
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            int loadedCount = 0;
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
                    }
                }
            }
            System.out.println("Загружено " + loadedCount + " записей из файла: " + filePath);
        } catch (IOException e) {
            System.out.println("Ошибка при загрузке словаря: " + e.getMessage());
        }
    }

    public static void saveDictionaryToFile(Dictionary dictionary, String filePath) {
        try {
            // Создаем директорию, если она не существует
            Path path = Paths.get(filePath);
            Path parentDir = path.getParent();

            // создаем директорию перед записью
            if (parentDir != null && !Files.exists(parentDir)) {
                Files.createDirectories(parentDir);
                System.out.println("Создана директория: " + parentDir.toAbsolutePath());
            }

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
                Map<String, String> entries = dictionary.getAllEntries();
                for (Map.Entry<String, String> entry : entries.entrySet()) {
                    writer.write(entry.getKey() + " - " + entry.getValue());
                    writer.newLine();
                }
                System.out.println("Словарь сохранен в файл: " + path.toAbsolutePath());
                System.out.println("Сохранено записей: " + entries.size());
            }
        } catch (IOException e) {
            System.out.println("Ошибка при сохранении словаря: " + e.getMessage());
        }
    }
}