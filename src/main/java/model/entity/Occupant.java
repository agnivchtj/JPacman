package model.entity;

import java.awt.Rectangle;

/**
 * Any presence that occupies the board.
 */
@SuppressWarnings("pmd:MissingStaticMethodInNonInstantiatableClass")
public abstract class Occupant extends Rectangle {
    private static final long serialVersionUID = 1L;

    /**
     * Block.
     */
    private transient Block block;

    /**
     * Direction.
     */
    private transient Direction direction;

    /**
     * Default direction facing.
     */
    private transient Direction defaultDir = Direction.UP;

    /**
     * Constructor for Occupant.
     */
    protected Occupant() {
        this.direction = defaultDir;
    }

    /**
     * Setter for direction.
     * @param newDir The new direction of the occupant
     */
    public void setDirection(Direction newDir) {
        this.direction = newDir;
    }

    /**
     * Getter for direction.
     */
    public Direction getDirection() {
        return this.direction;
    }


    /**
     * Getter for the String of the directions.
     * @return string of direction.
     */
    public String getStringDirection() {
        if(getDirection().equals(Direction.LEFT)){
            return "left";
        }
        else if(getDirection().equals(Direction.RIGHT)){
            return "right";
        }
        else if(getDirection().equals(Direction.UP)){
            return "up";
        }
        else return "down";
    }


    /**
     * Getter for the block the occupant is occupying.
     * @return the block occupant is on.
     */
    public Block getBlock() {
        if (onBlock()) {
            return block;
        }
        return null;
    }

    /**
     * Getter for onBlock.
     * @return whether the occupant is on a block.
     */
    public boolean onBlock() {
        return (block != null);
    }

    /**
     * Occupies the target block iff this occupant is able to.
     */
    public void on(Block target) throws Exception {
        if (target != null) {
            if (onBlock()) {
                block.removeOccupant(this);
            }
            block = target;
            target.addOccupant(this);
        }
    }
}
