package shapes;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

import ru.batorfly.exceptions.IllegalTriangleSidesException;
import ru.batorfly.factory.ShapeType;
import ru.batorfly.shapes.Triangle;

public class TriangleTest {
    // === tests for creating triangle ===
    @Test
    @DisplayName("Should create triangle with valid sides")
    void shouldCreateTriangleWithValidSides() {
        double a = 3, b = 4, c = 5;

        var triangle = new Triangle(a, b, c);

        assertThat(triangle).isNotNull();

        assertThat(triangle.computePerimeter()).isEqualTo(12.0);
    }

    @ParameterizedTest
    @MethodSource("provideInvalidTriangleSides")
    @DisplayName("Should throw IllegalTriangleSidesException for invalid sides")
    void shouldThrowExceptionForInvalidSides(double a, double b, double c) {
        var exception = assertThrows(
                IllegalTriangleSidesException.class,
                () -> new Triangle(a, b, c)
        );

        assertThat(exception.getSide1()).isEqualTo(a);
        assertThat(exception.getSide2()).isEqualTo(b);
        assertThat(exception.getSide3()).isEqualTo(c);
        assertThat(exception.getMessage())
                .contains(String.format("%.2f", a))
                .contains(String.format("%.2f", b))
                .contains(String.format("%.2f", c));
    }

    static Stream<Arguments> provideInvalidTriangleSides() {
        return Stream.of(
                Arguments.of(1.0, 2.0, 5.0), // 1 + 2 < 5
                Arguments.of(10.0, 1.0, 1.0), // 10 > 1 + 1
                Arguments.of(0.0, 5.0, 5.0),  // 0 is unacceptable
                Arguments.of(-1.0, 2.0, 3.0)  // negative side
        );
    }

    // === tests for calculation ===

    @Test
    @DisplayName("Should compute correct perimeter")
    void shouldComputeCorrectPerimeter() {
        var t = new Triangle(3, 4, 5);
        assertThat(t.computePerimeter()).isEqualTo(12.0);
    }

    @Test
    @DisplayName("Should compute correct area (3-4-5 triangle)")
    void shouldComputeCorrectArea() {
        var t = new Triangle(3, 4, 5);
        // area = 6
        assertThat(t.computeArea()).isCloseTo(6.0, within(1e-10));
    }

    @Test
    @DisplayName("Should compute correct angles for equilateral triangle")
    void shouldComputeCorrectAnglesForEquilateralTriangle() {
        double angle = Triangle.findAngleOppositeSide(5, 5, 5);
        assertThat(angle).isCloseTo(60.0, within(1e-10));
    }

    @Test
    @DisplayName("Should compute correct angle for right triangle (opposite hypotenuse)")
    void shouldCompute90DegreeAngleForHypotenuse() {
        // sides 3, 4, 5 -> the angle opposite the side 5 = 90°
        double angle = Triangle.findAngleOppositeSide(5, 3, 4);
        assertThat(angle).isCloseTo(90.0, within(1e-10));
    }

    // === test for shape type ===

    @Test
    @DisplayName("Should return correct shape type")
    void shouldReturnCorrectShapeType() {
        var t = new Triangle(3, 4, 5);
        assertThat(t.getType()).isEqualTo(ShapeType.TRIANGLE);
    }

    // === test for create() method ===

    @Test
    @DisplayName("Should create triangle from valid parameter string")
    void shouldCreateFromValidParameterString() {
        var t = Triangle.create("3 4 5");
        assertThat(t.computePerimeter()).isEqualTo(12.0);
    }

    @Test
    @DisplayName("Should throw exception when creating from invalid string (not enough numbers)")
    void shouldThrowWhenNotEnoughNumbers() {
        assertThrows(IllegalArgumentException.class, () -> Triangle.create("3 4"));
    }

    @Test
    @DisplayName("Should throw exception when creating from invalid triangle sides")
    void shouldThrowWhenCreatingInvalidTriangleFromStr() {
        assertThrows(IllegalTriangleSidesException.class, () -> Triangle.create("1 2 5"));
    }

    // === test for buildShapesDataStr() (partially completed) ===

    @Test
    @DisplayName("Should include side and angle info in buildShapesDataStr")
    void shouldIncludeSideAndAngleInOutput() {
        var t = new Triangle(3, 4, 5);
        var output = t.buildShapesDataStr().toString();

        assertThat(output).contains("Side1 = 3");
        assertThat(output).contains("Angle opposite the side1");
        assertThat(output).contains("Side2 = 4");
        assertThat(output).contains("Side3 = 5");
        assertThat(output).contains("°"); // degree symbol
    }
}
