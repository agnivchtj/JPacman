package model.frontend;

import model.dao.DaoManager;
import model.dao.StatsDao;
import model.entity.*;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;

/**
 * The game class.
 */
@SuppressWarnings("PMD")
public class PlayGame extends GameProperties implements Runnable, KeyListener {
    private boolean running;
    private transient Thread thread;
    public static Level level;
    private static Pacman player;
    private MapParser mapparser;
    private transient JButton pauseButton;
    private static transient JLabel points;
    private static transient JLabel remainingPellets;
    private static boolean paused;
    private transient User user;
    private transient Stats stat;
    private transient DaoManager daoManager;

    /**
     * Constructor for the class.
     */
    public PlayGame(User user) {
        this.user = user;
        this.stat = new Stats(user.getUserId());
        build();
    }

    /**
     * Start the method.
     */
    public void start() {
        if (running) {
            return;
        }

        setRunning(true);
        thread = new Thread(this);
        thread.start();
    }

    /**
     * Stop the method.
     */
    public void stop() {
        if (!running) {
            return;
        }
        setRunning(false);
        updateStatistics();

        try {
            saveStats();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Class that keeps the game running (w/ ticks).
     */
    @Override
    public void run() {
        if (isDisplayable()) {
            requestFocus();

            double targetTick = 60.0;

            double nsPerTick = 1000000000 / targetTick;

            long lastTime = System.nanoTime(); //we're gonna get it in nanoseconds
            long timer = System.currentTimeMillis();

            int fps = 0;
            double delta = 0;

            while (running) {
                long now = System.nanoTime();
                delta = delta + ((now - lastTime) / nsPerTick);

                while (delta >= 1) {
                    try {
                        Thread.sleep(150);
                        tick();
                        render();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    fps++;
                    delta--;
                }
                if (System.currentTimeMillis() - timer >= 1000) {
                    fps = 0;
                    timer += 1000;
                }
            }
            stop();
        }
    }

    /**
     * tick() method.
     * @throws Exception this is for any Exception.
     */
    private void tick() throws Exception {
        if (paused) {
            thread.sleep(1);
        } else {
            player.tick();
            if (player.getState().stop()) {
                stop();
            }
            getPoints();
            getRemainingPellets();
        }
    }

    /**
     * Pause the game. When clicking on the PauseButton the game is either paused when running and
     * resumed when paused.
     */
    private void pause() {
        this.pauseButton.addActionListener(e -> {
            if (pauseButton.getText().equals("Pause")) {
                pauseButton.setText("Resume");
                this.paused = true;
            } else if (pauseButton.getText().equals("Resume")) {
                this.pauseButton.setText("Pause");
                this.paused = false;
            }
        });
        pauseButton.setRequestFocusEnabled(false);
    }

    /**
     * Sets the text for the point label.
     */
    private void getPoints() {
        points.setText("point:" + scoreCalculator());
    }

    /**
     * Sets the text for the remaining pellets label.
     */
    private void getRemainingPellets() {
        remainingPellets.setText("remaining pellets:" + level.pellets.size());
    }

    //Updating statistics
    private void updateStatistics() {
        this.stat.setMaxLevel(1);
        this.stat.setTotalPoints(scoreCalculator());
        this.stat.setBlocksWalked(player.getBlockCount());
        this.stat.setPelletsEaten(player.getPellets().size());
        this.stat.setGamesPlayed(1);
        if (player.getState() == State.WON) {
            this.stat.setGamesWon(1);
        }
        this.stat.setMaxScore(scoreCalculator());
    }

    //Save to database
    private void saveStats() throws Exception {
        this.daoManager = new DaoManager();
        StatsDao statsDao = (StatsDao) daoManager.getDao(DaoManager.Table.STATS);
        statsDao.update(this.stat);
        this.daoManager.close();
    }

    /**
     * render() method for game class.
     */
    public void render() throws Exception {
        BufferStrategy buffer = getBufferStrategy();
        if (buffer == null) {
            createBufferStrategy(3);
            return;
        }
        Graphics graph = buffer.getDrawGraphics();
        graph.setColor(Color.black);
        graph.fillRect(0, 0, PlayGame.WIDTH, PlayGame.HEIGHT);
        level.render(graph);

        if (player.getState() == State.LOST) {
            ImageIcon lostimage = new ImageIcon("src//main//resources//gamelost.png");
            graph.drawImage(lostimage.getImage(), 50, 100, 500, 100, null);
        }
        if (player.getState() == State.WON) {
            ImageIcon lostimage = new ImageIcon("src//main//resources//gamewon.png");
            graph.drawImage(lostimage.getImage(), 50, 100, 500, 100, null);
        }

        graph.dispose();
        buffer.show();
    }

    /**
     * Start the game application.
     */
    public void play() {
        PlayGame game = new PlayGame(user);
        boundary();
        JFrame frame = new JFrame();
        frame.add(pauseButton);
        frame.add(points);
        frame.add(remainingPellets);
        frame.setTitle(PlayGame.title);

        frame.add(game);
        frame.setResizable(false);
        frame.setSize(WIDTH, HEIGHT);
        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        game.start();
        pause();
    }



    /**
     * The fixed boundaries of the buttons in the game.
     */
    public void boundary()
    {
        pauseButton.setBounds(500, 385, 80, 20);
        points.setBounds(300, 385, 80, 20);
        remainingPellets.setBounds(50, 385, 200, 20);
    }

    /**
     * Type controls for the game.
     * To be implemented.
     * @param e the input of player.
     */
    @Override
    public void keyTyped(KeyEvent e) {
    }

    /**
     * Key controls for the game.
     * @param enter the input, or key pressed by player.
     */
    @Override
    public void keyPressed(KeyEvent enter) {
        if (enter.getKeyCode() == KeyEvent.VK_RIGHT) {
            player.setWalking(true);
            player.setDirection(Direction.RIGHT);
        }
        if (enter.getKeyCode() == KeyEvent.VK_LEFT) {
            player.setWalking(true);
            player.setDirection(Direction.LEFT);
        }
        if (enter.getKeyCode() == KeyEvent.VK_UP) {
            player.setWalking(true);
            player.setDirection(Direction.UP);
        }
        if (enter.getKeyCode() == KeyEvent.VK_DOWN) {
            player.setWalking(true);
            player.setDirection(Direction.DOWN);
        }
    }

    /**
     * Releasing controls.
     * @param e the input.
     */
    @Override
    public void keyReleased(KeyEvent e) {
    }

    /**
     * Getter method for the running boolean.
     * @return value of running
     */
    public boolean isRunning() {
        return running;
    }

    /**
     * Setter method for the isRunning boolean.
     * @param newRunning if the game is in progress true, else false
     */
    public void setRunning(boolean newRunning) {
        running = newRunning;
    }

    /**
     * Setter method for the paused boolean.
     * @param paused if the game is paused, else false
     */
    public static void setPaused(boolean paused) {
        PlayGame.paused = paused;
    }

    /**
     * Setter method for the pauseButton.
     * @param newpauseButton the new PauseButton.
     */
    public void setPauseButton(JButton newpauseButton) {
        pauseButton = newpauseButton;
    }

    /**
     * Getter for the Pacman.
     * @return the current player.
     */
    public static Pacman getPlayer() {
        return player;
    }

    /**
     * Calculates the score of the player.
     * @return the score.
     */
    private int scoreCalculator() {
        Pellet pellet = new Pellet();
        int pelletValue = pellet.getValue();
        int pelletScore = player.getPellets().size() * pelletValue;
        return pelletScore;
    }

    /**
     * A build method that is used when the game is played.
     */
    public void build()
    {
        Dimension dimension = new Dimension(PlayGame.WIDTH, PlayGame.HEIGHT);
        setPreferredSize(dimension);
        setMinimumSize(dimension);
        setMaximumSize(dimension);
        addKeyListener(this);
        try {
            mapparser = new MapParser("/board.txt");
        } catch (Exception e) {
            e.printStackTrace();
        }
        level = this.mapparser.getLevel();
        player = new Pacman(
                (mapparser.getX_Coordinate() * (WIDTH - 30) / 30),
                (mapparser.getY_Coordinate() * (HEIGHT - 70)) / 20
        );
        level.getBoard().getGrid()[
                mapparser.getX_Coordinate()][
                mapparser.getY_Coordinate()].addOccupant(player);
        Block pacManBlock = level.getBoard().getGrid()[
                mapparser.getX_Coordinate()][
                mapparser.getY_Coordinate()];
        level.getBoard().setPacmanBlock(pacManBlock);
        setPauseButton(new JButton("Pause"));
        points = new JLabel("point: ");
        remainingPellets = new JLabel("remaining pellets: ");
        setRunning(defaultRunning);
        setPaused(defaultPaused);
    }
}

