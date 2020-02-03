package model.entity;

import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


/**
 * Test class for Block class.
 */
class BlockTest {


    /**
     * Test case for constructing a block and checking default moveability.
     */
    @Test
    void testBlockConstructorMoveability() {
        Block block = new Block();
        assertTrue(block.isMoveable());
    }

    /**
     * Test case for constructing a block and checking default moveToLose.
     */
    @Test
    void testBlockConstructorMoveToLose() {
        Block block = new Block();
        assertFalse(block.isFatal());
    }

    /**
     * Test case for constructing a block and checking whether list 'occupation' is empty.
     */
    @Test
    void testBlockConstructorOccupation() {
        Block block = new Block();
        assertEquals(0, block.getOccupants().size());
    }

    /**
     * Test if there are any occupants.
     */
    @Test
    void testGetOccupants() {
        Block block = new Block();
        assertTrue(block.occupants.size() == 0);
    }

    /**
     * Test if there is a pellet.
     */
    @Test
    void testIsPellet() {
        Block block = new Block();
        Pellet pellet = new Pellet();
        block.addOccupant(pellet);
        assertTrue(block.isPellet());
    }

    /**
     * Test if there is a pellet.
     */
    @Test
    void testIsPelletFalse() {
        Block block = new Block();
        assertFalse(block.isPellet());
    }

    /**
     * Test case for adding an occupant to a block and checking the moveability and moveToLose
     * boolean. Occupant used: Pellet.
     */
    @Test
    void testBlockWithAPelletOccupant() {
        Block block = new Block();
        Pellet pellet = new Pellet();
        block.addOccupant(pellet);
        assertEquals(1, block.getOccupants().size());
        assertTrue(block.isMoveable());
        assertFalse(block.isFatal());
    }

    /**
     * Test case for adding and deleting an occupant to the list 'occupation'. Occupant used: Pellet
     * @throws Exception if occupant can't be removed.
     */
    @Test
    void testBlockWithAddingAndDeletingOccupant() throws Exception {
        Block block = new Block();
        Pellet pellet = new Pellet();
        block.addOccupant(pellet);
        block.removeOccupant(pellet);
        assertEquals(0, block.getOccupants().size());
    }

    /**
     * Test case for adding and deleting a Wall from list 'occupation'.
     */
    @Test
    void testBlockWithAddingAndDeletingWall() {
        Block block = new Block();
        Wall wall = new Wall();
        block.addOccupant(wall);
        assertThrows(Exception.class, () -> block.removeOccupant(wall));
    }

    /**
     * Test case for adding and deleting a Wall from list 'occupation'.
     */
    @Test
    void testBlockWithAddingGhost() {
        Block block = new Block();
        Ghost ghost = new Mary(1, 1);
        block.addOccupant(ghost);
        assertTrue(block.isFatal());
    }

    /**
     * Test case for removing an occupant that is not in the list 'occupation'.
     */
    @Test
    void testBlockRemoveOccupantEmptyOccupation() throws Exception {
        Block block = new Block();
        Pellet pellet = new Pellet();
        block.addOccupant(pellet);
        block.removeOccupant(pellet);
        assertThrows(Exception.class, () -> block.removeOccupant(pellet));
    }

    /**
     * Test case for adding an occupant to a block and checking the moveability and moveToLose
     * boolean. Occupant used: Wall.
     */
    @Test
    void testBlockWithAWallOccupant() {
        Block block = new Block();
        Wall wall = new Wall();
        block.addOccupant(wall);
        assertEquals(1, block.getOccupants().size());
        assertFalse(block.isMoveable());
        assertFalse(block.isFatal());
    }


    /**
     * Test for rendering a Pellet.
     */
    @Test
    void testRenderPellet() {
        Graphics graph = mock(Graphics.class);
        Block block = new Block();
        block.addOccupant(new Pellet());
        Boolean bonus = false;
        String bonusType = "";
        block.render(1, 1, 19, 18, graph, bonus, bonusType);
        ImageIcon image = new ImageIcon("src//main//resources//sprites//pellet.gif");
        verify(graph).drawImage(image.getImage(), 1, 1, 19, 18, null);
    }

    /**
     * Test for rendering a Wall.
     */
    @Test
    void testRenderWall() {
        Graphics graph = mock(Graphics.class);
        Block block = new Block();
        block.addOccupant(new Wall());
        Boolean bonus = false;
        String bonusType = "";
        block.render(1, 1, 19, 18, graph, bonus, bonusType);
        ImageIcon image = new ImageIcon("src//main//resources//sprites//wall.png");
        verify(graph).drawImage(image.getImage(), 1, 1, 19, 18, null);
    }

    /**
     * Test for rendering a Mary Ghost.
     */
    @Test
    void testRenderGhostMary() {
        Graphics graph = mock(Graphics.class);
        Block block = new Block();
        block.addOccupant(new Mary(1,1));
        //block.render(1, 1, 19, 18, graph, true, "");
        block.addOccupant(new Mary(1,1));
        Boolean bonus = false;
        String bonusType = "";
        block.render(1, 1, 19, 18, graph, bonus, bonusType);
        ImageIcon image = new ImageIcon("src//main//resources//sprites//mary.png");
        verify(graph).drawImage(image.getImage(), 1, 1, 19, 18, null);
    }

