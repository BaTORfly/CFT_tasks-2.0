package shapes;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

import ru.batorfly.factory.ShapeType;
import ru.batorfly.shapes.Circle;

public class CircleTest {

    // === tests for creating ===

    @Test
    @DisplayName("Should create circle with valid radius")
    void shouldCreateCircleWithValidRadius() {
        // given
        var radius = 5.0;

        // when
        var circle = new Circle(radius);

        // then
        assertThat(circle).isNotNull();
    }

    @Test
    @DisplayName("Should create circle from valid parameter string")
    void shouldCreateFromValidParameterString() {
        var circle = Circle.create("7.5");
        assertThat(circle.computeArea()).isCloseTo(Math.PI * 7.5 * 7.5, within(1e-10));
    }

    @ParameterizedTest
    @ValueSource(strings = {"0.5", "0", "-1", "abc", "2 3"})
    @DisplayName("Should throw IllegalArgumentException for invalid input string")
    void shouldThrowForInvalidInputString(String input) {
        assertThrows(IllegalArgumentException.class, () -> Circle.create(input));
    }

    // === tests for calculating ===

    @Test
    @DisplayName("Should compute correct perimeter")
    void shouldComputeCorrectPerimeter() {
        var circle = new Circle(3.0);
        var expectedPerimeter = 2 * Math.PI * 3.0;
        assertThat(circle.computePerimeter()).isCloseTo(expectedPerimeter, within(1e-10));
    }

    @Test
    @DisplayName("Should compute correct area")
    void shouldComputeCorrectArea() {
        var circle = new Circle(4.0);
        var expectedArea = Math.PI * 4.0 * 4.0;
        assertThat(circle.computeArea()).isCloseTo(expectedArea, within(1e-10));
    }

    // === test for shape type ===

    @Test
    @DisplayName("Should return correct shape type")
    void shouldReturnCorrectShapeType() {
        var circle = new Circle(2.0);
        assertThat(circle.getType()).isEqualTo(ShapeType.CIRCLE);
    }

    // === test for buildShapesDataStr() ===

    @Test
    @DisplayName("Should include radius and diameter in output string")
    void shouldIncludeRadiusAndDiameterInOutput() {
        var circle = new Circle(10.0);
        var output = circle.buildShapesDataStr().toString();

        assertThat(output)
                .contains("Shape type: CIRCLE")
                .contains("Area:")
                .contains("Perimeter:")
                .contains("radius: 10")
                .contains("diameter: 20")
                .contains("см") // UNIT
                .contains("см²"); // SQ_UNIT
    }

    @Test
    @DisplayName("Should work correctly with minimal allowed radius (1.0)")
    void shouldWorkWithMinimalRadius() {
        var circle = new Circle(1.0);
        assertThat(circle.computePerimeter()).isGreaterThan(0);
        assertThat(circle.computeArea()).isGreaterThan(0);
    }
}
