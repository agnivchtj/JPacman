package model.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.backend.PasswordHasher;
import model.entity.Stats;
import model.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class UserDaoTest {

    transient Connection mockConnection;
    transient PreparedStatement mockStmt;
    transient ResultSet mockRs;
    transient UserDao userDao;
    transient User user;
    transient PasswordHasher passwordHasher;

    /**
     * Set up test environment.
     */
    @BeforeEach
    public void setUp() {

        mockConnection = Mockito.mock(Connection.class);
        mockStmt = Mockito.mock(PreparedStatement.class);
        mockRs = Mockito.mock(ResultSet.class);

        userDao = new UserDao(mockConnection, "user", mockStmt, mockRs);

        passwordHasher = new PasswordHasher();
        user = new User("CaptainMarvel");
        user.setHashedPassword(passwordHasher.hashPassword("CaptainMarvel"));
        user.setUserId(151515);
        user.setStats(new Stats(user.getUserId()));
    }

    @Test
    public void testSaveTrue() throws SQLException {
        Mockito.when(mockConnection.prepareStatement(anyString(), anyInt())).thenReturn(mockStmt);
        Mockito.doNothing().when(mockStmt).setString(anyInt(), anyString());
        Mockito.doNothing().when(mockStmt).setInt(anyInt(), anyInt());
        Mockito.when(mockStmt.execute()).thenReturn(true);
        Mockito.when(mockStmt.executeQuery()).thenThrow(SQLException.class);
        Mockito.doNothing().when(mockStmt).close();

        assertTrue(userDao.save(user));
        assertEquals(user, userDao.getLoggedUser());

        // verify
        Mockito.verify(mockConnection, times(2)).prepareStatement(anyString(), anyInt());
        Mockito.verify(mockStmt, times(3)).setString(anyInt(), anyString());
        Mockito.verify(mockStmt, times(1)).setInt(anyInt(), anyInt());
        Mockito.verify(mockStmt, times(1)).execute();
        Mockito.verify(mockStmt, times(1)).close();
    }

    @Test
    public void testSaveFalse() throws SQLException {
        Mockito.when(mockConnection.prepareStatement(anyString(), anyInt())).thenReturn(mockStmt);
        Mockito.doNothing().when(mockStmt).setString(anyInt(), anyString());
        Mockito.when(mockStmt.executeQuery()).thenReturn(mockRs);
        Mockito.when(mockRs.next()).thenReturn(true);
        Mockito.when(mockRs.getString(anyInt())).thenReturn("CaptainMarvel");
        Mockito.when(mockRs.getInt(anyInt())).thenReturn(151515);
        Mockito.doNothing().when(mockRs).close();
        Mockito.doNothing().when(mockStmt).close();

        assertFalse(userDao.save(user));

        // verify
        Mockito.verify(mockConnection, times(1)).prepareStatement(anyString(), anyInt());
        Mockito.verify(mockStmt, times(1)).setString(anyInt(), anyString());
        Mockito.verify(mockStmt, times(1)).executeQuery();
        Mockito.verify(mockRs, times(1)).next();
        Mockito.verify(mockRs, times(2)).getString(anyInt());
        Mockito.verify(mockRs, times(1)).getInt(anyInt());
        Mockito.verify(mockRs, times(1)).close();
        Mockito.verify(mockStmt, times(1)).close();
    }

    @Test
    public void testGetUserByUsernameAndPasswordTrue() throws Exception {
        Mockito.when(mockConnection.prepareStatement(anyString(), anyInt())).thenReturn(mockStmt);
        Mockito.doNothing().when(mockStmt).setString(anyInt(), anyString());
        Mockito.when(mockStmt.executeQuery()).thenReturn(mockRs);
        Mockito.when(mockRs.next()).thenReturn(true);
        Mockito.when(mockRs.getInt(anyInt())).thenReturn(151515);
        Mockito.when(mockRs.getString(anyInt())).thenReturn(user.getHashedPassword());
        Mockito.doNothing().when(mockRs).close();
        Mockito.doNothing().when(mockStmt).close();

        User usr = userDao.getUserByUsernameAndPassword(user.getUsername(), user.getUsername());

        assertEquals(user, usr);

        // verify
        Mockito.verify(mockConnection, times(1)).prepareStatement(anyString(), anyInt());
        Mockito.verify(mockStmt, times(1)).setString(anyInt(), anyString());
        Mockito.verify(mockStmt, times(1)).executeQuery();
        Mockito.verify(mockRs, times(1)).next();
        Mockito.verify(mockRs, times(1)).getInt(anyInt());
        Mockito.verify(mockRs, times(1)).getString(anyInt());
        Mockito.verify(mockRs, times(1)).close();
        Mockito.verify(mockStmt, times(1)).close();
    }

    @Test
    public void testGetUserByUsernameAndPasswordFalse() throws Exception {
        Mockito.when(mockConnection.prepareStatement(anyString(), anyInt())).thenReturn(mockStmt);
        Mockito.doNothing().when(mockStmt).setString(anyInt(), anyString());
        Mockito.when(mockStmt.executeQuery()).thenReturn(mockRs);
        Mockito.when(mockRs.next()).thenReturn(true);
        Mockito.when(mockRs.getInt(anyInt())).thenReturn(151515);
        Mockito.when(mockRs.getString(anyInt())).thenReturn(user.getHashedPassword());
        Mockito.doNothing().when(mockRs).close();
        Mockito.doNothing().when(mockStmt).close();

        User usr = userDao.getUserByUsernameAndPassword(user.getUsername(),
                user.getHashedPassword());

        assertNull(usr);

        // verify
        Mockito.verify(mockConnection, times(1)).prepareStatement(anyString(), anyInt());
        Mockito.verify(mockStmt, times(1)).setString(anyInt(), anyString());
        Mockito.verify(mockStmt, times(1)).executeQuery();
        Mockito.verify(mockRs, times(1)).next();
        Mockito.verify(mockRs, times(1)).getInt(anyInt());
        Mockito.verify(mockRs, times(1)).getString(anyInt());
        Mockito.verify(mockRs, times(1)).close();
        Mockito.verify(mockStmt, times(1)).close();
    }

    @Test
    public void testGetLastUserIdAdded() throws Exception {
        Mockito.when(mockConnection.prepareStatement(anyString(), anyInt())).thenReturn(mockStmt);
        Mockito.when(mockStmt.executeQuery()).thenReturn(mockRs);
        Mockito.when(mockRs.next()).thenReturn(true);
        Mockito.when(mockRs.getInt(anyInt())).thenReturn(151515);
        Mockito.doNothing().when(mockRs).close();
        Mockito.doNothing().when(mockStmt).close();

        assertTrue(userDao.getLastUserIdAdded() == 151515);

        // verify
        Mockito.verify(mockConnection, times(1)).prepareStatement(anyString(), anyInt());
        Mockito.verify(mockStmt, times(1)).executeQuery();
        Mockito.verify(mockRs, times(1)).next();
        Mockito.verify(mockRs, times(1)).getInt(anyInt());
        Mockito.verify(mockRs, times(1)).close();
        Mockito.verify(mockStmt, times(1)).close();
    }

}
