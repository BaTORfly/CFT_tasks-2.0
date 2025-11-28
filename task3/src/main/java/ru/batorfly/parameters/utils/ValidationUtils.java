package ru.batorfly.parameters.utils;

import java.nio.file.Files;
import java.nio.file.Path;

public class ValidationUtils {

    private ValidationUtils() {
        throw new UnsupportedOperationException("Utility class should not be instantiated");
    }

    public static String validateAndResolveInputFile(String filePath) {
        if (filePath == null || filePath.trim().isEmpty()) {
            throw new IllegalArgumentException("Input file path cannot be null or empty");
        }
        // Checks like usual file in the file system
        Path path = Path.of(filePath);
        if (Files.exists(path) && Files.isRegularFile(path) && Files.isReadable(path)) {
            return path.toAbsolutePath().toString();
        }
        // If it's not found as a file, search in the classpath resources
        if (ValidationUtils.class.getClassLoader().getResource(filePath) != null) {
            return "classpath:" + filePath;
        }

        throw new IllegalArgumentException(
                String.format("Input file not found: '%s' (searched as file and in classpath resources)", filePath)
        );
    }
}
