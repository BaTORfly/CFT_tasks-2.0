package ru.batorfly.shapes;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.batorfly.factory.ShapeType;

import java.io.BufferedWriter;
import java.io.IOException;
import java.text.DecimalFormat;

public abstract class Shape {

    private static final Logger log = LogManager.getLogger(Shape.class);

    static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("#.##");
    static final String UNIT = " см";
    static final String SQ_UNIT = UNIT + "²";
    static final String EOL = System.lineSeparator();


    public abstract void writeFigureData(BufferedWriter writer) throws IOException;

    public abstract ShapeType getType();

    public abstract double computePerimeter();

    public abstract double computeArea();

    StringBuilder computeShapesDataStr(){
        var sb = new StringBuilder();

        sb.append("Shape type: ")
                .append(getType()).append(EOL);
        sb.append("Area: ")
                .append(DECIMAL_FORMAT.format(computeArea())).append(SQ_UNIT).append(EOL);
        sb.append("Perimeter: ")
                .append(DECIMAL_FORMAT.format(computePerimeter())).append(EOL);
        return sb;
    }

    void writeFigureData(BufferedWriter writer, String shapeData) throws IOException {
        log.debug("Printing shape data to a present output stream");
        writer.write(shapeData);
    }
}
