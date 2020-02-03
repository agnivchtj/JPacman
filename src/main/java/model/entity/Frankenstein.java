package model.entity;

import model.frontend.Level;
import model.frontend.PlayGame;


/**
 * Frankenstein class.
 * Warning was suppressed to unhamper the game
 * logic.
 */
@SuppressWarnings("PMD.DataflowAnomalyAnalysis")
public class Frankenstein extends Ghost {
    private static final long serialVersionUID = 1L;
    private static final int hesitance = 15;
    private transient Level level = PlayGame.level;
    private transient int x1;
    private transient int y1;

    public Frankenstein(int x, int y) {
        super(x, y);
        setBounds(x, y, 32, 32);
    }

    /**
     * Determining the next move of the ghost.
     * @return the direction to move in.
     * @throws Exception this is for any Exception raised.
     */
    public Direction nextMove() throws Exception {
        return randomMove();
    }

    /**
     * Returns the path to the item in a string format.
     * @return
     */
    public String getItemPath() {
        return "src//main//resources//sprites//frank.png";
    }
}
