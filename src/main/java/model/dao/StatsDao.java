package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.entity.Stats;

/**
 * This class handles communication with the Stats table in the database.
 */
// All resources are closed.
@SuppressWarnings("PMD.CloseResource")
public class StatsDao extends Dao<Stats> {

    private transient PreparedStatement stmt;
    private transient PreparedStatement stmt2;
    private transient ResultSet rs;

    /**
     * Production constructor.
     */
    public StatsDao(Connection connection, String tableName) {
        super(connection, tableName);
    }

    /**
     * Testing constructor.
     */
    protected StatsDao(Connection connection, String tableName, PreparedStatement stmt,
                       PreparedStatement stmt2, ResultSet rs) {
        super(connection, tableName);
        this.stmt = stmt;
        this.stmt2 = stmt2;
        this.rs = rs;
    }

    /**
     * Add a new stat to the database.
     * @param stat The Stats object to be added
     * @return true if the stat was saved to the database
     * @throws SQLException otherwise
     */
    public boolean save(Stats stat) throws SQLException {

        try {

            String query = ("SELECT * FROM statistics WHERE id_user = ?;");
            stmt = connection.prepareStatement(query, 0);
            stmt.setInt(1, stat.getUserId());
            rs = stmt.executeQuery();

            if (rs.next()) {
                stmt.close();
                rs.close();
                return update(stat);
            }

            stmt.close();
            rs.close();

            query = ("INSERT INTO statistics (id_user, points, blocks_walked, pallets_eaten, "
                    + "games_played, game_won, max_level, max_score) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?);");
            stmt2 = connection.prepareStatement(query, 0);
            stmt2.setInt(1, stat.getUserId());
            stmt2.setInt(2, stat.getTotalPoints());
            stmt2.setInt(3, stat.getBlocksWalked());
            stmt2.setInt(4, stat.getPelletsEaten());
            stmt2.setInt(5, stat.getGamesPlayed());
            stmt2.setInt(6, stat.getGamesWon());
            stmt2.setInt(7, stat.getMaxLevel());
            stmt2.setInt(8, stat.getMaxScore());
            stmt2.execute();
            stmt2.close();

            return true;

        } catch (SQLException e) {
            throw e;
        }
    }

    /**
     * Updates an existing stat in the database.
     * @param stat The Stat object containing the stats to be updated
     * @return true if the stat was updated
     * @throws SQLException otherwise
     */
    public boolean update(Stats stat) throws SQLException {

        try {

            String query = ("SELECT * FROM statistics WHERE id_user = ?;");
            stmt = connection.prepareStatement(query, 0);
            stmt.setInt(1, stat.getUserId());
            rs = stmt.executeQuery();
            rs.next();
            final int points = stat.getTotalPoints() + rs.getInt(2);
            final int blocks = stat.getBlocksWalked() + rs.getInt(3);
            final int pellets = stat.getPelletsEaten() + rs.getInt(4);
            final int gamesPlayed = stat.getGamesPlayed() + rs.getInt(5);
            final int gamesWon = stat.getGamesWon() + rs.getInt(6);

            int maxLevel;
            if (stat.getMaxLevel() > rs.getInt(7)) {
                maxLevel = stat.getMaxLevel();
            } else {
                maxLevel = rs.getInt(7);
            }

            int maxScore;
            if (stat.getMaxScore() > rs.getInt(8)) {
                maxScore = stat.getMaxScore();
            } else {
                maxScore = rs.getInt(8);
            }

            query = ("UPDATE statistics SET points = ?, blocks_walked = ?, pallets_eaten = ?, "
                    + "games_played= ?, game_won = ?, max_level = ?, "
                    + "max_score = ? WHERE id_user = ?;");
            stmt = connection.prepareStatement(query, 0);
            stmt.setInt(1, points);
            stmt.setInt(2, blocks);
            stmt.setInt(3, pellets);
            stmt.setInt(4, gamesPlayed);
            stmt.setInt(5, gamesWon);
            stmt.setInt(6, maxLevel);
            stmt.setInt(7, maxScore);
            stmt.setInt(8, stat.getUserId());
            stmt.execute();

            rs.close();
            stmt.close();

            return true;

        } catch (SQLException e) {
            throw e;
        }

    }

    /**
     * Gets the stats for the given userId.
     * @param userId The id of user
     * @return The stats for that user
     * @throws SQLException otherwise
     */
    public Stats getStats(int userId) throws SQLException {

        Stats stat = new Stats(userId);

        try {

            String query = ("SELECT * FROM statistics WHERE id_user = ?;");
            stmt = connection.prepareStatement(query, 0);
            stmt.setInt(1, userId);
            rs = stmt.executeQuery();
            rs.next();
            stat.setTotalPoints(rs.getInt(2));
            stat.setBlocksWalked(rs.getInt(3));
            stat.setPelletsEaten(rs.getInt(4));
            stat.setGamesPlayed(rs.getInt(5));
            stat.setGamesWon(rs.getInt(6));
            stat.setMaxLevel(rs.getInt(7));
            stat.setMaxScore(rs.getInt(8));

            // close objects
            rs.close();
            stmt.close();

        } catch (Exception e) {
            throw e;
        }

        return stat;

    }

}
