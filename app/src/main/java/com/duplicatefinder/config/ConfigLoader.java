package com.duplicatefinder.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

/**
 * Загрузчик конфигурации из файла app.properties.
 * Считывает настройки, необходимые для работы приложения.
 */
public class ConfigLoader {
    private final Properties properties;

    /**
     * Конструктор, который загружает свойства из указанного файла.
     *
     * @param configPath путь к файлу конфигурации.
     * @throws IOException если файл не найден или произошла ошибка чтения.
     */
    public ConfigLoader(String configPath) throws IOException {
        properties = new Properties();
        Path path = Paths.get(configPath);
        if (!Files.exists(path)) {
            throw new IOException("Файл конфигурации не найден: " + configPath);
        }
        try (InputStream input = new FileInputStream(configPath)) {
            properties.load(input);
        }
    }

    /**
     * Возвращает значение свойства по его ключу.
     *
     * @param key ключ свойства.
     * @return значение свойства.
     * @throws IllegalStateException если свойство с таким ключом не найдено.
     */
    public String getProperty(String key) {
        String value = properties.getProperty(key);
        if (value == null) {
            throw new IllegalStateException("Свойство '" + key + "' не найдено в файле конфигурации.");
        }
        return value;
    }
}