package model.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.times;

import java.sql.Connection;
import java.sql.SQLException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

// The objects are properly initialised and instantiated.
@SuppressWarnings({"PMD.CloseResource", "PMD.DataflowAnomalyAnalysis"})
public class DaoManagerTest {

    transient DaoManager daoManager;
    transient Connection mockConnection;

    @BeforeEach
    void setupTestEnvironment() {

        mockConnection = Mockito.mock(Connection.class);
        daoManager = new DaoManager();
        daoManager.setConnection(mockConnection);

    }

    @AfterEach
    void tearDownTestEnvironment() throws Exception {

        daoManager.close();

    }

    @Test
    void testConstructor() {
        assertNotNull(daoManager);
    }

    @Test
    void testGetConnection() throws Exception {

        Mockito.when(mockConnection.isValid(anyInt())).thenReturn(true);

        Connection connection = daoManager.getConnection();
        assertTrue(connection.isValid(5));

        // verify
        Mockito.verify(mockConnection, times(1)).isValid(anyInt());

    }

    @Test
    void testOpen() throws Exception {

        Mockito.when(mockConnection.isClosed()).thenReturn(false);
        Mockito.when(mockConnection.isValid(anyInt())).thenReturn(true);

        daoManager.open();
        assertTrue(daoManager.getConnection().isValid(5));

        // verify
        Mockito.verify(mockConnection, times(1)).isClosed();
        Mockito.verify(mockConnection, times(1)).isValid(anyInt());

    }

    @Test
    void testCloseTrue() throws Exception {

        Mockito.when(mockConnection.isClosed()).thenReturn(false);

        Connection connection = daoManager.getConnection();
        daoManager.close();

        Mockito.when(mockConnection.isClosed()).thenReturn(true);
        assertTrue(connection.isClosed());

        // verify
        Mockito.verify(mockConnection, times(2)).isClosed();
        Mockito.verify(mockConnection, times(1)).close();

    }

    @Test
    void testCloseAlreadyClosed() throws Exception {

        Mockito.when(mockConnection.isClosed()).thenReturn(false);

        final Connection connection = daoManager.getConnection();
        daoManager.close();

        Mockito.when(mockConnection.isClosed()).thenReturn(true);
        daoManager.close();

        assertTrue(connection.isClosed());

        // verify
        Mockito.verify(mockConnection, times(3)).isClosed();
        Mockito.verify(mockConnection, times(1)).close();

    }

    @Test
    void testCloseNull() throws Exception {

        daoManager.setConnection(null);
        Connection connection = daoManager.getConnection();
        daoManager.close();

        assertNull(connection);

    }

    @Test
    void testGetDaoUserDao() throws Exception {

        Mockito.when(mockConnection.isClosed()).thenReturn(true).thenReturn(false);

        UserDao userDao = (UserDao) daoManager.getDao(DaoManager.Table.USER);
        assertNotNull(userDao);

        // verify
        Mockito.verify(mockConnection, times(2)).isClosed();

    }

    @Test
    void testGetDaoStatsDao() throws Exception {

        Mockito.when(mockConnection.isClosed()).thenReturn(false);

        StatsDao statsDao = (StatsDao) daoManager.getDao(DaoManager.Table.STATS);
        assertNotNull(statsDao);

        // verify
        Mockito.verify(mockConnection, times(1)).isClosed();

    }

    @Test
    void testGetDaoLeaderboardDao() throws Exception {

        Mockito.when(mockConnection.isClosed()).thenReturn(false);

        LeaderboardDao leaderboardDao = (LeaderboardDao)
                daoManager.getDao(DaoManager.Table.LEADERBOARD);
        assertNotNull(leaderboardDao);

        // verify
        Mockito.verify(mockConnection, times(1)).isClosed();

    }

    @Test
    void testGetDaoInexistentDao() throws SQLException {

        Mockito.when(mockConnection.isClosed()).thenReturn(false);

        Exception exception = assertThrows(Exception.class, () ->
                daoManager.getDao(DaoManager.Table.TEST));

        assertEquals("This table does not exist.", exception.getMessage());

        // verify
        Mockito.verify(mockConnection, times(1)).isClosed();

    }

    // The objects are properly initialised and instantiated.
    @SuppressWarnings("PMD.AvoidCallingFinalize")
    @Test
    void testFinalize() throws Throwable {

        Mockito.when(mockConnection.isClosed()).thenReturn(false);
        Mockito.doNothing().when(mockConnection).close();

        Connection connection = daoManager.getConnection();

        daoManager.finalize();

        Mockito.when(mockConnection.isClosed()).thenReturn(true);
        assertTrue(connection.isClosed());

        // verify
        Mockito.verify(mockConnection, times(2)).isClosed();
        Mockito.verify(mockConnection, times(1)).close();

    }


}
