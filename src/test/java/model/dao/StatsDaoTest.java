package model.dao;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import model.entity.Stats;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class StatsDaoTest {

    transient Connection mockConnection;
    transient PreparedStatement mockStmt;
    transient PreparedStatement mockStmt2;
    transient ResultSet mockRs;
    transient StatsDao statsDao;
    transient Stats stat;

    /**
     * Set up test environment.
     */
    @BeforeEach
    public void setUp() {

        mockConnection = Mockito.mock(Connection.class);
        mockStmt = Mockito.mock(PreparedStatement.class);
        mockStmt2 = Mockito.mock(PreparedStatement.class);
        mockRs = Mockito.mock(ResultSet.class);

        statsDao = new StatsDao(mockConnection, "statistics", mockStmt, mockStmt2, mockRs);

        stat = new Stats(151515);
        stat.setBlocksWalked(890);
        stat.setGamesPlayed(6);
        stat.setGamesWon(6);
        stat.setMaxLevel(4);
        stat.setPelletsEaten(235);
        stat.setTotalPoints(150);
        stat.setMaxScore(100);
    }

    @Test
    public void testSaveTrue() throws SQLException {
        Mockito.when(mockConnection.prepareStatement(anyString(), anyInt())).thenReturn(mockStmt);
        Mockito.doNothing().when(mockStmt).setInt(anyInt(), anyInt());
        Mockito.when(mockStmt.executeQuery()).thenReturn(mockRs);
        Mockito.when(mockRs.next()).thenReturn(false);
        Mockito.doNothing().when(mockRs).close();
        Mockito.doNothing().when(mockStmt).close();

        assertTrue(statsDao.save(stat));

        // verify
        Mockito.verify(mockConnection, times(2)).prepareStatement(anyString(), anyInt());
        Mockito.verify(mockStmt, times(9)).setInt(anyInt(), anyInt());
        Mockito.verify(mockStmt, times(1)).executeQuery();
        Mockito.verify(mockRs, times(1)).next();
        Mockito.verify(mockRs, times(1)).close();
        Mockito.verify(mockStmt, times(2)).close();
    }

    @Test
    public void testSaveThrows() throws SQLException {
        Mockito.when(mockConnection.prepareStatement(anyString(), anyInt())).thenReturn(mockStmt);
        Mockito.doNothing().when(mockStmt).setInt(anyInt(), anyInt());
        Mockito.when(mockStmt.executeQuery()).thenReturn(mockRs);
        Mockito.when(mockRs.next()).thenReturn(true).thenThrow(SQLException.class);
        Mockito.doNothing().when(mockRs).close();
        Mockito.doNothing().when(mockStmt).close();

        assertThrows(SQLException.class, () -> statsDao.save(stat));

        // verify
        Mockito.verify(mockConnection, times(2)).prepareStatement(anyString(), anyInt());
        Mockito.verify(mockStmt, times(2)).setInt(anyInt(), anyInt());
        Mockito.verify(mockStmt, times(2)).executeQuery();
        Mockito.verify(mockRs, times(2)).next();
        Mockito.verify(mockRs, times(1)).close();
        Mockito.verify(mockStmt, times(1)).close();
    }

    @Test
    public void testUpdate() throws SQLException {
        Mockito.when(mockConnection.prepareStatement(anyString(), anyInt())).thenReturn(mockStmt);
        Mockito.doNothing().when(mockStmt).setInt(anyInt(), anyInt());
        Mockito.when(mockStmt.executeQuery()).thenReturn(mockRs);
        Mockito.when(mockRs.next()).thenReturn(true);
        Mockito.when(mockRs.getInt(anyInt())).thenReturn(1);
        Mockito.doNothing().when(mockRs).close();
        Mockito.when(mockStmt.execute()).thenReturn(true);
        Mockito.doNothing().when(mockStmt).close();

        assertTrue(statsDao.update(stat));

        // verify
        Mockito.verify(mockConnection, times(2)).prepareStatement(anyString(), anyInt());
        Mockito.verify(mockStmt, times(9)).setInt(anyInt(), anyInt());
        Mockito.verify(mockStmt, times(1)).executeQuery();
        Mockito.verify(mockRs, times(1)).next();
        Mockito.verify(mockRs, times(1)).close();
        Mockito.verify(mockStmt, times(1)).close();
    }

    @Test
    public void testUpdateAltBranch() throws SQLException {
        Mockito.when(mockConnection.prepareStatement(anyString(), anyInt())).thenReturn(mockStmt);
        Mockito.doNothing().when(mockStmt).setInt(anyInt(), anyInt());
        Mockito.when(mockStmt.executeQuery()).thenReturn(mockRs);
        Mockito.when(mockRs.next()).thenReturn(true);
        Mockito.when(mockRs.getInt(anyInt())).thenReturn(151);
        Mockito.doNothing().when(mockRs).close();
        Mockito.when(mockStmt.execute()).thenReturn(true);
        Mockito.doNothing().when(mockStmt).close();

        assertTrue(statsDao.update(stat));

        // verify
        Mockito.verify(mockConnection, times(2)).prepareStatement(anyString(), anyInt());
        Mockito.verify(mockStmt, times(9)).setInt(anyInt(), anyInt());
        Mockito.verify(mockStmt, times(1)).executeQuery();
        Mockito.verify(mockRs, times(1)).next();
        Mockito.verify(mockRs, times(1)).close();
        Mockito.verify(mockStmt, times(1)).close();
    }

    @Test
    public void testGetStats() throws SQLException {
        Mockito.when(mockConnection.prepareStatement(anyString(), anyInt())).thenReturn(mockStmt);
        Mockito.doNothing().when(mockStmt).setInt(anyInt(), anyInt());
        Mockito.when(mockStmt.executeQuery()).thenReturn(mockRs);
        Mockito.when(mockRs.next()).thenReturn(true);
        Mockito.when(mockRs.getInt(anyInt())).thenReturn(1);
        Mockito.doNothing().when(mockRs).close();
        Mockito.doNothing().when(mockStmt).close();

        Stats res = statsDao.getStats(151515);
        assertTrue(res.getGamesWon() == 1);

        // verify
        Mockito.verify(mockConnection, times(1)).prepareStatement(anyString(), anyInt());
        Mockito.verify(mockStmt, times(1)).setInt(anyInt(), anyInt());
        Mockito.verify(mockStmt, times(1)).executeQuery();
        Mockito.verify(mockRs, times(1)).next();
        Mockito.verify(mockRs, times(7)).getInt(anyInt());
        Mockito.verify(mockRs, times(1)).close();
        Mockito.verify(mockStmt, times(1)).close();
    }

}
