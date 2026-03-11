package com.moon.labs.database.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ApplicationProperties {

    private final Properties properties = new Properties();

    public ApplicationProperties() {
        load();
    }

    private void load() {
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream("application.properties")) {
            if (inputStream == null) {
                throw new IllegalStateException("Файл application.properties не найден в resources");
            }
            properties.load(inputStream);
        } catch (IOException e) {
            throw new IllegalStateException("Не удалось прочитать application.properties", e);
        }
    }

    public String getDbUrl() {
        return require("db.url");
    }

    public String getDbUsername() {
        return require("db.username");
    }

    public String getDbPassword() {
        return require("db.password");
    }

    public String getDbSchema() {
        return require("db.schema");
    }

    public int getPoolSize() {
        return Integer.parseInt(require("db.pool.size"));
    }

    private String require(String key) {
        String value = properties.getProperty(key);
        if (value == null || value.isBlank()) {
            throw new IllegalStateException("Отсутствует обязательное свойство: " + key);
        }
        return value;
    }
}