package model.entity;

/**
 * The class Board is a matrix of x and y coordinates.
 */
@SuppressWarnings("PMD.AssignmentToNonFinalStatic")
public class Board {

    /**
     * 2-dimensional Array with in the first dimension the x coordinates and the second the y
     * coordinates.
     */
    private static Block[][] grid;

    private Block pacmanBlock;

    /**
     * Creates a board.
     * @param board - the array grid of the board.
     */
    public Board(Block[][] board) {
        grid = board;
    }

    /**
     * Getter for grid.
     * @return the grid of the board.
     */
    public Block[][] getGrid() {
        return this.grid;
    }

    /**
     * Setter for the grid of the Board.
     * @param grid a grid for the Board.
     */
    public void setGrid(Block[][] grid) {
        this.grid = grid;
    }

    public Block getPacmanBlock() {
        return this.pacmanBlock;
    }

    public void setPacmanBlock(Block block) {
        this.pacmanBlock = block;
    }

    /**
     * Get the width of the board (width is equal to the 1st dimension length).
     * @return the width of the board.
     */
    public static int getWidth() {
        return grid.length;
    }

    /**
     * Get the height of the board (height is equal to the 2nd dimension length).
     * @return the height of the board.
     */
    public static int getHeight() {
        return grid[0].length;
    }

    /**
     * Get the a block in the grid at coordinate (x,y).
     * @param x coordinate of x.
     * @param y coordinate of y.
     * @return the block at coordinate (x,y).
     */
    public Block getBlock(int x, int y) throws Exception {
        if (x >= getWidth() || y >= getHeight()) {
            throw new Exception("Coordinates are not inside the board");
        }
        Block block = grid[x][y];
        if (block == null) {
            throw new NullPointerException("NullPointerException at method getBlock");
        }
        return block;
    }

    public int getX(Block block) {
        for(int i = 0; i < getWidth(); i++){
            for(int j = 0; j < getHeight(); j++){
                if(grid[i][j] == block){
                    return i;
                }
            }
        }
        return -1;
    }

    public int getY(Block block) {
        for(int i = 0; i < getWidth(); i++){
            for(int j = 0; j < getHeight(); j++){
                if(grid[i][j] == block){
                    return j;
                }
            }
        }
        return -1;
    }
}
