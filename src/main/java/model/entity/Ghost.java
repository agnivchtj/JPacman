package model.entity;


import model.frontend.Level;
import model.frontend.PlayGame;

import java.util.*;

/**
 * This is the Ghost class.
 *
 * Dataflow Anomalies have been suppressed in order
 * to keep the ghost logic intact.
 */
@SuppressWarnings("PMD.DataflowAnomalyAnalysis")
public abstract class Ghost extends Occupant implements Ghoul {
    private static final long serialVersionUID = 1L;
    private transient Level level = PlayGame.level;
    private transient int x1;
    private transient int y1;

    /**
     * Constructor method for Ghost.
     * @param x default
     * @param y default
     */
    public Ghost(int x, int y) {
        this.x1 = x;
        this.y1 = y;
    }

    /**
     * Determines the next move of the ghost and moves the ghost
     * in that direction.
     *
     */
    public void tick() throws Exception {
        Direction direction = nextMove();

        int newX = direction.getDifX() + this.x1;
        int newY = direction.getDifY() + this.y1;
        Block next_block = level.getBoard().getBlock(newX, newY);

        if (next_block.isMoveable()) {
            level.getBoard().getBlock(x1,y1).removeOccupant(this);
            this.x1 = newX;
            this.y1 = newY;
            level.getBoard().getBlock(newX,newY).addOccupant(this);
            setBounds(x1, y1, 19, 19);
        }
    }

    /**
     * Determines a move based on the ghost.
     * @return the direction towards pacman or a random move.
     */
    public abstract Direction nextMove() throws Exception;

    /**
     * Finds the shortest path using BFS.
     * @param src the starting block
     * @param target the target block
     * @return the shortest path to the destination.
     */
    public List<Direction> findShortestPath(
            Block src, Block target) throws Exception {
        List<Node> queue = new LinkedList<>();
        Set<Block> visited = new HashSet<>();

        src = level.getBoard().getBlock(x1, y1);

        if (target.equals(src)) {
            return null;
        }

        queue.add(new Node(null, null, src));

        return helperShortestPath(queue, visited);
    }


    /**
     * Apply BFS to calculate the shortest path.
     *
     * @param queue the queue used
     * @param visited the set of visited nodes
     * @return the list of directions to Pacman
     * @throws Exception this is for any Exception.
     *
     */
    public List<Direction> helperShortestPath(List<Node> queue, Set<Block> visited) throws Exception {
        int xpath;
        int ypath;
        List<Direction> shortestPath;

        while (!queue.isEmpty()) {
            Node n = queue.remove(0);
            Block b = n.location;

            xpath = level.getBoard().getX(b);
            ypath = level.getBoard().getY(b);

            shortestPath = checkInstance(b, n);
            if (shortestPath != null) return shortestPath;

            visited.add(b);
            findDirection(xpath, ypath, queue, visited, n);
        }

        return null;
    }

    /**
     * Returns the shortest path, if there is one.
     * Otherwise, return null.
     *
     * @param b the block
     * @param n the node
     * @return the list of direction comprising the shortest path.
     *
     */
    public List<Direction> checkInstance(Block b, Node n) {
        for (Occupant occ : b.getOccupants()) {
            if (occ instanceof Pacman) {
                return n.getPath();
            }
        }

        return null;
    }

    /**
     * Finds the direction for the next move.
     *
     * @param xpath the x-direction path
     * @param ypath the y-direction path
     * @param queue the queue of nodes
     * @param visited the set of visited nodes
     * @param n the respective node
     * @throws Exception this is for any Exception.
     */
    public void findDirection(int xpath, int ypath, List<Node> queue, Set<Block> visited, Node n) throws Exception {
        for (int i = 0; i < Direction.values().length; i++) {
            int neighbourX = xpath + Direction.values()[i].getDifX();
            int neighbourY = ypath + Direction.values()[i].getDifY();
            Block block = level.getBoard().getBlock(neighbourX, neighbourY);

            if (!visited.contains(block)) {
                if (block.isMoveable()) {
                    queue.add(new Node(Direction.values()[i], n, block));
                }
            }
        }
    }

    /**
     * Determines a random move.
     *
     * @return a direction in which a ghost can move, or null if
     *     the ghost has unmovable squares.
     */
    public Direction randomMove() throws Exception {
        List<Direction> moves = new ArrayList<>();

        for (int i = 0; i < Direction.values().length; i++) {
            int neighborX = this.x + Direction.values()[i].getDifX();
            int neighborY = this.y + Direction.values()[i].getDifY();
            Block neighbor = level.getBoard().getBlock(neighborX, neighborY);

            if (neighbor.isMoveable()) {
                moves.add(Direction.values()[i]);
            }
        }
        return (moves.size() > 0) ? moves.get(new Random().nextInt(moves.size())) : null;
    }

    /**
     * Getter for the horizontal coordinate.
     * @return the x1 variable
     */
    public int get_X() {
        return this.x1;
    }

    /**
     * Getter for the vertical coordinate.
     * @return the y1 variable
     */
    public int get_Y() {
        return this.y1;
    }

    /**
     * Getter for the level.
     * @return the level.
     */
    public Level getLevel() {
        return this.level;
    }

    /**
     * Setter for the level.
     * @param level the current level.
     */
    public void setLevel(Level level) {
        this.level = level;
    }

    /**
     * Returns the path to the item in a string format.
     * @return the path
     */
    public abstract String getItemPath();
}
