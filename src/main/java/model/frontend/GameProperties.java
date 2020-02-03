package model.frontend;

import java.awt.Canvas;

/**
 * Abstract class that contains Game Properties for the PlayGame class.
 */
public abstract class GameProperties extends Canvas {
    public static final long serialVersionUID = 1L;
    public static final int WIDTH = 600;
    public static final int HEIGHT = 450;
    public static final String title = "PacMan";
    public final boolean defaultPaused = false;
    public final boolean defaultRunning = false;
}
