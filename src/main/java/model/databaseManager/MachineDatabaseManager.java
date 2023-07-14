package model.databaseManager;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Ingredient;
import model.Machine;

import java.sql.ResultSet;
import java.sql.SQLException;
/**
 This class represents the database manager for the Machine objects.
 It extends the abstract class DatabaseManagerWithEdit and overrides its getAllFromProperty and updatePropertyInDB methods.
 The getAllFromProperty method retrieves all machines from the Machine table in the database and returns them as an ObservableList of Machine objects.
 The updatePropertyInDB method updates the description of a machine in the database.
 */
public class MachineDatabaseManager extends DatabaseManagerWithEdit<Machine> {
    @Override
    public ObservableList<Machine> getAllFromProperty() throws SQLException {
        ObservableList<Machine> machines = FXCollections.observableArrayList();
        setStmt(getConnection().prepareStatement("SELECT * FROM Machine"));
        ResultSet rs = getStmt().executeQuery();
        while (rs.next()) {
            Machine machine = new Machine(rs);
            machines.add(machine);
        }
        getStmt().close();
        return machines;
    }

    @Override
    public void updatePropertyInDB(Machine updatedProperty) throws SQLException {
        setStmt(getConnection().prepareStatement("UPDATE Machine SET `decription` = ? WHERE `name` = ? "));
        getStmt().setString(1, updatedProperty.getDescription());
        getStmt().setString(2, updatedProperty.getName());
        getStmt().executeUpdate();
        getStmt().close();
    }
}
