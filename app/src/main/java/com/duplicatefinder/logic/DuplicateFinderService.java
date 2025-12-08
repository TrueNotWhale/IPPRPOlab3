package com.duplicatefinder.logic;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Сервис для поиска дубликатов файлов в указанной директории.
 */
public class DuplicateFinderService {

    private final String hashAlgorithm;
    private final List<String> ignoreDirs;

    /**
     * Конструктор сервиса.
     *
     * @param hashAlgorithm алгоритм хэширования.
     * @param ignoreDirs    список директорий для игнорирования.
     */
    public DuplicateFinderService(String hashAlgorithm, String[] ignoreDirs) {
        this.hashAlgorithm = hashAlgorithm;
        this.ignoreDirs = Arrays.asList(ignoreDirs);
    }

    /**
     * Основной метод, запускающий процесс поиска дубликатов.
     *
     * @param directory директория для сканирования.
     * @return Карта, где ключ - хэш-сумма, а значение - список путей к файлам-дубликатам.
     * @throws IOException если произошла ошибка при обходе директории.
     */
    public Map<String, List<Path>> findDuplicates(String directory) throws IOException {
        // 1. Рекурсивно находим все файлы в директории, исключая игнорируемые
        List<Path> allFiles;
        try (Stream<Path> walk = Files.walk(Paths.get(directory))) {
            allFiles = walk
                    .filter(Files::isRegularFile)
                    .filter(path -> !isIgnored(path))
                    .collect(Collectors.toList());
        }

        // 2. Группируем файлы по размеру (Задание 1)
        Map<Long, List<Path>> filesGroupedBySize = allFiles.stream()
                .collect(Collectors.groupingBy(this::getFileSize));

        // 3. Для групп с >1 файлом, группируем по хэшу (Задание 2)
        return filesGroupedBySize.values().stream()
                .filter(list -> list.size() > 1)
                .flatMap(list -> groupByHash(list).values().stream())
                .filter(list -> list.size() > 1)
                .collect(Collectors.toMap(
                        list -> getFileHash(list.get(0)),
                        list -> list
                ));
    }

    /**
     * Проверяет, следует ли игнорировать данный путь.
     */
    private boolean isIgnored(Path path) {
        return ignoreDirs.stream().anyMatch(dir -> path.toString().contains(dir));
    }

    /**
     * Возвращает размер файла. Обрабатывает возможные ошибки.
     */
    private long getFileSize(Path path) {
        try {
            return Files.size(path);
        } catch (IOException e) {
            System.err.println("Не удалось получить размер файла: " + path);
            return -1;
        }
    }

    /**
     * Группирует список файлов по их хэш-суммам.
     */
    private Map<String, List<Path>> groupByHash(List<Path> paths) {
        return paths.stream().collect(Collectors.groupingBy(this::getFileHash));
    }

    /**
     * Возвращает хэш файла. Обрабатывает возможные ошибки.
     */
    private String getFileHash(Path path) {
        try {
            return FileHashUtil.calculateHash(path, hashAlgorithm);
        } catch (IOException | NoSuchAlgorithmException e) {
            System.err.println("Не удалось рассчитать хэш для файла: " + path);
            return ""; // Возвращаем пустую строку, чтобы эти файлы не попали в группу дубликатов
        }
    }
}