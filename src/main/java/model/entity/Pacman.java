package model.entity;

import model.frontend.Level;
import model.frontend.PlayGame;

import java.util.ArrayList;
import java.util.List;

/**
 * The Pacman class is the player class.
 */
public class Pacman extends Occupant implements Player {
    private static final long serialVersionUID = 1L;
    private transient boolean walking;
    public transient Level level = PlayGame.level;
    private transient List<Occupant> pellets;
    private transient int blockCount;
    private transient State state;
    private transient boolean bonus;
    private transient String bonusType;
    private transient int bonusCounter;
    private transient int bonusEnd;

    /**
     * Constructor for the Pacman.
     * @param x the x-direction.
     * @param y the y-direction.
     */
    public Pacman(int x, int y) {
        //this is a rectangle method
        setBounds(x, y, 19, 19);
        this.pellets = new ArrayList<>();
        this.state = State.RUNNING;
        this.bonus = false;
        this.bonusType = " ";
        this.blockCount = 0;
        this.bonusCounter = 0;
        this.bonusEnd = 0;
    }

    /**
     * The tick method runs when a key is clicked.
     * @throws Exception this is for any Exception.
     */
    @SuppressWarnings("PMD.DataflowAnomalyAnalysis")
    public void tick() throws Exception {
        for(Occupant occ : level.getBoard().getPacmanBlock().getOccupants()){
           if(occ instanceof Ghost && !isBonusStraw()){
               this.state = State.LOST;
           }
        }
        if(!getState().stop()) {
            if (walking) {
                int deltax = getDirection().getDifX();
                int deltay = getDirection().getDifY();
                if (collision((x / 19) + deltax, (y/19) + deltay, level)) {
                    x = ((x/19) + deltax) * 19;
                    y = ((y/19) + deltay) * 19;
                }
            }
            for(Occupant occ : level.getBoard().getPacmanBlock().getOccupants()){
                if(occ instanceof Ghost && !isBonusStraw()){
                    this.state = State.LOST;
                }
            }
            if (level.ghosts != null) {
                for(Ghost ghost : level.ghosts){
                    ghost.tick();
                }
            }
        }
    }

    /**
     * The method that handles the collisions for pacman.
     *
     * @param x the x-direction.
     * @param y the y-direction.
     * @param level the current level.
     * @return whether there is collision or not.
     * @throws Exception this is for any Exception.
     *
     * Certain PMD warnings were suppressed to ensure game could
     * function unhampered.
     */
    @SuppressWarnings({"PMD.AvoidBranchingStatementAsLastInLoop", "PMD.DataflowAnomalyAnalysis"})
     public boolean collision(int x, int y, Level level) throws Exception {
        Block block = level.getBoard().getBlock(x, y);

        if (collisionBonusBlock(block)) return true;
        bonusCheck();

        if (block.isMoveable()) {
            if (!collisionFatal(block)) return false;

            for (Occupant occupant : block.getOccupants()) {
                collisionPellet(occupant, block);
                collisionBonus(occupant, block);
                collisionGhost(occupant);
                block.addOccupant(this);
                level.getBoard().setPacmanBlock(block);
                this.blockCount += 1;
                return true;
            }
        }

        return (block.getOccupants().isEmpty() && movement(block));
    }

