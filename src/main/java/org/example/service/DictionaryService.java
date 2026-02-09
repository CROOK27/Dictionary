package org.example.service;

import lombok.Data;
import org.example.dictionary.Dictionary;
import org.example.file.FileDictionaryStorage;
import java.util.Map;

@Data
public class DictionaryService {
    private Dictionary dictionary;
    private String filePath;

    public DictionaryService() {
    }

    public DictionaryService(Dictionary dictionary, String filePath) {
        this.dictionary = dictionary;
        this.filePath = filePath;
    }

    public void loadDictionary() {
        FileDictionaryStorage.loadDictionaryFromFile(dictionary, filePath);
    }

    public void saveDictionary() {
        FileDictionaryStorage.saveDictionaryToFile(dictionary, filePath);
    }

    public void printDictionary() {
        Map<String, String> entries = dictionary.getAllEntries();
        if (entries.isEmpty()) {
            System.out.println("Словарь пуст.");
        } else {
            entries.forEach((key, value) ->
                    System.out.println(key + " - " + value));
        }
    }

}