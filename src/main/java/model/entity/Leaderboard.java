package model.entity;

import java.util.Objects;

/**
 * This class creates a Leaderboard entry object.
 */
public class Leaderboard {

    private String username;
    private int points;

    /**
     * Constructor.
     * @param username The id for the user
     */
    public Leaderboard(String username) {

        this.username = username;

    }

    /**
     * Getter for the username.
     * @return The user's username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Setter for username.
     * @param username The user's new username.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Getter for points.
     * @return The user's points.
     */
    public int getPoints() {
        return points;
    }

    /**
     * Setter for points.
     * @param points The user's new points
     */
    public void setPoints(int points) {
        this.points = points;
    }

    /**
     * Checks for equality on username and points.
     * @param o The object to this compare to
     * @return true if it is the same Leaderboard entry
     *         false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Leaderboard that = (Leaderboard) o;
        return getUsername().equals(that.getUsername())
                && getPoints() == that.getPoints();
    }

    /**
     * Hashes on username and points.
     * @return The hashed username and points.
     */
    @Override
    public int hashCode() {
        return Objects.hash(getUsername(), getPoints());
    }
}
