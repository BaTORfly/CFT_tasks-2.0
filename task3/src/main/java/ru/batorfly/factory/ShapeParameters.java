package ru.batorfly.factory;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.batorfly.utils.IOUtils;

import java.io.IOException;
import java.util.List;

public record ShapeParameters(String typeLine, String parametersLine) {
    private static final Logger log = LogManager.getLogger(ShapeParameters.class);

    public static ShapeParameters readFigureParametersFromFile(String filePath) {
        String content = null;

        try{
            log.info(String.format("Reading shape parameters from file: %s", filePath));
            content = IOUtils.readFileContent(filePath);
        } catch (IOException ex) {
            log.error(String.format("Error while reading shape parameters: %s", ex.getMessage()));
        }
        if (content == null) {
            throw new IllegalArgumentException(String.format(
                    "Error while reading shape parameters from file: %s. File must not be empty", filePath));
        }

        List<String> lines = content.lines().toList();

        if (lines.size() != 2) {
            throw new IllegalArgumentException(String.format(
                    "Error while reading shape parameters from file: %s. Excepted 2 lines, got %d", filePath, lines.size()
            ));
        }

        return new ShapeParameters(lines.get(0), lines.get(1));
    }
}
