package ru.batorfly.shapes;

import ru.batorfly.factory.ShapeType;

import java.io.BufferedWriter;
import java.io.IOException;

public class Triangle extends Shape{
    @Override
    public void writeFigureData(BufferedWriter writer) throws IOException {

    }

    @Override
    public ShapeType getType() {
        return null;
    }

    @Override
    public double computePerimeter() {
        return 0;
    }

    @Override
    public double computeArea() {
        return 0;
    }
}
