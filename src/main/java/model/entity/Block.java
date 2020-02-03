package model.entity;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;


/**
 * The class Square is an Object that stores it's occupants.
 */
public class Block {
    /**
     * List of occupants of the Square.
     */
    public transient List<Occupant> occupants;

    /**
     * Boolean to check whether a square is available for the User to move to.
     */
    private boolean moveable = true;

    /**
     * Boolean to check whether moving to this square means losing the game.
     */
    private boolean fatal = false;

    /**
     * Creating a Square.
     */
    public Block() {
        this.occupants = new ArrayList<>();
    }

    /**
     * Add an occupant to the block.
     *
     * @param occupant the occupant that has to be added.
     */
    public void addOccupant(Occupant occupant) {
        if (!occupants.contains(occupant)) {
            occupants.add(occupant);
        }
    }

    /**
     * Remove an occupant from the block.
     *
     * @param occupant the occupant that should be removed.
     * @throws Exception triggered if there is not an occupant of this kind.
     */
    public void removeOccupant(Occupant occupant) throws Exception {
        if (occupant instanceof Wall) {
            throw new Exception("Walls cannot be removed from a Block");
        }
        if (occupants.contains(occupant)) {
            occupants.remove(occupant);
        } else {
            throw new Exception("There is not an occupant of this kind on this block");
        }
    }


    /**
     * Checks if the block contains a wall. If it contains a wall, the User can't move to that
     * block and the boolean moveability should be set to false.
     */
    private void isWall() {
        for (int i = 0; i < occupants.size(); i++) {
            if (occupants.get(i) instanceof Wall) {
                this.setMoveable(false);
                break;
            }
        }
    }

    /**
     * Checks if the block contains a wall. If it contains a wall, the User can't move to that
     * block and the boolean moveability should be set to false.
     */
    public boolean isPellet() {
        for (int i = 0; i < occupants.size(); i++) {
            if (occupants.get(i) instanceof Pellet) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if the block contains a ghost. If it contains a ghost, the User will lose the game
     * and the boolean moveToLose should be set to true.
     * Suppress PMD error sinds PMD is not compatible with for-each loops.
     */
    @SuppressWarnings("PMD.DataflowAnomalyAnalysis")
    public void isGhost() {
        for (Occupant occupant : occupants) {
            if (occupant instanceof Ghost) {
                this.setFatal(true);
            }
        }
    }

    /**
     * Get the moveability of a Block.
     *
     * @return the boolean moveability.
     */
    public boolean isMoveable() {
        isWall();
        return moveable;
    }

    /**
     * Get the moveToLose of a Block.
     *
     * @return the boolean moveToLose.
     */
    public boolean isFatal() {
        isGhost();
        return fatal;
    }

    /**
     * Setter for the moveability.
     *
     * @param moveable the moveability boolean.
     */
    public void setMoveable(boolean moveable) {
        this.moveable = moveable;
    }

    /**
     * Setter for moveToLose.
     *
     * @param fatal the boolean.
     */
    private void setFatal(boolean fatal) {
        this.fatal = fatal;
    }

    /**
     * Get the occupants of the block.
     *
     * @return List of occupants of the block.
     */
    public List<Occupant> getOccupants() {
        return occupants;
    }

    /**
     * Render method that checks which occupant has to be shown on screen.
     * Note: PMD warning has been suppressed since PMD is not compatible with for-each loops.
     *
     * @param x        the horizontal movement.
     * @param y        the vertical movement.
     * @param width    the width.
     * @param height   the height.
     * @param graphics the graphics of the game.
     */
    @SuppressWarnings("PMD.DataflowAnomalyAnalysis")
    public void render(int x, int y, int width, int height, Graphics graphics,
                       Boolean bonus, String bonusType) {
        ImageIcon image = null;
        for (Occupant occupant : occupants) {
            if (occupant instanceof Pellet) {
                image = new ImageIcon("src//main//resources//sprites//pellet.gif");
            } else if (occupant instanceof Ghost) {
                if(bonus && bonusType.equals("strawberry")){
                    image = new ImageIcon("src//main//resources//sprites//ghost_bonus.png");
                } else {
                    image = new ImageIcon(((Ghost) occupant).getItemPath());
                }
            } else if (occupant instanceof Wall) {
                if (!bonus || !bonusType.equals("cherry")){
                    image = new ImageIcon("src//main//resources//sprites//wall.png");
                }
            } else if (occupant instanceof Pacman) {
                StringBuilder sb = new StringBuilder();
                sb.append("src//main//resources//pacman");
                sb.append(occupant.getStringDirection());
                sb.append(".gif");
                image = new ImageIcon(sb.toString());
            } else if(occupant instanceof Bonus) {
                image = new ImageIcon(((Bonus) occupant).getItemPath());
            }
            if(image != null){
                graphics.drawImage(image.getImage(), x, y, width, height, null);
            }
        }
    }
}