    /**
     * Test for rendering a Dracula Ghost.
     */
    @Test
    void testRenderGhostDracula() {
        Graphics graph = mock(Graphics.class);
        Block block = new Block();
        block.addOccupant(new Dracula(1,1));
        //block.render(1, 1, 19, 18, graph, true, "");
        block.addOccupant(new Dracula(1,1));
        Boolean bonus = false;
        String bonusType = "";
        block.render(1, 1, 19, 18, graph, bonus, bonusType);
        ImageIcon image = new ImageIcon("src//main//resources//sprites//dracula.png");
        verify(graph).drawImage(image.getImage(), 1, 1, 19, 18, null);
    }

    /**
     * Test for rendering a Frankenstein Ghost.
     */
    @Test
    void testRenderGhostFrank() {
        Graphics graph = mock(Graphics.class);
        Block block = new Block();
        block.addOccupant(new Frankenstein(1,1));
        //block.render(1, 1, 19, 18, graph, true, "");
        Boolean bonus = false;
        String bonusType = "";
        block.render(1, 1, 19, 18, graph, bonus, bonusType);
        ImageIcon image = new ImageIcon("src//main//resources//sprites//frank.png");
        verify(graph).drawImage(image.getImage(), 1, 1, 19, 18, null);
    }

    /**
     * Test for rendering Pacman going up.
     */
    @Test
    void testRenderPacmanUp() {
        Graphics graph = mock(Graphics.class);
        Block block = new Block();
        Pacman player = new Pacman(1,1);
        player.setDirection(Direction.UP);
        block.addOccupant(player);
        Boolean bonus = false;
        String bonusType = "";
        block.render(1, 1, 19, 18, graph, bonus, bonusType);
        ImageIcon image = new ImageIcon("src//main//resources//pacmanup.gif");
        verify(graph).drawImage(image.getImage(), 1, 1, 19, 18, null);
    }

    /**
     * Test for rendering Pacman going down.
     */
    @Test
    void testRenderPacmanDown() {
        Graphics graph = mock(Graphics.class);
        Block block = new Block();
        Pacman player = new Pacman(1,1);
        player.setDirection(Direction.DOWN);
        block.addOccupant(player);
        Boolean bonus = false;
        String bonusType = "";
        block.render(1, 1, 19, 18, graph, bonus, bonusType);
        ImageIcon image = new ImageIcon("src//main//resources//pacmandown.gif");
        verify(graph).drawImage(image.getImage(), 1, 1, 19, 18, null);
    }

    /**
     * Test for rendering Pacman going right.
     */
    @Test
    void testRenderPacmanRight() {
        Graphics graph = mock(Graphics.class);
        Block block = new Block();
        Pacman player = new Pacman(1,1);
        player.setDirection(Direction.RIGHT);
        block.addOccupant(player);
        Boolean bonus = false;
        String bonusType = "";
        block.render(1, 1, 19, 18, graph, bonus, bonusType);
        ImageIcon image = new ImageIcon("src//main//resources//pacmanright.gif");
        verify(graph).drawImage(image.getImage(), 1, 1, 19, 18, null);
    }

    /**
     * Test for rendering Pacman going left.
     */
    @Test
    void testRenderPacmanLeft() {
        Graphics graph = mock(Graphics.class);
        Block block = new Block();
        Pacman player = new Pacman(1,1);
        player.setDirection(Direction.LEFT);
        block.addOccupant(player);
        Boolean bonus = false;
        String bonusType = "";
        block.render(1, 1, 19, 18, graph, bonus, bonusType);
        ImageIcon image = new ImageIcon("src//main//resources//pacmanleft.gif");
        verify(graph).drawImage(image.getImage(), 1, 1, 19, 18, null);
    }

    /**
     * Test for rendering a Ghost when bonus item strawberry is eaten.
     */
    @Test
    void testRenderGhostBonusStrawBerry() {
        Graphics graph = mock(Graphics.class);
        Block block = new Block();
        block.addOccupant(new Mary(1,1));
        Boolean bonus = true;
        String bonusType = "strawberry";
        block.render(1, 1, 19, 18, graph, bonus, bonusType);
        ImageIcon image = new ImageIcon("src//main//resources//sprites//ghost_bonus.png");
        verify(graph).drawImage(image.getImage(), 1, 1, 19, 18, null);
    }

    /**
     * Test for rendering a Wall when bonus item cherry is eaten.
     */
    @Test
    void testRenderGhostBonusCherry() {
        Graphics graph = mock(Graphics.class);
        Block block = new Block();
        block.addOccupant(new Wall());
        Boolean bonus = true;
        String bonusType = "cherry";
        block.render(1, 1, 19, 18, graph, bonus, bonusType);
        verifyZeroInteractions(graph);
    }

    /**
     * Test for rendering a Bonus item cherry.
     */
    @Test
    void testRenderBonusCherry() {
        Graphics graph = mock(Graphics.class);
        Block block = new Block();
        block.addOccupant(new Bonus("cherry"));
        Boolean bonus = true;
        String bonusType = "cherry";
        block.render(1, 1, 19, 18, graph, bonus, bonusType);
        ImageIcon image = new ImageIcon("src//main//resources//sprites//cherry.gif");
        verify(graph).drawImage(image.getImage(), 1, 1, 19, 18, null);
    }

    /**
     * Test for rendering a Bonus item strawberry.
     */
    @Test
    void testRenderBonusStrawberry() {
        Graphics graph = mock(Graphics.class);
        Block block = new Block();
        block.addOccupant(new Bonus("strawberry"));
        Boolean bonus = true;
        String bonusType = "strawberry";
        block.render(1, 1, 19, 18, graph, bonus, bonusType);
        ImageIcon image = new ImageIcon("src//main//resources//sprites//strawberry.gif");
        verify(graph).drawImage(image.getImage(), 1, 1, 19, 18, null);
    }


}