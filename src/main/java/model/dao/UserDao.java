package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.backend.PasswordHasher;
import model.entity.User;

/**
 * This class handles communication with the User table in the database.
 */
// All resources are closed.
@SuppressWarnings("PMD.CloseResource")
public class UserDao extends Dao<User> {

    private transient PasswordHasher passwordHasher;
    private transient User loggedUser;
    private transient PreparedStatement stmt;
    private transient ResultSet rs;

    /**
     * Production constructor.
     */
    public UserDao(Connection connection, String tableName) {

        super(connection, tableName);
        passwordHasher = new PasswordHasher();

    }

    /**
     * Testing constructor.
     */
    protected UserDao(Connection connection, String tableName, PreparedStatement stmt,
                      ResultSet rs) {

        super(connection, tableName);
        passwordHasher = new PasswordHasher();
        this.stmt = stmt;
        this.rs = rs;

    }

    /**
     * Getter for loggedUser.
     * @return the User currently logged in
     */
    public User getLoggedUser() {
        return loggedUser;
    }

    /**
     * Add a new user to the database.
     * @param user The User to be added
     * @return true if the user was saved
     * @throws SQLException otherwise
     */
    // The objects are used to assert existence of a user with same username on database.
    @SuppressWarnings("PMD.DataflowAnomalyAnalysis")
    public boolean save(User user) throws SQLException {

        try {

            User usr = getUserByUsername(user.getUsername());
            return false;

        } catch (SQLException e) {

            String query = ("INSERT INTO user (iduser, username, password) VALUES(? , ? , ?)");
            stmt = connection.prepareStatement(query, 0);
            stmt.setInt(1, user.getUserId());
            stmt.setString(2, user.getUsername());
            stmt.setString(3, user.getHashedPassword());
            stmt.execute();
            stmt.close();
            this.loggedUser = user;
            return true;

        }
    }

    /**
     * Gets the user from the database that matches the input details.
     * @param username The username to be searched
     * @return The User object
     * @throws Exception when the username does not exist
     */
    public User getUserByUsername(String username) throws SQLException {

        String query = ("SELECT * FROM user WHERE username = ?;");
        stmt = connection.prepareStatement(query, 0);
        stmt.setString(1, username);
        rs = stmt.executeQuery();
        rs.next();
        User usr = new User(rs.getString(2));
        usr.setUserId(rs.getInt(1));
        usr.setHashedPassword(rs.getString(3));

        // close objects
        rs.close();
        stmt.close();

        return usr;
    }

    /**
     * Gets the user details from the DB that matches the input details.
     * Creates a local instance of User from the DB details.
     * @param username The username to be searched
     * @param password The password to be checked
     * @return The User object created
     */
    // The object is defined by the result from the query.
    @SuppressWarnings("PMD.DataflowAnomalyAnalysis")
    public User getUserByUsernameAndPassword(String username, String password) throws Exception {

        try {

            String query = ("SELECT iduser, password FROM user WHERE username = ?;");
            stmt = connection.prepareStatement(query, 0);
            stmt.setString(1, username);
            rs = stmt.executeQuery();
            rs.next();
            int id = rs.getInt(1);
            String hashedPassword = rs.getString(2);

            // close objects
            rs.close();
            stmt.close();

            if (passwordHasher.verifyPassword(password, hashedPassword)) {

                User usr = new User(username);
                usr.setHashedPassword(hashedPassword);
                usr.setUserId(id);
                this.loggedUser = usr;
                return usr;

            }

        } catch (Exception e) {
            throw new Exception("Invalid login details");
        }

        return null;
    }

    /**
     * Getter for the last user added to the DB.
     * @return the last user added to the DB
     */
    public int getLastUserIdAdded() throws SQLException {
        String query = ("SELECT MAX(iduser) FROM user;");
        stmt = connection.prepareStatement(query, 0);
        rs = stmt.executeQuery();
        rs.next();
        int id = rs.getInt(1);

        // close objects
        rs.close();
        stmt.close();

        return id;
    }

}
