package model.frontend;

import model.entity.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * Class to used to Parse txt files into a Level.
 */
@SuppressWarnings("PMD.DataflowAnomalyAnalysis")
public class MapParser {

    /**
     * Level that gets created.
     */
    protected transient Level level;

    /**
     * X coordinate of PacMan on the board.
     */
    private transient int x1;

    /**
     * Y Coordinate of PacMan on the board.
     */
    private transient int y1;


    /**
     * Constructor method.
     * SuppressWarnings for ConstructorCallsOverridableMethod since the method doesn't.
     */
    @SuppressWarnings("PMD.ConstructorCallsOverridableMethod")
    public MapParser(String txt) throws Exception {
        this.level = parseString(txt);
    }


    /**
     * Parses string into Inputstream.
     * @param mapName name of the resource file.
     * @return the level.
     * @throws Exception this is for any IOException.
     */
    public Level parseString(String mapName) throws Exception {
        try (InputStream boardStream = MapParser.class.getResourceAsStream(mapName)) {
            if (boardStream == null) {
                boardStream.close();
                throw new IOException(
                        "IOException; could not get resource for: "
                                + mapName
                                + ", boardStream is: "
                                + boardStream
                );
            }
            return parseTxt(boardStream);
        }
    }

    /**
     * Parses InputStream to an ArrayList containing String that represent the lines in txt file.
     * SupressWarning CloseResource, since resources are closed but warning stays.
     * @param txt InputStream that has to be read.
     * @return Level
     * @throws Exception IOException when resource is not found.
     */
    @SuppressWarnings("PMD.CloseResource")
    public Level parseTxt(InputStream txt) throws Exception {
        Reader read = new InputStreamReader(txt, StandardCharsets.UTF_8);
        BufferedReader reader = new BufferedReader(read);
        List<String> lines = new ArrayList<>();
        while (reader.ready()) {
            String line = reader.readLine();
            lines.add(line);
        }
        reader.close();
        read.close();
        txt.close();
        return parseLines(lines);
    }

    /**
     * Parses ArrayList of Strings into a 2 dimensional char array.
     * @param lines ArrayList used to parse
     * @return Level
     * @throws Exception IOException when resource is not found.
     */
    public Level parseLines(List<String> lines) throws Exception {
        int ycor = lines.size();
        int xcor = lines.get(0).length();
        char[][] charMap = new char[xcor][ycor];
        for (int x = 0; x < xcor; x++) {
            for (int y = 0; y < ycor; y++) {
                charMap[x][y] = lines.get(y).charAt(x);
            }
        }
        return createLevel(charMap);
    }

    /**
     * Creates Level for the game.
     * @param charMap charMap used in order to create a Board.
     * @return Level
     * @throws Exception IOException when resource is not found.
     */
    public Level createLevel(char[][] charMap) throws Exception {
        int width = charMap.length;
        int heigth = charMap[0].length;
        Block[][] grid = new Block[width][heigth];
        List<Pellet> pellets = new ArrayList<>();
        List<Ghost> ghosts = new ArrayList<>();
        makeGrid(charMap, width, heigth, grid, ghosts, pellets);
        Board board = new Board(grid);
        Level level = new Level(board, pellets, ghosts);
        return level;
    }

    /**
     * Makes the grid for the Board.
     * @param charMap char array used to fill the board.
     * @param width 1st dimension (width) of the Block[][]
     * @param height 2nd dimension (height) of the Block[][]
     * @param grid empty grid that will be filled
     * @param ghosts list of ghosts on the board
     * @param pellets list of pellets on the board
     * @throws Exception IOException when resource is not found.
     */
    public void makeGrid(
            char[][] charMap,
            int width, int height, Block[][] grid,
            List<Ghost> ghosts, List<Pellet> pellets) throws Exception {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                grid[x][y] = new Block();
                char c = charMap[x][y];
                addBlock(grid, x, y, c, ghosts, pellets);
            }
        }
    }

    /**
     * Checks what char it is and creates Occupant according to char.
     * @param grid grid that gets filled
     * @param x coordinate of 1st dimension
     * @param y coordinate of 2nd dimension
     * @param c char that gets compared
     * @param ghosts list of ghosts on the board
     * @param pellets list of pellets on the board
     * @throws Exception IOException when resource is not found.
     */
    public void addBlock(
            Block[][] grid, int x, int y, char c,
            List<Ghost> ghosts, List<Pellet> pellets) throws Exception {
        switch (c) {
            case '-':
                grid[x][y].addOccupant(new Wall());
                break;
            case '.':
                Pellet pellet = new Pellet();
                pellets.add(pellet);
                grid[x][y].addOccupant(pellet);
                break;
            case 'P':
                setX_Coordinate(x);
                setY_Coordinate(y);
                break;
            case 'M':
                Ghost ghostM = new Mary(x, y);
                ghosts.add(ghostM);
                grid[x][y].addOccupant(ghostM);
                break;
            case 'F':
                Ghost ghostF = new Frankenstein(x, y);
                ghosts.add(ghostF);
                grid[x][y].addOccupant(ghostF);
                break;
            case 'D':
                Ghost ghostD = new Dracula(x, y);
                ghosts.add(ghostD);
                grid[x][y].addOccupant(ghostD);
                break;
            case 'C':
                Bonus bonusC = new Bonus("cherry");
                grid[x][y].addOccupant(bonusC);
                break;
            case 'S':
                Bonus bonusS = new Bonus("strawberry");
                grid[x][y].addOccupant(bonusS);
                break;
            default:
                throw new Exception("Invalid character " + c + "at coordinate: (" + x + ", " + y);
        }
    }

    /**
     * Setter for coordinate x.
     * @param x2 the x-coordinate.
     */
    public void setX_Coordinate(int x2) {
        this.x1 = x2;
    }

    /**
     * Setter for coordinate y.
     * @param y2 the y-coordinate.
     */
    public void setY_Coordinate(int y2) {
        this.y1 = y2;
    }

    /**
     * Getter for coordinate x.
     * @return coordinate x
     */
    public int getX_Coordinate() {
        return x1;
    }

    /**
     * Getter for coordinate y.
     * @return coordinate y
     */
    public int getY_Coordinate() {
        return y1;
    }

    /**
     * Getter for level.
     * @return level
     */
    public Level getLevel() {
        return level;
    }
}
