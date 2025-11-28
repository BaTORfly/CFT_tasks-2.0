package ru.batorfly.shapes;

import ru.batorfly.factory.ShapeType;

import java.io.BufferedWriter;
import java.io.IOException;

public class Circle extends Shape{

    @Override
    public void writeFigureData(BufferedWriter writer) throws IOException {

    }

    @Override
    public ShapeType getType() {
        return ShapeType.CIRCLE;
    }

    @Override
    public double computePerimeter() {
        return 0;
    }

    @Override
    public double computeArea() {
        return 0;
    }

    @Override
    StringBuilder computeShapesDataStr() {
        return super.computeShapesDataStr();
    }

    @Override
    void writeFigureData(BufferedWriter writer, String shapeData) throws IOException {
        super.writeFigureData(writer, shapeData);
    }
}
