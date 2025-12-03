package factory;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

import ru.batorfly.factory.ShapeFactory;
import ru.batorfly.factory.ShapeParameters;
import ru.batorfly.factory.ShapeType;
import ru.batorfly.exceptions.IllegalTriangleSidesException;

public class ShapeFactoryTest {

    // === tests for successful creating shapes ===

    @Test
    @DisplayName("Should create Circle from CIRCLE type")
    void shouldCreateCircle() {
        var params = new ShapeParameters("CIRCLE", "5");
        var shape = ShapeFactory.create(params);
        assertThat(shape).isNotNull();
        assertThat(shape.getType()).isEqualTo(ShapeType.CIRCLE);
        assertThat(shape.computeArea()).isCloseTo(Math.PI * 25, within(1e-10));
    }

    @Test
    @DisplayName("Should create Rectangle from RECTANGLE type")
    void shouldCreateRectangle() {
        var params = new ShapeParameters("RECTANGLE", "3 4");
        var shape = ShapeFactory.create(params);
        assertThat(shape).isNotNull();
        assertThat(shape.getType()).isEqualTo(ShapeType.RECTANGLE);
        assertThat(shape.computeArea()).isEqualTo(12.0);
    }

    @Test
    @DisplayName("Should create Triangle from TRIANGLE type")
    void shouldCreateTriangle() {
        var params = new ShapeParameters("TRIANGLE", "3 4 5");
        var shape = ShapeFactory.create(params);
        assertThat(shape).isNotNull();
        assertThat(shape.getType()).isEqualTo(ShapeType.TRIANGLE);
        assertThat(shape.computeArea()).isCloseTo(6.0, within(1e-10));
    }

    // === tests for passing exceptions from shapes ===

    @Test
    @DisplayName("Should propagate IllegalArgumentException from Circle.create()")
    void shouldPropagateExceptionFromCircle() {
        var params = new ShapeParameters("CIRCLE", "0.5");
        assertThrows(IllegalArgumentException.class, () -> ShapeFactory.create(params));
    }

    @Test
    @DisplayName("Should propagate IllegalArgumentException from Rectangle.create()")
    void shouldPropagateExceptionFromRectangle() {
        var params = new ShapeParameters("RECTANGLE", "10 0.9");
        assertThrows(IllegalArgumentException.class, () -> ShapeFactory.create(params));
    }

    @Test
    @DisplayName("Should propagate IllegalTriangleSidesException from Triangle.create()")
    void shouldPropagateExceptionFromTriangle() {
        var params = new ShapeParameters("TRIANGLE", "1 2 5");
        assertThrows(IllegalTriangleSidesException.class, () -> ShapeFactory.create(params));
    }

    // === tests for incorrect shape type ===

    static Stream<Arguments> invalidShapeTypes() {
        return Stream.of(
                Arguments.of("CUB"),
                Arguments.of("square"),
                Arguments.of(""),
                Arguments.of("TRIANGLe")
        );
    }

    // === The case-independence test ===

    @ParameterizedTest
    @MethodSource("validCaseVariants")
    @DisplayName("Should accept shape type in any case")
    void shouldAcceptShapeTypeInAnyCase(String typeVariant) {
        ShapeParameters params = new ShapeParameters(typeVariant, "5");
        var shape = ShapeFactory.create(params);
        assertThat(shape).isNotNull();
        assertThat(shape.getType()).isEqualTo(ShapeType.CIRCLE);
    }

    static Stream<Arguments> validCaseVariants() {
        return Stream.of(
                Arguments.of("circle"),
                Arguments.of("CIRCLE"),
                Arguments.of("Circle"),
                Arguments.of("CiRcLe")
        );
    }
}
