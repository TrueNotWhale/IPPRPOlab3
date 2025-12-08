package com.duplicatefinder;

import com.duplicatefinder.config.ConfigLoader;
import com.duplicatefinder.logic.DuplicateFinderService;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

/**
 * Главный класс приложения для поиска дубликатов файлов.
 */
public class App {
    public static void main(String[] args) {
        try {
            // Загружаем конфигурацию
            ConfigLoader config = new ConfigLoader("app.properties");
            String scanDir = config.getProperty("scan.directory");
            String hashAlgo = config.getProperty("hash.algorithm");
            String[] ignoreDirs = config.getProperty("scan.ignore.dirs").split(",");

            // Создаем и запускаем сервис
            DuplicateFinderService service = new DuplicateFinderService(hashAlgo, ignoreDirs);
            Map<String, List<Path>> duplicates = service.findDuplicates(scanDir);

            // Выводим результат (Задание 3)
            printResults(duplicates);

        } catch (IOException e) {
            System.err.println("Ошибка: " + e.getMessage());
        }
    }

    /**
     * Выводит результаты поиска дубликатов в консоль.
     */
    private static void printResults(Map<String, List<Path>> duplicates) {
        if (duplicates.isEmpty()) {
            System.out.println("Дубликаты не найдены.");
            return;
        }

        System.out.println("--- Found Duplicates ---");
        int groupIndex = 1;
        for (Map.Entry<String, List<Path>> entry : duplicates.entrySet()) {
            System.out.printf("\nGroup %d (Hash: %s):\n", groupIndex++, entry.getKey());
            for (Path path : entry.getValue()) {
                System.out.println(" - " + path.toAbsolutePath());
            }
        }
    }
}