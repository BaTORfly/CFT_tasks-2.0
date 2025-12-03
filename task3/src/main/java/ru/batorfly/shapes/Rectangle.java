package ru.batorfly.shapes;

import lombok.AllArgsConstructor;
import ru.batorfly.factory.ShapeType;

@AllArgsConstructor
public class Rectangle extends Shape{
    private static final ShapeType SHAPE_TYPE = ShapeType.RECTANGLE;
    private static final int EXCEPTED_ARGUMENTS_COUNT = 2;

    private final double length;
    private final double width;

    public static Rectangle create(String parameterLine) {
        var doubles = Shape.parseDoubles(parameterLine, EXCEPTED_ARGUMENTS_COUNT);
        return new Rectangle(doubles[0], doubles[1]);
    }

    @Override
    public ShapeType getType() {
        return SHAPE_TYPE;
    }

    @Override
    public double computePerimeter() {
        return (length + width) * 2;
    }

    @Override
    public double computeArea() {
        return length * width;
    }

    public double computeDiagonal(){
        return Math.sqrt(Math.pow(length, 2) + Math.pow(width, 2));
    }

    @Override
    public StringBuilder buildShapesDataStr() {
        StringBuilder rectangleInfo = super.buildShapesDataStr();
        rectangleInfo.append("Diagonal: ")
                .append(DECIMAL_FORMAT.format(computeDiagonal())).append(UNIT).append(EOL);
        rectangleInfo.append("Length: ")
                .append(DECIMAL_FORMAT.format(length)).append(UNIT).append(EOL);
        rectangleInfo.append("Width: ")
                .append(DECIMAL_FORMAT.format(width)).append(UNIT).append(EOL);

        return rectangleInfo;
    }
}
