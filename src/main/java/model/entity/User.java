package model.entity;

import java.util.Objects;

/**
 * This class creates a User object.
 */
public class User {

    private int userId;
    private String username;
    private Stats stats;
    // password storage is temporary
    private String hashedPassword;

    /**
     * Constructor.
     * @param username The username for the user
     */
    public User(String username) {

        this.username = username;

    }

    /**
     * Getter for user ID.
     * @return The user's ID
     */
    public int getUserId() {
        return userId;
    }

    /**
     * Setter for user ID.
     * @param id The new id for the user
     */
    public void setUserId(int id) {
        this.userId = id;
    }

    /**
     * Getter for the username.
     * @return The user's username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Setter for the username.
     * @param username  The username for the user
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Getter for the user's Stats.
     * @return The user's Stats
     */
    public Stats getStats() {
        return stats;
    }

    /**
     * Setter for the user's Stats.
     * @param stats The stats object that should be linked to this user
     */
    public void setStats(Stats stats) {
        this.stats = stats;
    }

    /**
     * Getter for the user's hashed password.
     * @return The user's hashed password
     */
    public String getHashedPassword() {
        return hashedPassword;
    }

    /**
     * Setter for the user's hashed password.
     * @param hashedPassword The password that should be linked to this user
     */
    public void setHashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
    }

    /**
     * Checks for equality on user ID.
     * @param o The object to this compare to
     * @return True if it is the same user
     *          False otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        User user = (User) o;
        return userId == user.userId;
    }

    /**
     * Hashes the user's username and ID.
     * @return The hashed username and ID
     */
    @Override
    public int hashCode() {
        return Objects.hash(userId, username);
    }
}
