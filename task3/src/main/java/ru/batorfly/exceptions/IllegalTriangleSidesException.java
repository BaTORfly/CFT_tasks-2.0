package ru.batorfly.exceptions;

import lombok.Getter;

@Getter
public class IllegalTriangleSidesException extends IllegalArgumentException{
    private final double side1, side2, side3;

    public IllegalTriangleSidesException(double side1, double side2, double side3) {
        super(String.format(
                "Invalid triangle sides: %.2f, %.2f, %.2f. Violates triangle inequality",
                side1, side2, side3
        ));
        this.side1 = side1;
        this.side2 = side2;
        this.side3 = side3;
    }
}
