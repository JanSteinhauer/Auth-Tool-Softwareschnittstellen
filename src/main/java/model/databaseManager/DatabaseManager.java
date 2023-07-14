package model.databaseManager;

import javafx.collections.ObservableList;
import model.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**

 An abstract class that provides a common interface for managing a specific type of object

 in a database. The class uses a {@link Connection} object to communicate with the database,

 and defines a common set of methods for retrieving and manipulating data.

 @param <T> the type of object that is managed by this {@code DatabaseManager}
 */
 public abstract class DatabaseManager<T> {
 /**
  The connection object used to communicate with the database.
 */
private final Connection connection;

/**
 The prepared statement object used to execute queries against the database.
 */
private PreparedStatement stmt;

/**
 Constructs a new {@code DatabaseManager} object with a connection to the database.
 The connection is retrieved from a {@link Database} object.
 */
public DatabaseManager() {
        this.connection = Database.getInstance().conn;
        }

/**
 Returns the {@link Connection} object used by this {@code DatabaseManager} to communicate
 with the database.
 @return the {@code Connection} object
 */
protected Connection getConnection() {
        return connection;
        }

/**
 Returns the {@link PreparedStatement} object used by this {@code DatabaseManager} to execute
 queries against the database.
 @return the {@code PreparedStatement} object
 */
protected PreparedStatement getStmt() {
        return stmt;
        }

/**
 Sets the {@link PreparedStatement} object used by this {@code DatabaseManager} to execute
 queries against the database.
 @param stmt the {@code PreparedStatement} object to set
 */
protected void setStmt(PreparedStatement stmt) {
        this.stmt = stmt;
        }

/**
 Retrieves all objects of type {@code T} from the database and returns them as an
 {@link ObservableList}.
 @return an {@code ObservableList} of all objects of type {@code T} in the database
 @throws SQLException if an error occurs while communicating with the database
 */
    public abstract ObservableList<T> getAllFromProperty() throws SQLException;
}
