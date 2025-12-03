package shapes;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

import ru.batorfly.factory.ShapeType;
import ru.batorfly.shapes.Rectangle;

public class RectangleTest {

    // === tests for creating ===

    @Test
    @DisplayName("Should create rectangle with valid length and width")
    void shouldCreateRectangleWithValidDimensions() {
        var rect = new Rectangle(4.0, 5.0);
        assertThat(rect).isNotNull();
        assertThat(rect.computeArea()).isEqualTo(20.0);
    }

    @Test
    @DisplayName("Should create rectangle from valid parameter string")
    void shouldCreateFromValidParameterString() {
        var rect = Rectangle.create("6 8");
        assertThat(rect.computePerimeter()).isEqualTo((6 + 8) * 2);
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "0.5 2",        // length < 1
            "3 -1",         // width < 1
            "0 5",          // zero not allowed
            "abc 4",        // non-numeric
            "5",            // not enough params
            "1 2 3",        // too many params
            "2.5"           // only one param
    })
    @DisplayName("Should throw IllegalArgumentException for invalid input string")
    void shouldThrowForInvalidInputString(String input) {
        assertThrows(IllegalArgumentException.class, () -> Rectangle.create(input));
    }

    // === Тесты на вычисления ===

    @Test
    @DisplayName("Should compute correct perimeter")
    void shouldComputeCorrectPerimeter() {
        var rect = new Rectangle(3.0, 7.0);
        assertThat(rect.computePerimeter()).isEqualTo(20.0);
    }

    @Test
    @DisplayName("Should compute correct area")
    void shouldComputeCorrectArea() {
        var rect = new Rectangle(4.5, 2.0);
        assertThat(rect.computeArea()).isEqualTo(9.0);
    }

    @Test
    @DisplayName("Should compute correct diagonal")
    void shouldComputeCorrectDiagonal() {
        var rect = new Rectangle(3.0, 4.0); // diagonal = 5
        assertThat(rect.computeDiagonal()).isCloseTo(5.0, within(1e-10));
    }

    // === test for shape type ===

    @Test
    @DisplayName("Should return correct shape type")
    void shouldReturnCorrectShapeType() {
        var rect = new Rectangle(2.0, 3.0);
        assertThat(rect.getType()).isEqualTo(ShapeType.RECTANGLE);
    }

    // === test for buildShapesDataStr() ===

    @Test
    @DisplayName("Should include diagonal, length and width in output string")
    void shouldIncludeDiagonalLengthWidthInOutput() {
        var rect = new Rectangle(3.0, 4.0);
        var output = rect.buildShapesDataStr().toString();

        assertThat(output)
                .contains("Shape type: RECTANGLE")
                .contains("Area:")
                .contains("Perimeter:")
                .contains("Diagonal: 5")
                .contains("Length: 3")
                .contains("Width: 4")
                .contains("см")    // UNIT
                .contains("см²");  // SQ_UNIT
    }

    // === test for square ===

    @Test
    @DisplayName("Should work correctly when length equals width (square)")
    void shouldWorkCorrectlyForSquare() {
        var square = new Rectangle(5.0, 5.0);
        assertThat(square.computeArea()).isEqualTo(25.0);
        assertThat(square.computePerimeter()).isEqualTo(20.0);
        assertThat(square.computeDiagonal()).isCloseTo(5 * Math.sqrt(2), within(1e-10));
    }

    // === test for min acceptable values ===

    @Test
    @DisplayName("Should work with minimal allowed dimensions (1.0, 1.0)")
    void shouldWorkWithMinimalDimensions() {
        var rect = new Rectangle(1.0, 1.0);
        assertThat(rect.computeArea()).isEqualTo(1.0);
        assertThat(rect.computePerimeter()).isEqualTo(4.0);
    }
}
