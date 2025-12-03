package ru.batorfly.shapes;

import ru.batorfly.exceptions.IllegalTriangleSidesException;
import ru.batorfly.factory.ShapeType;

public class Triangle extends Shape{
    private static final ShapeType SHAPE_TYPE = ShapeType.TRIANGLE;
    private static final String DEGREE_UNIT = "°";
    private static final int EXCEPTED_ARGUMENTS_COUNT = 3;

    private final double side1, side2, side3;

    public Triangle(double side1, double side2, double side3) throws IllegalTriangleSidesException {
        if (!isTriangleValid(side1, side2, side3))
            throw new IllegalTriangleSidesException(side1, side2, side3);
        this.side1 = side1;
        this.side2 = side2;
        this.side3 = side3;
    }

    public static Triangle create(String parameterLine) throws IllegalTriangleSidesException{
        var doubles = Shape.parseDoubles(parameterLine, EXCEPTED_ARGUMENTS_COUNT);
        return new Triangle(doubles[0], doubles[1], doubles[2]);
    }

    @Override
    public ShapeType getType() {
        return SHAPE_TYPE;
    }

    @Override
    public double computePerimeter() {
        return side1 + side2 + side3;
    }

    @Override
    public double computeArea() {
        double p = computePerimeter() / 2;
        return Math.sqrt(p * (p - side1) * (p - side2) * (p - side3));
    }

    @Override
    public StringBuilder buildShapesDataStr() {
        StringBuilder triangleInfo = super.buildShapesDataStr();

        triangleInfo.append("Side1 = ").append(DECIMAL_FORMAT.format(side1)).append(UNIT).append(EOL);
        triangleInfo.append("Angle opposite the side1 (in degrees) = ")
                .append(DECIMAL_FORMAT.format(findAngleOppositeSide(side1, side2,side3)))
                .append(DEGREE_UNIT).append(EOL);

        triangleInfo.append("Side2 = ").append(DECIMAL_FORMAT.format(side2)).append(UNIT).append(EOL);
        triangleInfo.append("Angle opposite the side2 (in degrees) = ")
                .append(DECIMAL_FORMAT.format(findAngleOppositeSide(side2, side1,side3)))
                .append(DEGREE_UNIT).append(EOL);

        triangleInfo.append("Side3 = ").append(DECIMAL_FORMAT.format(side3)).append(UNIT).append(EOL);
        triangleInfo.append("Angle opposite the side3 (in degrees) = ")
                .append(DECIMAL_FORMAT.format(findAngleOppositeSide(side3, side1,side2)))
                .append(DEGREE_UNIT).append(EOL);

        return triangleInfo;
    }

    private static boolean isTriangleValid(double side1, double side2, double side3) {
         return ((side1 + side2 > side3) && (side1 + side3 > side2) && (side2 + side3 > side1));
    }

    /**
     * Calculates the angle (in degrees) opposite to side 'a'
     * using the Law of Cosines, given all three sides of a triangle.
     *
     * @param a the side opposite to the angle being calculated
     * @param b second side of the triangle
     * @param c third side of the triangle
     * @return the angle in degrees opposite to side 'a'
     */
    public static double findAngleOppositeSide(double a, double b, double c) {
        // Apply the Law of Cosines: cos(α) = (b² + c² - a²) / (2 * b * c)
        double cosAlpha = (b * b + c * c - a * a) / (2.0 * b * c);

        // Clamp cosine value to [-1, 1] to avoid numerical errors
        if (cosAlpha < -1.0) cosAlpha = -1.0;
        if (cosAlpha > 1.0) cosAlpha = 1.0;

        // Compute angle in radians and convert to degrees
        double angleRadians = Math.acos(cosAlpha);
        return Math.toDegrees(angleRadians);
    }
}
