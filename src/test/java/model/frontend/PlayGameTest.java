package model.frontend;

import model.entity.Direction;
import model.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.event.KeyEvent;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


class PlayGameTest {

    private transient PlayGame game;
    private transient User user = mock(User.class);

    /**
     * Setting up mock objects and the class to test before each test.
     */
    @BeforeEach
    void setUp() {
        game = new PlayGame(user);
        //game.play();
        //If game.play() is added the coverage of this Test class is a lot higher. This commented
        //because game.play() will cause an error in the pipeline of gitlab since it can't make the
        //game screen.
    }

    /**
     * Testing game.start when game is in progress.
     */
    @Test
    void startTest() {
        assertFalse(game.isRunning());
        game.start();
        assertTrue(game.isRunning());
    }

    /**
     * Testing game.start when it has already been started once.
     */
    @Test
    void startTwiceTest() {
        game.start();
        assertTrue(game.isRunning());
        game.start();
        assertTrue(game.isRunning());
    }

    /**
     * Testing game.stop after game has started.
     */
    @Test
    void stopTest() {
        game.start();
        assertTrue(game.isRunning());
        game.stop();
        assertFalse(game.isRunning());
    }

    /**
     * Testing key event when left arrow is pressed and released.
     */
    @Test
    void testKeyPressedLeft() {
        game.start();
        KeyEvent key = new KeyEvent(game, KeyEvent.KEY_PRESSED, System.currentTimeMillis(),
            0,  KeyEvent.VK_LEFT, 'Z');
        game.getKeyListeners()[0].keyPressed(key);
        assertTrue(game.getPlayer().getDirection() == Direction.LEFT);
    }

    /**
     * Testing key event when right arrow is pressed and released.
     */
    @Test
    void testKeyPressedRight() {
        game.start();
        KeyEvent key = new KeyEvent(game, KeyEvent.KEY_PRESSED, System.currentTimeMillis(),
            0,  KeyEvent.VK_RIGHT, 'Z');
        game.getKeyListeners()[0].keyPressed(key);
        assertTrue(game.getPlayer().getDirection() == Direction.RIGHT);
    }

    /**
     * Testing key event when up arrow is pressed and released.
     */
    @Test
    void testKeyPressedUp() {
        game.start();
        KeyEvent key = new KeyEvent(game, KeyEvent.KEY_PRESSED, System.currentTimeMillis(),
            0,  KeyEvent.VK_UP, 'Z');
        game.getKeyListeners()[0].keyPressed(key);
        assertTrue(game.getPlayer().getDirection() == Direction.UP);
    }

    /**
     * Testing key event when down arrow is pressed and released.
     */
    @Test
    void testKeyPressedDown() {
        game.start();
        KeyEvent key = new KeyEvent(game, KeyEvent.KEY_PRESSED, System.currentTimeMillis(),
            0,  KeyEvent.VK_DOWN, 'Z');
        game.getKeyListeners()[0].keyPressed(key);
        assertTrue(game.getPlayer().getDirection() == Direction.DOWN);
    }
}