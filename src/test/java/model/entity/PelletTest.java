package model.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Test class for the Pellet class.
 */
class PelletTest {

    /**
     * Test case for construction and method getValue().
     */
    @Test
    void testPellet() {
        Pellet pellet = new Pellet();
        assertEquals(10, pellet.getValue());
    }

}