package model.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Objects;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class StatsTest {

    private transient Stats stats1;
    private transient Stats stats2;
    private transient Stats stats3;
    private transient User user1;

    @BeforeEach
    void setupTestEnvironment() {

        stats1 = new Stats(151515);
        stats1.setBlocksWalked(890);
        stats1.setGamesPlayed(6);
        stats1.setGamesWon(6);
        stats1.setMaxLevel(4);
        stats1.setPelletsEaten(235);
        stats1.setTotalPoints(150);

        stats2 = new Stats(252525);
        stats2.setBlocksWalked(890);
        stats2.setGamesPlayed(6);
        stats2.setGamesWon(5);
        stats2.setMaxLevel(4);
        stats2.setPelletsEaten(235);
        stats2.setTotalPoints(150);

        stats3 = new Stats(151515);
        stats3.setBlocksWalked(890);
        stats3.setGamesPlayed(6);
        stats3.setGamesWon(6);
        stats3.setMaxLevel(4);
        stats3.setPelletsEaten(235);
        stats3.setTotalPoints(150);

        user1 = new User("CaptainMarvel");

    }

    @Test
    void testConstructor() {
        assertNotNull(stats1);
    }

    @Test
    void testGetMaxLevel() {
        assertTrue(stats1.getMaxLevel() == 4);
    }

    @Test
    void testSetMaxLevel() {

        int level = 5;
        stats1.setMaxLevel(level);
        assertTrue(stats1.getMaxLevel() == level);

    }

    @Test
    void testGetTotalPoints() {
        assertTrue(stats1.getTotalPoints() == 150);
    }

    @Test
    void testSetTotalPoints() {
        int points = 200;
        stats1.setTotalPoints(points);
        assertTrue(stats1.getTotalPoints() == points);
    }

    @Test
    void testGetBlocksWalked() {
        assertTrue(stats1.getBlocksWalked() == 890);
    }

    @Test
    void testSetBlocksWalked() {
        int blocks = 900;
        stats1.setBlocksWalked(blocks);
        assertTrue(stats1.getBlocksWalked() == blocks);
    }

    @Test
    void testGetPelletsEaten() {
        assertTrue(stats1.getPelletsEaten() == 235);
    }

    @Test
    void testSetPelletsEaten() {
        int pellets = 300;
        stats1.setPelletsEaten(pellets);
        assertTrue(stats1.getPelletsEaten() == pellets);
    }

    @Test
    void testGetGamesPlayed() {
        assertTrue(stats1.getGamesPlayed() == 6);
    }

    @Test
    void testSetGamesPlayed() {
        int games = 8;
        stats1.setGamesPlayed(games);
        assertTrue(stats1.getGamesPlayed() == games);
    }

    @Test
    void testGetGamesWon() {
        assertTrue(stats1.getGamesWon() == 6);
    }

    @Test
    void testSetGamesWon() {
        int games = 7;
        stats1.setGamesWon(games);
        assertTrue(stats1.getGamesWon() == games);
    }

    @Test
    void testGetUserId() {
        assertTrue(stats1.getUserId() == 151515);
    }

    @Test
    void testSetUserId() {
        int id = 353535;
        stats1.setUserId(id);
        assertTrue(stats1.getUserId() == id);
    }

    @Test
    void testEqualsTrue() {
        assertEquals(stats1, stats3);
    }

    @Test
    void testEqualsTrueSameObject() {
        assertEquals(stats1, stats1);
    }

    @Test
    void testEqualsFalse() {
        assertNotEquals(stats1, stats2);
    }

    @Test
    void testEqualsFalseNull() {
        assertNotEquals(stats1, null);
    }

    @Test
    void testEqualsFalseDifferentClass() {
        assertNotEquals(stats1, user1);
    }

    @Test
    void testEqualsFalseDifferentUserId() {

        stats3.setUserId(12);

        assertNotEquals(stats1, stats3);
    }

    @Test
    void testHashCode() {
        int hash = Objects.hash(stats1.getTotalPoints(), stats1.getGamesWon(), stats1.getUserId());
        assertTrue(stats1.hashCode() == hash);
    }

}
