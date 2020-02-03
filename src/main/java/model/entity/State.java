package model.entity;

/**
 * Enum for the state of the game; either Won, Lost, or the game is still Running.
 */
public enum State {
    WON(true),
    LOST(true),
    RUNNING(false);
    private final boolean stop;

    State(boolean stop) {
        this.stop = stop;
    }

    /**
     * Boolean for the state of the game.
     * @return boolean if the game is stopped or still running.
     */
    public boolean stop() {
        return stop;
    }
}