    /**
     * Helper class that checks for collisions with cherry.
     *
     * @param block the current block
     * @return whether there is a collision with cherry
     * @throws Exception this is for any Exception e
     */
    @SuppressWarnings("PMD.DataflowAnomalyAnalysis")
    public boolean collisionBonusBlock(Block block) throws Exception {
        if (this.bonus && this.bonusType.equals("cherry")) {
            for (Occupant occupant : block.getOccupants()) {
                if (occupant instanceof Wall) {
                    Block oldPac = level.getBoard().getPacmanBlock();
                    oldPac.removeOccupant(this);
                    block.addOccupant(this);
                    level.getBoard().setPacmanBlock(block);
                    this.blockCount += 1;
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * Helper class that resets bonus.
     */
    public void bonusCheck() {
        if (this.bonus) {
            this.bonusCounter++;
            bonusCounting();
        }
    }

    /**
     * Helper class used in collision for checking fatal block.
     *
     * @param block the current block
     * @return if this block is fatal
     */
    public boolean collisionFatal(Block block) {
        if (block.isFatal() && !isBonusStraw()) {
            this.state = State.LOST;
            return false;
        }

        return true;
    }

    /**
     * Helper class used in collision for a ghost.
     *
     * @param occupant the occupant on the block
     * @throws Exception this is for any Exception
     *
     */
    public void collisionGhost(Occupant occupant) throws Exception {
        if (occupant instanceof Ghost && isBonusStraw()) {
            Block oldPac = level.getBoard().getPacmanBlock();
            oldPac.removeOccupant(this);
        }
    }

    /**
     * Helper class used in collision for bonus items.
     *
     * @param occupant the occupant on the block.
     * @param block the current block.
     * @throws Exception this is for any Exception.
     *
     */
    public void collisionBonus(Occupant occupant, Block block) throws Exception {
        if (occupant instanceof Bonus) {
            String bonusType = ((Bonus) occupant).getItem();
            this.bonus = true;
            this.bonusType = bonusType;
            level.setBonus(true);
            level.setBonusType(bonusType);
            this.bonusEnd = ((Bonus) occupant).getEnd();
            block.removeOccupant(occupant);
            Block oldPac = level.getBoard().getPacmanBlock();
            oldPac.removeOccupant(this);
        }
    }

    /**
     * Helper class used in collision for pellet.
     *
     * @param occupant the occupant of the block
     * @param block the current block
     * @throws Exception this is for any Exception.
     *
     */
    public void collisionPellet(Occupant occupant, Block block) throws Exception {
        if (occupant instanceof Pellet && block.isPellet()) {
            block.removeOccupant(occupant);
            pellets.add(occupant);
            level.pellets.remove(0);
            if (level.pellets.size() <= 0) {
                state = State.WON;
            }
            Block oldPac = level.getBoard().getPacmanBlock();
            oldPac.removeOccupant(this);
        }
    }

    /**
     * Helper class for setting a new Pacman block.
     *
     * @param block the block to move Pacman.
     * @return true after Pacman has been moved.
     * @throws Exception this is for any Exception
     *
     */
    public boolean movement(Block block) throws Exception {
        Block oldPac = level.getBoard().getPacmanBlock();
        oldPac.removeOccupant(this);
        block.addOccupant(this);
        level.getBoard().setPacmanBlock(block);
        this.blockCount += 1;
        return true;
    }

    /**
     * Getter for whether Pacman's walking.
     * @return the boolean variable 'walking'.
     */
    public boolean isWalking() {
        return walking;
    }

    /**
     * Setter for whether Pacman's walking.
     * @param walking the boolean variable.
     */
    public void setWalking(boolean walking) {
        this.walking = walking;
    }

    /**
     * Getter for list of Pellets Pacman has eaten.
     * @return list of Pellets.
     */
    public List<Occupant> getPellets() {
        return pellets;
    }

    //Block count
    public int getBlockCount() {
        return blockCount;
    }


    /**
     * Getter for the State that Pacman is in.
     * @return the current State.
     */
    public State getState() {
        return state;
    }

    /**
     * Bonus counting.
     */
    private void bonusCounting() {
        if (bonusCounter >= bonusEnd) {
            this.bonus = false;
            level.setBonus(false);
            this.bonusCounter = 0;
        }
    }

    /**
     * returns TRUE or FALSE if the bonus is active.
     * @return whether there is a bonus or not
     */
    public boolean isBonus() {
        return this.bonus;
    }

    /**
     * Set the bonus counter.
     * @param bonusCounter default
     */
    public void setBonusCounter(int bonusCounter) {
        this.bonusCounter = bonusCounter;
    }

    public boolean isBonusStraw(){
        return (bonus && this.bonusType.equals("strawberry"));
    }
}
