package ru.batorfly.factory;

import java.util.Arrays;

public enum ShapeType {
    CIRCLE,
    RECTANGLE,
    TRIANGLE;
    /**
     * Максимальная длина строки с названием фигуры
     * с учетом символа переноса строки
     */
    public static final int MAX_NAME_LINE_LENGTH;

    static {
        MAX_NAME_LINE_LENGTH = Arrays.stream(values())
                .mapToInt(type -> type.toString().length())
                .max()
                .orElse(0) + System.lineSeparator().length();
    }

    public static ShapeType fromString(String type) {
        return switch(type) {
            case "CIRCLE" -> CIRCLE;
            case "RECTANGLE" -> RECTANGLE;
            case "TRIANGLE" -> TRIANGLE;
            default -> throw new IllegalArgumentException("Unknown shape type: " + type);
        };
    }
}
