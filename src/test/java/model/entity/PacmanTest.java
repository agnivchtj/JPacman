package model.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import model.frontend.Level;

import org.junit.jupiter.api.Test;


/**
 * Test class for Pacman.
 */

@SuppressWarnings("PMD.DataflowAnomalyAnalysis")
class PacmanTest {

    /**
     * Test case for collision with empty block.
     */
    @Test
    void collisionEmpty() throws Exception {
        Block pacBlock = new Block();
        Block emptyBlock = new Block();
        Block[][] block = new Block[2][2];
        block[0][0] = pacBlock;
        block[1][0] = emptyBlock;
        Board board = new Board(block);
        Level level = new Level(board, null, null);
        Pacman pacman = new Pacman(0,0);
        pacBlock.addOccupant(pacman);
        pacman.level = level;
        pacman.setWalking(true);
        pacman.setDirection(Direction.RIGHT);
        level.getBoard().setPacmanBlock(pacBlock);
        pacman.tick();
        pacman.collision(1, 0,pacman.level);
        assertEquals(level.getBoard().getPacmanBlock(), emptyBlock);
    }

    /**
     * Test case for collision with a ghost occupant.
     */
    @Test
    void collisionGhost() throws Exception {
        Block pacBlock = new Block();
        Block ghostBlock = new Block();
        Block[][] block = new Block[2][2];
        block[0][0] = pacBlock;
        block[0][1] = ghostBlock;
        Board board = new Board(block);
        Level level = new Level(board, null, null);
        Pacman pacman = new Pacman(0,0);
        Ghost ghost = new Mary(0,1);
        pacBlock.addOccupant(pacman);
        ghostBlock.addOccupant(ghost);
        pacman.level = level;
        pacman.setWalking(true);
        pacman.setDirection(Direction.UP);
        level.getBoard().setPacmanBlock(pacBlock);
        pacman.collision(0, 1,pacman.level);
        assertEquals(pacman.getState(), State.LOST);
    }

    /**
     * Test case for collision with a Wall occupant.
     */
    @Test
    void collisionWall() throws Exception {
        Block pacBlock = new Block();
        Block wallBlock = new Block();
        Block[][] block = new Block[2][2];
        block[0][0] = pacBlock;
        block[0][1] = wallBlock;
        Board board = new Board(block);
        Level level = new Level(board, null, null);
        Pacman pacman = new Pacman(0,0);
        Wall wall = new Wall();
        pacBlock.addOccupant(pacman);
        wallBlock.addOccupant(wall);
        pacman.level = level;
        pacman.setWalking(true);
        pacman.setDirection(Direction.UP);
        level.getBoard().setPacmanBlock(pacBlock);
        pacman.collision(0, 1,pacman.level);
        assertEquals(level.getBoard().getPacmanBlock(), pacBlock);
        assertEquals(pacman.getState(), State.RUNNING);
    }

    /**
     * Test case for collision with a Pellet occupant.
     */
    @Test
    void collisionPellet() throws Exception {
        Block pacBlock = new Block();
        Block pelletBlock = new Block();
        Block[][] block = new Block[2][2];
        block[0][0] = pacBlock;
        block[0][1] = pelletBlock;
        Board board = new Board(block);
        List<Pellet> pellets = new ArrayList<>();
        pellets.add(new Pellet());
        pellets.add(new Pellet());
        Level level = new Level(board, pellets, null);
        Pacman pacman = new Pacman(0,0);
        Pellet pellet = new Pellet();
        pacBlock.addOccupant(pacman);
        pelletBlock.addOccupant(pellet);
        pacman.level = level;
        pacman.setWalking(true);
        pacman.setDirection(Direction.UP);
        level.getBoard().setPacmanBlock(pacBlock);
        pacman.collision(0, 1,pacman.level);
        assertEquals(level.getBoard().getPacmanBlock(), pelletBlock);
        assertEquals(pacman.getState(), State.RUNNING);
    }

