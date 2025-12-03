package ru.batorfly;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.batorfly.cli.CmdParams;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class ShapeInfoWriter {
    private static final Logger log = LogManager.getLogger(ShapeInfoWriter.class);
    private static final String OUTPUT_FILE_DIR = "task3/output";
    private static final String FILENAME = "shapeInfo.txt";

    private final boolean consoleMode;
    private final boolean fileMode;

    public ShapeInfoWriter(CmdParams cmdParams) {
        this.consoleMode = cmdParams.consoleMode();
        this.fileMode = cmdParams.fileMode();
    }

    public void write(String figureInfo) throws IOException {
        if (consoleMode) {
            printToConsole(figureInfo);
            log.trace("Console mode is active: figure information printed to console.");
        } else if (fileMode) {
            writeToFile(figureInfo);
            log.trace("File mode is active: figure information written to file.");
        }
    }

    private void printToConsole(String figureInfo) {
        System.out.println(figureInfo);
    }

    private void writeToFile(String figureInfo) throws IOException {
        File resourcesDir = new File(OUTPUT_FILE_DIR);
        if (!resourcesDir.exists()) {
            boolean dirCreated = resourcesDir.mkdirs();
            if (!dirCreated) {
                log.error("Error: Unable to create output directory.");
                return;
            } else {
                log.info("Resources directory created successfully.");
            }
        }
        File outputFile = new File(resourcesDir, FILENAME);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {
            writer.write(figureInfo);
            log.info("Figure information has been successfully written to " + outputFile.getAbsolutePath());
        } catch (IOException ex) {
            throw new IOException("Error writing figure information to " + outputFile.getAbsolutePath(), ex);
        }
    }
}
