package ru.batorfly.factory;

import lombok.NoArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.batorfly.exceptions.IllegalTriangleSidesException;
import ru.batorfly.shapes.Circle;
import ru.batorfly.shapes.Rectangle;
import ru.batorfly.shapes.Shape;
import ru.batorfly.shapes.Triangle;

@NoArgsConstructor
public class ShapeFactory {
    private static final Logger log = LogManager.getLogger(ShapeFactory.class);

    public static Shape create(ShapeParameters params){
        Shape shape = null;
        ShapeType type;

        try {
            type = ShapeType.valueOf(params.typeLine().toUpperCase());
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException(String.format("Invalid shape type: %s", params.typeLine()));
        }

        switch (type) {
            case CIRCLE -> {
                shape = Circle.create(params.parametersLine());
            }
            case RECTANGLE -> {
                shape = Rectangle.create(params.parametersLine());
            }
            case TRIANGLE -> {
                shape = Triangle.create(params.parametersLine());
            }
        }

        return shape;
    }
}
