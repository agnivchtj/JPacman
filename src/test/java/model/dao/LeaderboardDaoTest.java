package model.dao;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import model.entity.Leaderboard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class LeaderboardDaoTest {

    transient Connection mockConnection;
    transient PreparedStatement mockStmt;
    transient ResultSet mockRs;
    transient LeaderboardDao leaderboardDao;
    transient Leaderboard leaderboard;

    /**
     * Set up test environment.
     */
    @BeforeEach
    public void setupTestEnvironment() {

        mockConnection = Mockito.mock(Connection.class);
        mockStmt = Mockito.mock(PreparedStatement.class);
        mockRs = Mockito.mock(ResultSet.class);

        leaderboardDao = new LeaderboardDao(mockConnection, "leaderboard", mockStmt, mockRs);

        leaderboard = new Leaderboard("CaptainMarvel");
        leaderboard.setPoints(500);
    }

    @Test
    public void testGetLeaderboard() throws SQLException {
        Mockito.when(mockConnection.prepareStatement(anyString(), anyInt())).thenReturn(mockStmt);
        Mockito.when(mockStmt.executeQuery()).thenReturn(mockRs);
        Mockito.when(mockRs.next()).thenReturn(true).thenReturn(true).thenReturn(true)
                .thenReturn(true).thenReturn(true).thenReturn(false);
        Mockito.when(mockRs.getString(anyInt())).thenReturn("CaptainMarvel");
        Mockito.when(mockRs.getInt(anyInt())).thenReturn(500);
        Mockito.doNothing().when(mockRs).close();
        Mockito.doNothing().when(mockStmt).close();

        List<Leaderboard> leaderboards = leaderboardDao.getLeaderboard();
        assertTrue(leaderboards.size() == 5);

        // verify
        Mockito.verify(mockConnection, times(1)).prepareStatement(anyString(), anyInt());
        Mockito.verify(mockStmt, times(1)).executeQuery();
        Mockito.verify(mockRs, times(5)).next();
        Mockito.verify(mockRs, times(5)).getString(anyInt());
        Mockito.verify(mockRs, times(5)).getInt(anyInt());
        Mockito.verify(mockStmt, times(1)).close();
        Mockito.verify(mockRs, times(1)).close();

    }

}
