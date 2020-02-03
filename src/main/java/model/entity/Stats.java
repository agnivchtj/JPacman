package model.entity;

import java.util.Objects;

/**
 * This class creates a Stats object, linked to a user ID.
 */
public class Stats {
    private int maxLevel;
    private int totalPoints;
    private int blocksWalked;
    private int pelletsEaten;
    private int gamesPlayed;
    private int gamesWon;
    private int userId;
    private int maxScore;

    /**
     * Constructor.
     * @param userId The user ID linked to these stats.
     */
    public Stats(int userId) {
        this.userId = userId;
    }

    /**
     * Getter for the maximal score achieved by the user.
     * @return The user's maxScore
     */
    public int getMaxScore() {
        return maxScore;
    }

    /**
     * Setter for the maximal score achieved by the user.
     * @param maxScore The maxScore for the user
     */
    public void setMaxScore(int maxScore) {
        this.maxScore = maxScore;
    }

    /**
     * Getter for the maximal level achieved by the user.
     * @return The user's maxLevel
     */
    public int getMaxLevel() {
        return maxLevel;
    }

    /**
     * Setter for the maximal level achieved by the user.
     * @param maxLevel The maxLevel for the user
     */
    public void setMaxLevel(int maxLevel) {
        this.maxLevel = maxLevel;
    }

    /**
     * Getter for the total points scored by the user.
     * @return The user's totalPoints
     */
    public int getTotalPoints() {
        return totalPoints;
    }

    /**
     * Setter for the total points scored by the user.
     * @param totalPoints The totalPoints for the user
     */
    public void setTotalPoints(int totalPoints) {
        this.totalPoints = totalPoints;
    }

    /**
     * Getter for the total number of blocks the user has walked.
     * @return The user's blocksWalked
     */
    public int getBlocksWalked() {
        return blocksWalked;
    }

    /**
     * Setter for the total number of blocks the user has walked.
     * @param blocksWalked The blocksWalked for the user
     */
    public void setBlocksWalked(int blocksWalked) {
        this.blocksWalked = blocksWalked;
    }

    /**
     * Getter for the total number of pellets eaten by the user.
     * @return The user's pelletsEaten
     */
    public int getPelletsEaten() {
        return pelletsEaten;
    }

    /**
     * Setter for the total number of pellets eaten by the user.
     * @param pelletsEaten The pelletsEaten by the user.
     */
    public void setPelletsEaten(int pelletsEaten) {
        this.pelletsEaten = pelletsEaten;
    }

    /**
     * Getter for the total number of games played by the user.
     * @return The user's gamesPlayed
     */
    public int getGamesPlayed() {
        return gamesPlayed;
    }

    /**
     * Setter for the total number of games played by the user.
     * @param gamesPlayed The gamesPlayed by the user
     */
    public void setGamesPlayed(int gamesPlayed) {
        this.gamesPlayed = gamesPlayed;
    }

    /**
     * Getter for total number of games won by the user.
     * @return The user's gamesWon
     */
    public int getGamesWon() {
        return gamesWon;
    }

    /**
     * Setter for total number of games won by the user.
     * @param gamesWon The gamesWon by the user
     */
    public void setGamesWon(int gamesWon) {
        this.gamesWon = gamesWon;
    }

    /**
     * Getter for the user ID.
     * @return The user's ID
     */
    public int getUserId() {
        return userId;
    }

    /**
     * Setter for the user ID.
     * @param userId The ID for the user
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * Compares two another object to this for equality.
     * @param o The other object to be compared
     * @return True if they are the same object
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
        Stats stats = (Stats) o;
        return getTotalPoints() == stats.getTotalPoints()
                && getGamesWon() == stats.getGamesWon()
                && getUserId() == stats.getUserId();
    }

    /**
     * Hashes the stats' totalPoints, gamesWon, and userId.
     * @return The stats' hashed fields
     */
    @Override
    public int hashCode() {
        return Objects.hash(getTotalPoints(), getGamesWon(), getUserId());
    }
}