    /**
     * Test case for collision with the last remaining Pellet.
     */
    @Test
    void collisionLastPellet() throws Exception {
        Block pacBlock = new Block();
        Block pelletBlock = new Block();
        Block[][] block = new Block[2][2];
        block[0][0] = pacBlock;
        block[0][1] = pelletBlock;
        Board board = new Board(block);
        List<Pellet> pellets = new ArrayList<>();
        pellets.add(new Pellet());
        Level level = new Level(board, pellets, null);
        Pacman pacman = new Pacman(0, 0);
        Pellet pellet = new Pellet();
        pacBlock.addOccupant(pacman);
        pelletBlock.addOccupant(pellet);
        pacman.level = level;
        pacman.setWalking(true);
        pacman.setDirection(Direction.UP);
        level.getBoard().setPacmanBlock(pacBlock);
        pacman.collision(0, 1, pacman.level);
        assertEquals(level.getBoard().getPacmanBlock(), pelletBlock);
        assertEquals(pacman.getState(), State.WON);
    }

    /**
     * Test case for collision with Bonus cherry.
     */
    @Test
    void collisionBonusCherry() throws Exception {
        Block pacBlock = new Block();
        Block bonusBlock = new Block();
        Block wallBlock = new Block();
        Block[][] block = new Block[3][3];
        block[0][2] = pacBlock;
        block[0][1] = bonusBlock;
        block[0][0] = wallBlock;
        Board board = new Board(block);
        List<Pellet> pellets = new ArrayList<>();
        pellets.add(new Pellet());
        Level level = new Level(board, pellets, null);
        Pacman pacman = new Pacman(0, 2);
        Bonus bonus = new Bonus("cherry");
        pacBlock.addOccupant(pacman);
        bonusBlock.addOccupant(bonus);
        wallBlock.addOccupant(new Wall());
        pacman.level = level;
        pacman.setWalking(true);
        pacman.setDirection(Direction.DOWN);
        level.getBoard().setPacmanBlock(pacBlock);
        pacman.tick();
        pacman.collision(0, 1, pacman.level);
        pacman.tick();
        pacman.collision(0,0, pacman.level);
        assertEquals(level.getBoard().getPacmanBlock(), wallBlock);
        assertEquals(pacman.isBonus(), true);
    }

    /**
     * Test case for collision with Bonus strawberry.
     */
    @Test
    void collisionBonusStrawberry() throws Exception {
        Block pacBlock = new Block();
        Block bonusBlock = new Block();
        Block ghostBlock = new Block();
        Block[][] block = new Block[3][3];
        block[0][2] = pacBlock;
        block[0][1] = bonusBlock;
        block[0][0] = ghostBlock;
        Board board = new Board(block);
        List<Pellet> pellets = new ArrayList<>();
        pellets.add(new Pellet());
        Level level = new Level(board, pellets, null);
        Pacman pacman = new Pacman(0, 2);
        Bonus bonus = new Bonus("strawberry");
        pacBlock.addOccupant(pacman);
        bonusBlock.addOccupant(bonus);
        ghostBlock.addOccupant(new Mary(0,0));
        pacman.level = level;
        pacman.setWalking(true);
        pacman.setDirection(Direction.DOWN);
        level.getBoard().setPacmanBlock(pacBlock);
        pacman.tick();
        pacman.collision(0, 1, pacman.level);
        pacman.tick();
        pacman.collision(0,0, pacman.level);
        assertEquals(level.getBoard().getPacmanBlock(), ghostBlock);
        assertEquals(pacman.isBonus(), true);
    }

    /**
     * Test case for collision with Bonus strawberry, at the end of bonus time.
     */
    @Test
    void collisionBonusEnding() throws Exception {
        Block pacBlock = new Block();
        Block bonusBlock = new Block();
        Block ghostBlock = new Block();
        Block[][] block = new Block[3][3];
        block[0][2] = pacBlock;
        block[0][1] = bonusBlock;
        block[0][0] = ghostBlock;
        Board board = new Board(block);
        Level level = new Level(board, null, null);
        Pacman pacman = new Pacman(0, 2);
        Bonus bonus = new Bonus("strawberry");
        pacBlock.addOccupant(pacman);
        bonusBlock.addOccupant(bonus);
        pacman.level = level;
        pacman.setWalking(true);
        pacman.setDirection(Direction.DOWN);
        level.getBoard().setPacmanBlock(pacBlock);
        pacman.tick();
        pacman.collision(0, 1, pacman.level);
        pacman.setBonusCounter(800);
        pacman.tick();
        pacman.collision(0,0, pacman.level);
        assertEquals(pacman.isBonus(), false);
    }

}