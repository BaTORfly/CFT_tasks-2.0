package ru.batorfly.utils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public final class IOUtils {
    private IOUtils() {
        throw new UnsupportedOperationException("Utility class should not be instantiated");
    }

    public static String readFileContent(String filePath) throws IOException {
        if (filePath.startsWith("classpath:")) {
            String resourcePath = filePath.substring("classpath:".length());
            return readFromClassPath(resourcePath);
        } else {
            return readFromFileSystem(filePath);
        }
    }

    private static String readFromClassPath(String resourcePath) throws IOException {
        try (InputStream inputStream = IOUtils.class.getClassLoader().getResourceAsStream(resourcePath)) {
            if (inputStream == null) {
                throw new IOException("Resource not found in classpath: " + resourcePath);
            }
            return new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
        }
    }

    private static String readFromFileSystem(String filePath) throws IOException {
        return Files.readString(Path.of(filePath), StandardCharsets.UTF_8);
    }
}
