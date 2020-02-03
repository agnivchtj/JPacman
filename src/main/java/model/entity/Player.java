package model.entity;

import model.frontend.Level;

public interface Player {
    /**
     * The tick method runs when a key is clicked.
     * @throws Exception this is for any Exception.
     */
    void tick() throws Exception;


    /**
     * The method that handles the collisions for pacman.
     *
     * @return whether there is collision or not.
     * @throws Exception this is for any Exception.
     */
    boolean collision(int x, int y, Level level) throws Exception;
}
