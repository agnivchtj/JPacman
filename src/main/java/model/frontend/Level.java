package model.frontend;

import model.entity.Block;
import model.entity.Board;
import model.entity.Ghost;
import model.entity.Pellet;

import java.awt.*;
import java.util.List;

/**
 * This is the Level class.
 */
public class Level {
    public transient List<Pellet> pellets;
    public transient List<Ghost> ghosts;
    public transient int blockWidth = 19;
    public transient int blockHeight = 19;
    public transient Board board;
    private transient boolean bonus;
    private transient String bonusType;

    /**
     * Constructor class.
     * @param board the board array.
     * @param pellets the pellets.
     * @param ghosts the ghosts.
     */
    public Level(Board board, List<Pellet> pellets, List<Ghost> ghosts) {
        this.board = board;
        this.pellets = pellets;
        this.ghosts = ghosts;
        this.bonus = false;
        this.bonusType = "";
    }

    /**
     * Getter for the board.
     * @return the board.
     */
    public Board getBoard() {
        return board;
    }

    /**
     * A render method that is called from the PlayGame class,
     * and traverses through the board and the list of pellets.
     * @param graphics the graphics of the game.
     */
    public void render(Graphics graphics) {
        pellets.clear();
        for (int x = 0; x < board.getWidth(); x++) {
            for (int y = 0; y < board.getHeight(); y++) {
                Block block = board.getGrid()[x][y];
                if (block.isPellet()) {
                    pellets.add(new Pellet());
                }
                board.getGrid()[x][y].render(
                        x * blockWidth,
                        y * blockHeight,
                        blockWidth,
                        blockHeight,
                        graphics,
                        this.bonus,
                        this.bonusType
                );
            }
        }
    }

    public void setBonus(boolean bonus) {
        this.bonus = bonus;
    }
    public boolean getBonus() {
        return this.bonus;
    }

    public void setBonusType(String bonusType) {
        this.bonusType = bonusType;
    }
    public String getBonusType()
    {
    return this.bonusType;
    }

    public List<Ghost> getGhosts() {
        return this.ghosts;
    }
    public void setGhosts(List<Ghost> ghosts) {
        this.ghosts = ghosts;
    }
}
