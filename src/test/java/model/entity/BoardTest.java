package model.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for the Board class.
 */
@SuppressWarnings("PMD.DataflowAnomalyAnalysis")
class BoardTest {

    private transient Block[][] grid;
    private transient Block block;

    @BeforeEach
    void setUp() {
        grid = new Block[1][1];
        block = new Block();

    }

    /**
     * Test case for constructing a Board.
     * @throws Exception if block is not in grid.
     */
    @Test
    void testBoardConstructor() throws Exception {
        grid[0][0] = block;
        Board board = new Board(grid);
        assertEquals(block, board.getBlock(0,0));
    }

    /**
     * Test case for getting the height of the Board.
     */
    @Test
    void testBoardgetHeigt() {
        Block block = new Block();
        Block[][] grid = new Block[1][1];
        grid[0][0] = block;
        Board board = new Board(grid);
        assertEquals(1, board.getHeight());
    }

    /**
     * Test case for getting the width of the Board.
     */
    @Test
    void testBoardgetWidth() {
        Block block = new Block();
        Block[][] grid = new Block[1][1];
        grid[0][0] = block;
        Board board = new Board(grid);
        assertEquals(1, board.getWidth());
    }

    /**
     * Test case for getting a block in an empty board.
     */
    @Test
    void testEmptyBoard() {
        Block[][] grid = new Block[1][1];
        Board board = new Board(grid);
        assertThrows(Exception.class, () -> board.getBlock(0,0));
    }

    /**
     * Test case for getting a block that has x coordinate that is not on the board.
     */
    @Test
    void testCoordinatesNotOnBoardX() {
        Block[][] grid = new Block[1][1];
        Board board = new Board(grid);
        assertThrows(Exception.class, () -> board.getBlock(1,0));
    }

    /**
     * Test case for getting a block that has y coordinate that is not on the board.
     */
    @Test
    void testCoordinatesNotOnBoardY() {
        Block[][] grid = new Block[1][1];
        Board board = new Board(grid);
        assertThrows(Exception.class, () -> board.getBlock(0,1));
    }

    /**
     * Test case for setting and getting the grid of the board.
     */
    @Test
    void testSetGetGrid() {
        Block[][] grid = new Block[1][1];
        Block[][] grid2 = new Block[2][2];
        Board board = new Board(grid);
        board.setGrid(grid2);
        assertEquals(2, board.getGrid().length);
    }

    @Test
    void testGetX() {
        grid[0][0] = block;
        Board board = new Board(grid);
        assertEquals(board.getX(block), 0);
    }

    @Test
    void testGetY() {
        grid[0][0] = block;
        Board board = new Board(grid);
        assertEquals(board.getY(block), 0);
    }

    @Test
    void testGetXNotFound() {
        Board board = new Board(grid);
        assertEquals(board.getX(block), -1);
    }

    @Test
    void testGetYNotFound() {
        Board board = new Board(grid);
        assertEquals(board.getY(block), -1);
    }

    @Test
    void testSetPacMan() {
        Board board = new Board(grid);
        board.setPacmanBlock(block);
        assertTrue(board.getPacmanBlock() == block);
    }
}