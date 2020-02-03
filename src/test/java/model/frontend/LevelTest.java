package model.frontend;

import model.entity.Block;
import model.entity.Board;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LevelTest {
    private transient PlayGame game;
    private transient Level level;
    private transient MapParser mapparser;
    private transient Block[][] block;
    private transient Board board;

    /**
     * Setting up mockobjects and the class to test before each test.
     */
    @BeforeEach
    void setUp() {
        try {
            mapparser = new MapParser("/testingPath.txt");
        } catch (Exception e) {
            e.printStackTrace();
        }
        level = this.mapparser.getLevel();
        board = level.getBoard();
    }
    @Test
    void getBoard() {
     assertEquals(level.getBoard(), board);
    }
    @Test
    void blockMeasure()
    {
        assertEquals(19, level.blockWidth);
        assertEquals(19, level.blockHeight);
    }

    @Test
    void render() {
        assertEquals(4, board.getHeight());
        assertEquals(30, board.getWidth());

     }

     @Test
     void levelOccupants()
     {
         assertEquals(1, level.ghosts.size());
         assertFalse(level.pellets.size() == 20);
     }

    @Test
    void setBonus() {
        boolean bonusfalse = false;
        level.setBonus(bonusfalse);
        assertEquals(false, level.getBonus());
    }

    @Test
    void setBonusType() {
        String bonustype = "PelletChase";
        level.setBonusType(bonustype);
        assertEquals("PelletChase", level.getBonusType());
    }

}