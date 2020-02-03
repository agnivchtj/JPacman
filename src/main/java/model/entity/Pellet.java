package model.entity;

/**
 * The class Pellet represents Pellets that Pac-man is able to eat.
 */
public class Pellet extends Occupant {
    private static final long serialVersionUID = 1L;

    /**
     * The value of a pellet in points.
     */
    private final int value;

    private transient int defaultValue = 10;

    /**
     * Constructor class for Pellet.
     */
    public Pellet() {
        this.value = defaultValue;
    }

    /**
     * Get the value of a Pellet.
     * @return the value of the Pellet.
     */
    public int getValue() {
        return value;
    }
}
