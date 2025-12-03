package ru.batorfly.shapes;

import ru.batorfly.factory.ShapeType;

import java.text.DecimalFormat;
import java.util.Arrays;

public abstract class Shape {

    static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("#.##");
    static final String UNIT = " см";
    static final String SQ_UNIT = UNIT + "²";
    static final String EOL = System.lineSeparator();

    public static double[] parseDoubles(String line, int exceptedCount) throws IllegalArgumentException {
        double[] params;
        try {
            params = Arrays.stream(line.trim().split("\\s+"))
                    .mapToDouble(Double::parseDouble)
                    .toArray();
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Error parsing parameters. Ensure all parameters are numbers.");
        }

        if (params.length != exceptedCount) {
            throw new IllegalArgumentException("Excepted " + exceptedCount + " parameters but found: " + params.length);
        }

        for (double param : params) {
            if (param < 1) {
                throw new IllegalArgumentException("All the parameters must be >=1 but found: " + param);
            }
        }
        return params;
    }

    public abstract ShapeType getType();

    public abstract double computePerimeter();

    public abstract double computeArea();

    public StringBuilder buildShapesDataStr(){
        var sb = new StringBuilder();

        sb.append("Shape type: ")
                .append(getType()).append(EOL);
        sb.append("Area: ")
                .append(DECIMAL_FORMAT.format(computeArea())).append(SQ_UNIT).append(EOL);
        sb.append("Perimeter: ")
                .append(DECIMAL_FORMAT.format(computePerimeter())).append(UNIT).append(EOL);
        return sb;
    }

}
