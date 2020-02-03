package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.entity.Leaderboard;

/**
 * This class handles communication with the Leaderboard table in the database.
 */
// All resources are closed.
@SuppressWarnings("PMD.CloseResource")
public class LeaderboardDao extends Dao<Leaderboard> {

    private transient PreparedStatement stmt;
    private transient ResultSet rs;

    /**
     * Production constructor.
     */
    public LeaderboardDao(Connection connection, String tableName) {

        super(connection, tableName);

    }

    /**
     * Testing constructor.
     */
    protected LeaderboardDao(Connection connection, String tableName, PreparedStatement stmt,
                             ResultSet rs) {

        super(connection, tableName);
        this.stmt = stmt;
        this.rs = rs;

    }

    /**
     * Get top scores in Leaderboard table.
     * @return List with top5 entries in Leaderboard.
     */
    // leaderboard is clearly defined and initialised.
    @SuppressWarnings("PMD.DataflowAnomalyAnalysis")
    public List<Leaderboard> getLeaderboard() throws SQLException {

        List<Leaderboard> leaderboard = new ArrayList<>();

        try {

            String query = ("SELECT username, score FROM leaderboard ORDER BY score DESC LIMIT 5;");
            stmt = connection.prepareStatement(query, 0);
            rs = stmt.executeQuery();

            for (int i = 0; i < 5; i++) {
                rs.next();
                String username = rs.getString(1);
                int points = rs.getInt(2);
                Leaderboard entry = new Leaderboard(username);
                entry.setPoints(points);
                leaderboard.add(entry);
            }

            // close objects
            rs.close();
            stmt.close();

        } catch (Exception e) {
            throw e;
        }

        return leaderboard;
    }

}
