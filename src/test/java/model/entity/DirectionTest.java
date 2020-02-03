package model.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

/**
 * Test class for the enum Direction.
 */
class DirectionTest {

    /**
     * Do we get the correct difference in x coordinate when moving up, down, left or right?.
     * @param expect - expected difX
     * @param direction - name of the direction
     */
    @ParameterizedTest
    @CsvSource({"LEFT, -1", "RIGHT, 1", "UP, 0", "DOWN, 0"})
    void testDifX(String direction, int expect) {
        Direction direct = Direction.valueOf(direction);
        assertEquals(expect, direct.getDifX());
    }

    /**
     * Do we get the correct difference in y coordinate when moving up, down, left or right?.
     * @param expect - expected difY
     * @param direction - name of the direction
     */
    @ParameterizedTest
    @CsvSource({"UP, -1", "DOWN, 1", "LEFT, 0", "RIGHT, 0"})
    void testDifY(String direction, int expect) {
        Direction direct = Direction.valueOf(direction);
        assertEquals(expect, direct.getDifY());
    }

}
