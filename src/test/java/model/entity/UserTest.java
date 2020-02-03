package model.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Objects;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class UserTest {

    private transient User user1;
    private transient User user2;
    private transient User user3;
    private transient Stats stats1;

    @BeforeEach
    void setupTestEnvironment() {

        user1 = new User("CaptainMarvel");
        user1.setUserId(151515);
        user1.setHashedPassword("maverick");
        user2 = new User("WonderWoman");
        user2.setUserId(161616);
        user2.setHashedPassword("themyscira");
        user3 = new User("CaptainMarvel");
        user3.setUserId(151515);
        user3.setHashedPassword("maverick");
        stats1 = new Stats(user1.getUserId());
        stats1.setBlocksWalked(890);
        stats1.setGamesPlayed(6);
        stats1.setGamesWon(5);
        stats1.setMaxLevel(4);
        stats1.setPelletsEaten(235);
        stats1.setTotalPoints(150);
        user1.setStats(stats1);

    }

    @Test
    void testConstructor() {
        assertNotNull(user1);
    }

    @Test
    void testGetUsername() {
        assertEquals(user1.getUsername(), "CaptainMarvel");
    }

    @Test
    void testSetUsername() {

        String username = "ScarletWitch";
        user1.setUsername(username);
        assertEquals(username, user1.getUsername());
    }

    @Test
    void testGetUserId() {
        assertEquals(user1.getUserId(), 151515);
    }

    @Test
    void testSetUserId() {

        int userId = 5467;
        user1.setUserId(userId);
        assertTrue(user1.getUserId() == userId);

    }

    @Test
    void testGetStats() {

        assertEquals(stats1, user1.getStats());

    }

    @Test
    void testSetStats() {

        Stats stats2 = new Stats(user1.getUserId());
        user1.setStats(stats2);

        assertNotEquals(stats1, user1.getStats());

    }

    @Test
    void testGetHashedPassword() {
        assertEquals("maverick", user1.getHashedPassword());
    }

    @Test
    void testSetHashedPassword() {

        String password = "themyscira";
        user1.setHashedPassword(password);
        assertEquals(password, user1.getHashedPassword());
    }

    @Test
    void testEqualsTrue() {
        assertEquals(user1, user3);
    }

    @Test
    void testEqualsTrueSameObject() {
        assertEquals(user1, user1);
    }

    @Test
    void testEqualsFalse() {
        assertNotEquals(user1, user2);
    }

    @Test
    void testEqualsFalseNull() {
        assertNotEquals(user1, null);
    }

    @Test
    void testEqualsFalseDifferentClass() {
        assertNotEquals(user1, stats1);
    }

    @Test
    void testHashCode() {
        int hash = Objects.hash(user1.getUserId(), user1.getUsername());
        assertTrue(hash == user1.hashCode());
    }

}
