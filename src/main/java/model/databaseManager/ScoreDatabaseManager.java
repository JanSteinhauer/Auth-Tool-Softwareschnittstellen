package model.databaseManager;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Score;
import model.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
/**

 This class represents the database manager for Score objects.
 It extends the abstract class DatabaseManagerWithEdit and implements
 methods to get all scores from the Score table, update a score in the table,
 and get a score by its ID.
 */
public class ScoreDatabaseManager extends DatabaseManagerWithEdit<Score> {
    /**
     * Returns an ObservableList of all Score objects in the Score table.
     * @return an ObservableList of all Score objects in the Score table.
     * @throws SQLException if there is an error accessing the database.
     */
    @Override
    public ObservableList<Score> getAllFromProperty() throws SQLException {
        ObservableList<Score> scores = FXCollections.observableArrayList();
        setStmt(getConnection().prepareStatement("SELECT * FROM Score"));
        ResultSet rs = getStmt().executeQuery();
        while (rs.next()) {
            scores.add(new Score(rs));
        }
        getStmt().close();
        return scores;
    }

    /**
     * Updates a Score object in the Score table.
     * @param updatedProperty the Score object to update.
     * @throws SQLException if there is an error accessing the database.
     */
    @Override
    public void updatePropertyInDB(Score updatedProperty) throws SQLException {
        setStmt(getConnection().prepareStatement("UPDATE Score SET `highscore` = ? WHERE `idScore` = ?"));
        getStmt().setInt(1, updatedProperty.getHighscore());
        getStmt().setInt(2, updatedProperty.getId());
        getStmt().executeUpdate();
        getStmt().close();
    }

    /**
     * Returns an Optional Score object by its ID.
     * @param id the ID of the Score object to retrieve.
     * @return an Optional containing the Score object, or an empty Optional if the Score object is not found.
     * @throws SQLException if there is an error accessing the database.
     */
    public Optional<Score> getScoreById(int id) throws SQLException {
        setStmt(getConnection().prepareStatement("SELECT * FROM Score WHERE `idScore` = ?"));
        getStmt().setInt(1, id);
        ResultSet rs = getStmt().executeQuery();
        if (!rs.next()){
            return Optional.empty();
        }

        return Optional.ofNullable(new Score(rs));
    }
}
