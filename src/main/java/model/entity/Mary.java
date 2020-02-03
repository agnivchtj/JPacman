package model.entity;

import model.frontend.Level;
import model.frontend.PlayGame;

import java.util.List;

public class Mary extends Ghost {
    private static final long serialVersionUID = 1L;
    private transient Level level = PlayGame.level;
    private transient int x1;
    private transient int y1;


    public Mary(int x, int y) {
        super(x,y);
        this.x1 = x;
        this.y1 = y;
        setBounds(x1, y1, 19, 19);
    }

    /**
     * Determining the next move of the ghost.
     * @return the direction to move in.
     * @throws Exception this is for any Exception raised.
     */
    public Direction nextMove() throws Exception {
        assert level.getBoard().getBlock(this.x1, this.y1) != null;

        Block target = level.getBoard().getPacmanBlock();
        assert target != null;

        Block curr = level.getBoard().getBlock(this.x1, this.y1);

        List<Direction> path = findShortestPath(curr, target);
        if (path != null && !path.isEmpty()) {
            return path.get(0);
        }
        return randomMove();
    }

    /**
     *
     * @return the path to the item in a string format.
     */
    public String getItemPath() {
        return "src//main//resources//sprites//mary.png";
    }
}
