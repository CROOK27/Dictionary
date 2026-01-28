package org.example.dictionary;

import java.util.HashMap;
import java.util.Map;

public abstract class LanguageDictionary implements Dictionary {
    protected Map<String, String> entries;

    public LanguageDictionary() {
        this.entries = new HashMap<>();
    }

    @Override
    public boolean addEntry(String key, String value) {
        if (isValidKey(key) && !entries.containsKey(key)) {
            entries.put(key, value);
            return true;
        }
        return false;
    }

    @Override
    public boolean removeEntry(String key) {
        return entries.remove(key) != null;
    }

    @Override
    public String searchByKey(String key) {
        return entries.get(key);
    }

    @Override
    public Map<String, String> getAllEntries() {
        return new HashMap<>(entries);
    }

    @Override
    public String getDictionaryType() {
        return "Base Dictionary";
    }

    protected abstract boolean isValidKey(String key);
}