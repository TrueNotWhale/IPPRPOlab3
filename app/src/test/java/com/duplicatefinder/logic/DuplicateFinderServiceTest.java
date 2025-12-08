package com.duplicatefinder.logic;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class DuplicateFinderServiceTest {

    private DuplicateFinderService service;

    // JUnit 5 создаст для нас временную директорию перед каждым тестом
    @TempDir
    Path tempDir;

    @BeforeEach
    void setUp() {
        // Инициализируем сервис перед каждым тестом
        service = new DuplicateFinderService("SHA-256", new String[]{".git"});
    }

    @Test
    @DisplayName("Должен находить одну группу дубликатов")
    void shouldFindOneGroupOfDuplicates() throws IOException {
        // Arrange: создаем файлы
        Files.writeString(tempDir.resolve("file1.txt"), "hello world");
        Files.writeString(tempDir.resolve("file2.txt"), "hello world");
        Files.writeString(tempDir.resolve("unique.txt"), "unique content");

        // Act: запускаем поиск
        Map<String, List<Path>> duplicates = service.findDuplicates(tempDir.toString());

        // Assert: проверяем результат
        assertFalse(duplicates.isEmpty(), "Карта дубликатов не должна быть пустой");
        assertEquals(1, duplicates.size(), "Должна быть найдена одна группа дубликатов");

        List<Path> group = duplicates.values().iterator().next();
        assertEquals(2, group.size(), "В группе должно быть два файла");
        assertTrue(group.stream().anyMatch(p -> p.getFileName().toString().equals("file1.txt")));
        assertTrue(group.stream().anyMatch(p -> p.getFileName().toString().equals("file2.txt")));
    }

    @Test
    @DisplayName("Не должен находить дубликаты, если их нет")
    void shouldNotFindDuplicatesWhenNoneExist() throws IOException {
        Files.writeString(tempDir.resolve("file1.txt"), "content 1");
        Files.writeString(tempDir.resolve("file2.txt"), "content 2");

        Map<String, List<Path>> duplicates = service.findDuplicates(tempDir.toString());

        assertTrue(duplicates.isEmpty(), "Карта дубликатов должна быть пустой");
    }

    @Test
    @DisplayName("Должен находить дубликаты во вложенных папках")
    void shouldFindDuplicatesInSubdirectories() throws IOException {
        Path subDir = tempDir.resolve("sub");
        Files.createDirectory(subDir);

        Files.writeString(tempDir.resolve("original.log"), "log message");
        Files.writeString(subDir.resolve("copy.log"), "log message");

        Map<String, List<Path>> duplicates = service.findDuplicates(tempDir.toString());

        assertEquals(1, duplicates.size());
        assertEquals(2, duplicates.values().iterator().next().size());
    }

    @Test
    @DisplayName("Должен игнорировать файлы с разным размером")
    void shouldIgnoreFilesWithDifferentSizes() throws IOException {
        Files.writeString(tempDir.resolve("small.txt"), "small");
        Files.writeString(tempDir.resolve("large.txt"), "large content");

        Map<String, List<Path>> duplicates = service.findDuplicates(tempDir.toString());

        assertTrue(duplicates.isEmpty());
    }
}