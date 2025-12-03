package shapes;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import ru.batorfly.shapes.Shape;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ShapeTest {
    @Test
    void shouldParseValidDoubleLine() {
        double[] result = Shape.parseDoubles("3.5 4.2 5.1", 3);
        assertThat(result).containsExactly(3.5, 4.2, 5.1);
    }

    @Test
    void shouldThrowWhenNotEnoughNumbers() {
        assertThrows(
                IllegalArgumentException.class,
                () -> Shape.parseDoubles("3 4", 3)
        );
    }

    @Test
    void shouldThrowWhenTooManyNumbers() {
        assertThrows(
                IllegalArgumentException.class,
                () -> Shape.parseDoubles("1 2 3 4", 3)
        );
    }

    @ParameterizedTest
    @ValueSource(strings = {"abc 2 3", "1 x 3", "1 2 !"})
    void shouldThrowWhenNonNumericInput(String input) {
        assertThrows(
                IllegalArgumentException.class,
                () -> Shape.parseDoubles(input, 3)
        );
    }

    @ParameterizedTest
    @ValueSource(doubles = {0.5, 0.0, -1.0})
    void shouldThrowWhenParameterLessThanOne(double invalidValue) {
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> Shape.parseDoubles(invalidValue + " 2 3", 3)
        );
        assertThat(ex.getMessage()).contains("All the parameters must be >=1");
    }

    @Test
    void shouldHandleExtraWhitespace() {
        double[] result = Shape.parseDoubles("  3   4   5  ", 3);
        assertThat(result).containsExactly(3, 4, 5);
    }
}
