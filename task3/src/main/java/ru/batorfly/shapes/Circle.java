package ru.batorfly.shapes;

import lombok.AllArgsConstructor;
import ru.batorfly.factory.ShapeType;

@AllArgsConstructor
public class Circle extends Shape{
    private static final ShapeType SHAPE_TYPE = ShapeType.CIRCLE;
    private static final int EXCEPTED_ARGUMENTS_COUNT = 1;

    private final double radius;

    public static Circle create(String parameterLine){
        var doubles = Shape.parseDoubles(parameterLine, EXCEPTED_ARGUMENTS_COUNT);
        return new Circle(doubles[0]);
    }

    @Override
    public ShapeType getType() {
        return ShapeType.CIRCLE;
    }

    @Override
    public double computePerimeter() {
        return 2 * Math.PI * radius;
    }

    @Override
    public double computeArea() {
        return Math.PI * radius * radius;
    }

    private double computeDiameter() {
        return radius * 2;
    }

    @Override
    public StringBuilder buildShapesDataStr() {
        StringBuilder circleInfo = super.buildShapesDataStr();
        circleInfo.append("radius: ").append(DECIMAL_FORMAT.format(radius)).append(UNIT).append(EOL);
        circleInfo.append("diameter: ").append(DECIMAL_FORMAT.format(computeDiameter())).append(UNIT).append(EOL);
        return circleInfo;
    }

}
