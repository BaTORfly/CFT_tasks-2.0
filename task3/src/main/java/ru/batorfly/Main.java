package ru.batorfly;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.batorfly.cli.CmdParams;
import ru.batorfly.cli.ParseCmdParams;
import ru.batorfly.factory.ShapeFactory;
import ru.batorfly.factory.ShapeParameters;

import java.io.IOException;

public class Main {
    private static final Logger log = LogManager.getLogger(Main.class);

    public static void main(String[] args) {
        log.info("Starting the program...");
        CmdParams cmdParams = ParseCmdParams.parse(args);

        try {
            var shapeParameters = ShapeParameters.readFigureParametersFromFile(cmdParams.inputFile());

            var shape = ShapeFactory.create(shapeParameters);

            var shapeInfoWriter = new ShapeInfoWriter(cmdParams);

            shapeInfoWriter.write(shape.buildShapesDataStr().toString());
        } catch (IllegalArgumentException | IOException ex){
            log.error("Application failed", ex);
            System.exit(1);
        }
    }
}