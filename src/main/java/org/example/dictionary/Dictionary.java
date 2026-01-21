package org.example.dictionary;

import java.util.Map;

public interface Dictionary {
    void loadFromFile(String filePath);
    boolean addEntry(String key, String value);
    boolean removeEntry(String key);
    String searchByKey(String key);
    Map<String, String> getAllEntries();
    void saveToFile(String filePath);
    String getDictionaryType();
}