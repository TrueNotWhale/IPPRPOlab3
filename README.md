# Поиск дубликатов файлов (Duplicate File Finder)

Простое консольное приложение на Java для рекурсивного поиска файлов-дубликатов в указанной директории на основе их хэш-сумм.

**Технологии:** Java 17, Gradle.

## Пример работы

Приложение сканирует директорию, указанную в `app.properties`, и выводит группы одинаковых файлов.

**Пример вывода:**
--- Found Duplicates ---
Group 1 (Hash: a1b2c3d4...):
C:\dev\project\test-data\file.txt
C:\dev\project\test-data\backup\file.txt.bak
Group 2 (Hash: e5f6g7h8...):
C:\dev\project\test-data\images\img1.jpg

## Сборка проекта

Для сборки проекта используйте Gradle Wrapper. Откройте терминал в корневой папке проекта и выполните команду:

./gradlew build
Или для Windows:
code
Bash
gradlew.bat build

Это скомпилирует код, запустит тесты и создаст исполняемый JAR-файл в директории app/build/libs/.