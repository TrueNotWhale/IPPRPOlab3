package com.duplicatefinder.logic;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Утилитарный класс для вычисления хэш-сумм файлов.
 */
public class FileHashUtil {

    /**
     * Вычисляет хэш-сумму файла по заданному алгоритму.
     *
     * @param path      путь к файлу.
     * @param algorithm алгоритм хэширования ("MD5", "SHA-256").
     * @return шестнадцатеричное представление хэш-суммы в нижнем регистре.
     * @throws IOException              если произошла ошибка чтения файла.
     * @throws NoSuchAlgorithmException если указанный алгоритм хэширования не поддерживается.
     */
    public static String calculateHash(Path path, String algorithm) throws IOException, NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance(algorithm);
        try (InputStream is = Files.newInputStream(path)) {
            byte[] buffer = new byte[8192];
            int read;
            while ((read = is.read(buffer)) > 0) {
                digest.update(buffer, 0, read);
            }
        }
        byte[] hashBytes = digest.digest();
        return bytesToHex(hashBytes);
    }

    /**
     * Преобразует массив байт в шестнадцатеричную строку.
     *
     * @param bytes массив байт.
     * @return шестнадцатеричная строка.
     */
    private static String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }
}