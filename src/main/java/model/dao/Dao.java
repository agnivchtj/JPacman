package model.dao;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * This class sets the methods to be implemented by Dao classes.
 * @param <T> The type of the Dao.
 */
public abstract class Dao<T> {

    protected final transient String tableName;
    protected transient Connection connection;

    /**
     * Constructor.
     * @param connection The connection to the databased to be used.
     * @param tableName The name of the table in the database.
     */
    protected Dao(Connection connection, String tableName) {

        this.tableName = tableName;
        this.connection = connection;

    }

}
