package model.entity;

import java.util.ArrayList;
import java.util.List;

public interface Ghoul {
    /**
     * The method where it's to be implemented that the ghosts chase the player.
     */
    void tick() throws Exception;

    /**
     * Calculates a move based on the ghost.
     *
     * @return an optional containing the move or empty if the current state of the game
     *      makes the move impossible
     */
    Direction nextMove() throws Exception;

    /**
     * Finds the shortest path using BFS.
     * @param src the starting block
     * @param target the target block
     * @return the shortest path to the destination.
     */
    List <Direction> findShortestPath(
            Block src, Block target) throws Exception;

    final class Node {
        protected final Direction direction;

        protected final Node parent;

        protected final Block location;

        Node(Direction direction, Node parent, Block location) {
            this.direction = direction;
            this.parent = parent;
            this.location = location;
        }

        /**
         * Returns the path.
         * @return default
         */
        public List<Direction> getPath() {
            if (parent == null) {
                return new ArrayList<>();
            }

            List<Direction> path = parent.getPath();
            path.add(direction);
            return path;
        }
    }
}
