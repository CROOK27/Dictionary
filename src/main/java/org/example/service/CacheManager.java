package org.example.service;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CacheManager {
    private int cacheSize;
    private int cacheTimeout;

    public void init() {
        System.out.println("CacheManager initialized with size: " + cacheSize +
                ", timeout: " + cacheTimeout);
    }

    public void destroy() {
        System.out.println("CacheManager destroyed");
        clearCache();
    }

    private void clearCache() {
        // Очистка кэша
    }
}