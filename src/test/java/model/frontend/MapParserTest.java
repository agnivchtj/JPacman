package model.frontend;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.InputStream;

import java.util.ArrayList;

import model.entity.Block;
import model.entity.Ghost;
import model.entity.Pellet;
import model.entity.Wall;

import org.junit.jupiter.api.Test;


/**
 * Test class for MapParser class.
 */
@SuppressWarnings("PMD.CloseResource")
class MapParserTest {

    private final transient String mapFile = "/board.txt";
    private transient MapParser mapP;

    /**
     * Check if works from method ParseString.
     * @throws Exception IOException in the InputStream.
     */
    @Test
    void testConstructparseString() throws Exception {
        mapP = new MapParser(mapFile);
        mapP.parseString(mapFile);
        Block block = mapP.getLevel().getBoard().getBlock(0, 0);
        Block blockresult = new Block();
        blockresult.addOccupant(new Wall());
        assertEquals(block.getOccupants(), blockresult.getOccupants());
    }

    /**
     * Check if works from method ParseString with exception.
     * @throws Exception IOException in the InputStream.
     */
    @Test
    void testConstructparseStringException() throws Exception {
        mapP = new MapParser(mapFile);
        assertThrows(Exception.class, () -> mapP.parseString("/boardexception.txt"));
    }


    /**
     * In the text file there is a Wall at coordinate (0,0). Test case checks this.
     *
     * @throws Exception IOException in the InputStream.
     */
    @Test
    void testConstructMapCheckBlockWall() throws Exception {
        InputStream boardStream = MapParser.class.getResourceAsStream(mapFile);
        mapP = new MapParser(mapFile);
        mapP.parseTxt(boardStream);
        Block block = mapP.getLevel().getBoard().getBlock(0, 0);
        Block blockResult = new Block();
        blockResult.addOccupant(new Wall());
        assertEquals(block.getOccupants(), blockResult.getOccupants());
    }

    /**
     * In the text file there is a Pellet at coordinate (1,1). Test case checks this.
     *
     * @throws Exception IOException in the InputStream.
     */
    @Test
    void testConstructMapCheckBlockPellet() throws Exception {
        InputStream boardStream = MapParser.class.getResourceAsStream(mapFile);
        mapP = new MapParser(mapFile);
        mapP.parseTxt(boardStream);
        Block block = mapP.getLevel().getBoard().getBlock(1, 1);
        System.out.println(block.getOccupants());
        assertTrue(block.getOccupants().get(0) instanceof Pellet);
    }

    /**
     * In the text file there is a Ghost at coordinate (2,1). Test case checks this.
     *
     * @throws Exception IOException in the InputStream.
     */
    @Test
    void testConstructMapCheckBlockGhost() throws Exception {
        InputStream boardStream = MapParser.class.getResourceAsStream(mapFile);
        mapP = new MapParser(mapFile);
        mapP.parseTxt(boardStream);
        Block block = mapP.getLevel().getBoard().getBlock(2, 1);
        //System.out.println(block.getOccupants());
        assertTrue(block.getOccupants().get(0) instanceof Ghost);
    }

    /**
     * In the txt file Pacman is at coordinate (13,12). Test case checks this.
     *
     * @throws Exception IOException in the InputStream.
     */
    @Test
    void testConstructMapCheckLocationPacMan() throws Exception {
        InputStream boardStream = MapParser.class.getResourceAsStream(mapFile);
        mapP = new MapParser(mapFile);
        mapP.parseTxt(boardStream);
        int x = mapP.getX_Coordinate();
        int y = mapP.getY_Coordinate();
        assertEquals(13, x, "x is not on correct coordinate");
        assertEquals(12, y, "y is not on correct coordinate");
    }

    /**
     * Test if method throws an error when reading an invalid character.
     * @throws Exception default
     */
    @Test
    void testInvalidCharacter() throws Exception {
        ArrayList<String> lines = new ArrayList<>();
        lines.add("--x--G------");
        mapP = new MapParser(mapFile);
        assertThrows(Exception.class, () -> mapP.parseLines(lines));
    }
}