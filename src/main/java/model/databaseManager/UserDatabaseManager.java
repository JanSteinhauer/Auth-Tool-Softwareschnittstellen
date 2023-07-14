package model.databaseManager;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.User;

import java.sql.*;
import java.util.Optional;
/**

 This class handles the CRUD operations for the User object in the database. It extends the DatabaseManagerWithEditAndCreateAndDelete class, which allows it to create, read, update, and delete user objects in the database. It also includes a method to find a user by their name in the database.
 */
public class UserDatabaseManager extends DatabaseManagerWithEditAndCreateAndDelete<User> {
    /**
     * Returns an observable list of all the users in the database.
     *
     * @return an observable list of all the users in the database
     * @throws SQLException if there is a problem with the database connection or the SQL query
     */
    @Override
    public ObservableList<User> getAllFromProperty() throws SQLException {
        ObservableList<User> users = FXCollections.observableArrayList();
        setStmt(getConnection().prepareStatement("SELECT * FROM User"));
        ResultSet rs = getStmt().executeQuery();
        while (rs.next()) {
            User user = new User(rs);
            users.add(user);
        }
        getStmt().close();
        return users;
    }

    /**
     * Updates the given user object in the database.
     *
     * @param updatedProperty the user object to update in the database
     * @throws SQLException if there is a problem with the database connection or the SQL query
     */
    @Override
    public void updatePropertyInDB(User updatedProperty) throws SQLException {
        setStmt(getConnection().prepareStatement("UPDATE User SET `name` = ?, `password` = ?, `mail` = ? WHERE `idUser` = ?"));
        getStmt().setString(1, updatedProperty.getUsername());
        getStmt().setString(2, updatedProperty.getPassword());
        getStmt().setString(3, updatedProperty.getMail());
        getStmt().setInt(4, updatedProperty.getId());
        getStmt().executeUpdate();
        getStmt().close();
    }

    /**
     * Creates a new user object in the database.
     *
     * @param newProperty the user object to create in the database
     * @throws SQLException if there is a problem with the database connection or the SQL query
     */
    @Override
    public void createNewPropertyInDB(User newProperty) throws SQLException {
        String sql_Score = "INSERT INTO Score (`highscore`) VALUES (?)";
        PreparedStatement stmt_Score = getConnection().prepareStatement(sql_Score, Statement.RETURN_GENERATED_KEYS);
        stmt_Score.setInt(1, 0);
        stmt_Score.executeUpdate();
        ResultSet rs_score = stmt_Score.getGeneratedKeys();
        int score_id = -1;
        if (rs_score.next()) {
            score_id = rs_score.getInt(1);
        }
        stmt_Score.close();
        setStmt(getConnection().prepareStatement("INSERT INTO User (`name`, `password`, `mail`, `Score_idScore`) VALUES (?, ?, ?, ?)"));
        getStmt().setString(1, newProperty.getUsername());
        getStmt().setString(2, newProperty.getPassword());
        getStmt().setString(3, newProperty.getMail());
        getStmt().setInt(4, score_id);
        getStmt().execute();
        getStmt().close();
    }

    /**
     * Deletes the given user object from the database.
     *
     * @param propertyToDelete the user object to delete from the database
     * @throws SQLException if there is a problem with the database connection or the SQL query
     */
    @Override
    public void deletePropertyFromDB(User propertyToDelete) throws SQLException {
        PreparedStatement scoreStatement = getConnection().prepareStatement("SELECT Score_idScore FROM User WHERE `idUser` = ?");
        scoreStatement.setInt(1, propertyToDelete.getId());
        ResultSet rs = scoreStatement.executeQuery();
        rs.next();
        int scoreId = rs.getInt(1);
        PreparedStatement scoreDelete = getConnection().prepareStatement("DELETE FROM Score WHERE `idScore` = ?");
        scoreDelete.setInt(1, scoreId);
        scoreDelete.execute();
        setStmt(getConnection().prepareStatement("DELETE FROM User WHERE `idUser` = ?"));
        getStmt().setInt(1, propertyToDelete.getId());
        getStmt().execute();

        scoreStatement.close();
        scoreDelete.close();
        getStmt().close();
    }
    /**

     Searches for a user in the database by their name.
     @param name the name of the user to search for
     @return an Optional containing the user if found, or  Optional.empty() if not found
     @throws SQLException if there is an error accessing the database
     */
    public Optional<User> findUserByName(String name) throws SQLException {
        setStmt(getConnection().prepareStatement("SELECT * FROM User WHERE `name` = ?"));
        getStmt().setString(1, name);
        ResultSet rs = getStmt().executeQuery();
        if (!rs.next()){
            return Optional.empty();
        }

        return Optional.ofNullable(new User(rs));
    }
}
