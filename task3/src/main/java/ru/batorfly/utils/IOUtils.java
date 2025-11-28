package ru.batorfly.utils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class IOUtils {
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

    public static void createOutputDirectoryIfNeeded() throws IOException {
        Path outputDir = Path.of("output");
        if (!Files.exists(outputDir)) {
            Files.createDirectories(outputDir);
        }
    }

    public static void writeToFile(String content, String filePath) throws IOException {
        Path path = Path.of(filePath);
        Files.writeString(path, content, StandardCharsets.UTF_8);
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
