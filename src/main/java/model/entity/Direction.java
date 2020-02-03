package model.entity;

/**
 * The directions in which units Ghosts and Pac-man can move on the Board.
 */
public enum Direction {

    /**
     * When unit moves up.
     */
    UP(0, -1),

    /**
     * When unit moves down.
     */
    DOWN(0, 1),

    /**
     * When unit moves left.
     */
    LEFT(-1, 0),

    /**
     * When unit moves right.
     */
    RIGHT(1,0);

    private final int difX;
    private final int difY;

    Direction(int difX, int difY) {
        this.difX = difX;
        this.difY = difY;
    }

    /**
     * Getter for the change in x.
     * @return the change in x when moving left or right.
     */
    public int getDifX() {
        return difX;
    }

    /**
     * Getter for the change in y.
     * @return the change in y when moving up or down.
     */
    public int getDifY() {
        return difY;
    }
}
