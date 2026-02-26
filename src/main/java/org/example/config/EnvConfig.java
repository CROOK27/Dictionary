package org.example.config;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

@Configuration
public class EnvConfig {

    private final Dotenv dotenv = Dotenv.configure()
            .ignoreIfMissing()
            .load();

    @Bean
    public String dbUrl() {
        return dotenv.get("DB_URL", "jdbc:postgresql://localhost:5432/dictionary_db");
    }

    @Bean
    public String dbUsername() {
        return dotenv.get("DB_USERNAME", "postgres");
    }

    @Bean
    public String dbPassword() {
        return dotenv.get("DB_PASSWORD", "postgres");
    }

    @Bean
    public Integer dbInitialSize() {
        return Integer.parseInt(dotenv.get("DB_INITIAL_SIZE", "5"));
    }

    @Bean
    public Integer dbMaxActive() {
        return Integer.parseInt(dotenv.get("DB_MAX_ACTIVE", "10"));
    }
}