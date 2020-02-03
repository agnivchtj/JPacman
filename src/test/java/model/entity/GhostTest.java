package model.entity;

import model.frontend.Level;
import model.frontend.MapParser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

class GhostTest {
    private transient Ghost mary;
    private transient MapParser mapParser;
    private transient Pacman pacman;

    /**
     * Testing the path of the ghost.
     * @throws Exception this is for exception.
     */
    @Test
    public void testPath() throws Exception {
        mapParser = new MapParser("/testingPath.txt");

        Level level = mapParser.getLevel();
        mary = level.getGhosts().get(0);
        mary.setLevel(level);
        pacman = new Pacman(mapParser.getX_Coordinate(), mapParser.getY_Coordinate());

        Block mary_block = level.getBoard().getBlock(mary.get_X(), mary.get_Y());
        Block pacman_block = level.getBoard().getBlock(
                mapParser.getX_Coordinate(), mapParser.getY_Coordinate());

        assert(pacman_block != null);
        assert(mary.findShortestPath(mary_block, pacman_block) == null);
    }
}
