package model.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Objects;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class LeaderboardTest {

    transient Leaderboard leaderboard1;
    transient Leaderboard leaderboard2;
    transient Leaderboard leaderboard3;
    transient User user1;
    transient User user2;
    transient User user3;

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

        leaderboard1 = new Leaderboard(user1.getUsername());
        leaderboard1.setPoints(500);
        leaderboard2 = new Leaderboard(user2.getUsername());
        leaderboard2.setPoints(200);
        leaderboard3 = new Leaderboard(user1.getUsername());
        leaderboard3.setPoints(500);

    }

    @Test
    void testConstructor() {
        assertNotNull(leaderboard1);
    }

    @Test
    void testGetUsername() {
        assertEquals(leaderboard1.getUsername(), "CaptainMarvel");
    }

    @Test
    void testSetUsername() {

        String username = "ScarletWitch";
        leaderboard1.setUsername(username);
        assertEquals(username, leaderboard1.getUsername());
    }

    @Test
    void testGetPoints() {
        assertEquals(500, leaderboard1.getPoints());
    }

    @Test
    void testSetPoints() {

        int points = 5467;
        leaderboard1.setPoints(points);
        assertEquals(points, leaderboard1.getPoints());

    }

    @Test
    void testEqualsTrue() {
        assertEquals(leaderboard1, leaderboard3);
    }

    @Test
    void testEqualsTrueSameObject() {
        assertEquals(leaderboard1, leaderboard1);
    }

    @Test
    void testEqualsFalseOnUsername() {
        assertNotEquals(leaderboard1, leaderboard2);
    }

    @Test
    void testEqualsFalseOnPoints() {
        leaderboard3.setPoints(200);
        assertNotEquals(leaderboard1, leaderboard3);
    }

    @Test
    void testEqualsFalseNull() {
        assertNotEquals(leaderboard1, null);
    }

    @Test
    void testEqualsFalseDifferentClass() {
        assertNotEquals(leaderboard1, user1);
    }

    @Test
    void testHashCode() {
        int hash = Objects.hash(leaderboard1.getUsername(), leaderboard1.getPoints());
        assertEquals(hash, leaderboard1.hashCode());
    }

}
