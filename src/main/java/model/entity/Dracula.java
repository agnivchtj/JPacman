package model.entity;

import model.frontend.Level;
import model.frontend.PlayGame;

import java.util.List;


public class Dracula extends Ghost {
    private static final long serialVersionUID = 1L;
    private transient Level level = PlayGame.level;
    private transient int x1;
    private transient int y1;


    public Dracula(int x, int y) {
        super(x, y);
        setBounds(x, y, 32, 32);
    }

    /**
     * Determining the next move of the ghost.
     * @return the direction to move in.
     * @throws Exception this is for any Exception raised.
     */
    public Direction nextMove() throws Exception {
        Block curr = level.getBoard().getBlock(this.x1, this.y1);
        Block target = level.getBoard().getPacmanBlock();

        List<Direction> path = findShortestPath(curr, target);
        if (path != null && !path.isEmpty()) {
            return path.get(0);
        }

        return randomMove();
    }

    /**
     * Returns the path to the item in a string format.
     * @return the path
     */
    public String getItemPath() {
        return "src//main//resources//sprites//dracula.png";
    }
}
