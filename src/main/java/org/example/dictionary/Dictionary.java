package org.example.dictionary;

import java.util.Map;

public interface Dictionary {
    boolean addEntry(String key, String value);
    boolean removeEntry(String key);
    String searchByKey(String key);
    Map<String, String> getAllEntries();
    String getDictionaryType();
}