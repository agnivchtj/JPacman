package model.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * This class handles communication with the database.
 */
public class DaoManager {

    private transient Connection connection;

    /**
     * Constructor.
     */
    public DaoManager() {

    }

    /**
     * Creates a connection to the database that is used internally.
     * @return Returns a database connection.
     * @throws SQLException SQL error
     * @throws ClassNotFoundException JDBC Driver error
     */
    private Connection openConnection() throws SQLException, ClassNotFoundException {

        Class.forName("com.mysql.cj.jdbc.Driver");
        this.connection = DriverManager.getConnection(
                    "jdbc:mysql://projects-db.ewi.tudelft.nl:3306/projects_PacMan27"
                            + "?serverTimezone=UTC", "pu_PacMan27", "Xc5cOwzFsyzX");

        return this.connection;

    }

    /**
     * !!!! Strictly for testing purposes !!!!
     * Getter for connection.
     * @return The connection
     */
    protected Connection getConnection() {
        return this.connection;
    }

    /**
     * !!!! Strictly for testing purposes !!!!
     * Setter for the connection.
     * @param connection The connection that should be used
     */
    protected void setConnection(Connection connection) {
        this.connection = connection;
    }

    /**
     * Opens the database connection.
     * @throws Exception Throws a caught connection exception.
     */
    public void open() throws Exception {

        try {

            if (this.connection == null || this.connection.isClosed()) {
                this.connection = openConnection();

            }
        } catch (Exception e) {
            throw e;
        }

    }

    /**
     * Closes the database connection.
     * @throws Exception Throws a caught connection exception.
     */
    public void close() throws Exception {

        try {

            if (this.connection != null && !this.connection.isClosed()) {
                this.connection.close();

            }
        } catch (Exception e) {
            throw e;
        }

    }

    /**
     * Part of Factory Design Pattern for managing Dao creation.
     */
    public enum Table { USER, STATS, LEADERBOARD, TEST }

    /**
     * Manages Dao creation using the Factory Design Pattern.
     * @param table Table entry for desired Dao type.
     * @return Returns the desired Dao object.
     * @throws Exception Throws a caught connection exception.
     */
    public Dao getDao(Table table) throws Exception {

        try {

            if (this.connection == null || this.connection.isClosed()) {
                this.open();
            }

        } catch (Exception e) {
            throw e;
        }

        switch (table) {

            case USER:
                return new UserDao(this.connection, "user");
            case STATS:
                return new StatsDao(this.connection, "statistics");
            case LEADERBOARD:
                return new LeaderboardDao(this.connection, "leaderboard");
            default:
                throw new Exception("This table does not exist.");

        }

    }

    /**
     * Best practice for Java Object.finalize.
     * @throws Throwable Throws a caught Throwable.
     */
    @Override
    // This is best practice code for dealing with finalizer method.
    @SuppressWarnings("PMD.AvoidCatchingThrowable")
    protected void finalize() throws Throwable {

        try {
            this.close();
        } catch (Throwable t) {
            throw t;
        } finally {
            super.finalize();
        }
    }

}
