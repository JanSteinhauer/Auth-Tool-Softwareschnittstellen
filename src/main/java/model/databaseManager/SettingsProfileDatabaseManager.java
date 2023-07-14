package model.databaseManager;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.SettingsProfile;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
/**

 This class provides methods to manage the settings profiles stored in the database. A SettingsProfile object represents a user's settings configuration that specifies preferences like font size, font family, and color scheme.
 The class extends the abstract class DatabaseManagerWithEdit and overrides its methods to handle SettingsProfile objects.
 */
public class SettingsProfileDatabaseManager extends DatabaseManagerWithEdit<SettingsProfile> {
    /**
     * This method returns an ObservableList containing all the SettingsProfile objects stored in the database.
     * It executes a SELECT statement and then iterates over the ResultSet to create SettingsProfile objects.
     * @return an ObservableList containing all the SettingsProfile objects stored in the database
     * @throws SQLException if a database access error occurs or this method is called on a closed Statement
     */
    @Override
    public ObservableList<SettingsProfile> getAllFromProperty() throws SQLException {
        ObservableList<SettingsProfile> settingProfiles = FXCollections.observableArrayList();
        setStmt(getConnection().prepareStatement("SELECT * FROM Setting_Profiles_Authoringtool"));
        ResultSet rs = getStmt().executeQuery();
        while (rs.next()) {
            settingProfiles.add(new SettingsProfile(rs));
        }
        getStmt().close();
        return settingProfiles;
    }

    /**
     * This method updates the SettingsProfile object specified in the argument in the database.
     * It executes an UPDATE statement with the SettingsProfile object's properties.
     * @param updatedProperty the SettingsProfile object to be updated in the database
     * @throws SQLException if a database access error occurs or this method is called on a closed Statement
     */
    @Override
    public void updatePropertyInDB(SettingsProfile updatedProperty) throws SQLException {
        setStmt(getConnection().prepareStatement("UPDATE Setting_Profiles_Authoringtool SET `inDarkMode` = ?, `font_size` = ?, `font_family` = ? WHERE `name` = ?"));
        getStmt().setInt(1, (int) updatedProperty.isInDarkModeAsInt());
        getStmt().setInt(2, updatedProperty.getFontSize());
        getStmt().setString(3, updatedProperty.getFontFamily().toString());
        getStmt().setString(4, updatedProperty.getName());
        getStmt().executeUpdate();
        getStmt().close();
    }

    /**
     * This method returns an Optional containing the SettingsProfile object that represents the current settings configuration.
     * It executes a SELECT statement with the name "Current" and returns an Optional containing the SettingsProfile object created from the ResultSet.
     * @return an Optional containing the SettingsProfile object that represents the current settings configuration, or an empty Optional if no result is returned from the database
     * @throws SQLException if a database access error occurs or this method is called on a closed Statement
     */
    public Optional<SettingsProfile> getCurrentSettings() throws SQLException {
        setStmt(getConnection().prepareStatement("SELECT * FROM Setting_Profiles_Authoringtool WHERE `name` = ?"));
        getStmt().setString(1, "Current");
        ResultSet rs = getStmt().executeQuery();
        if (!rs.next()){
            return Optional.empty();
        }
        return Optional.ofNullable(new SettingsProfile(rs));
    }

/**
 * This method returns an Optional containing the SettingsProfile object that represents the default settings configuration.
 * It executes a SELECT statement with the name "Default" and returns an Optional containing the SettingsProfile object created from the ResultSet.
 * @return an Optional containing the SettingsProfile object that represents the default settings configuration,
 * @throws SQLException if a database access error occurs or this method is called on a closed Statement
 */
    public Optional<SettingsProfile> getDefaultSettings() throws SQLException {
        setStmt(getConnection().prepareStatement("SELECT * FROM Setting_Profiles_Authoringtool WHERE `name` = ?"));
        getStmt().setString(1, "Default");
        ResultSet rs = getStmt().executeQuery();
        if (!rs.next()){
            return Optional.empty();
        }

        return Optional.ofNullable(new SettingsProfile(rs));
    }
    /**

     Retrieves a SettingsProfile object with the given name from the database, if it exists.
     If no matching profile is found, returns an empty Optional.
     @param name the name of the SettingsProfile to retrieve
     @return an Optional containing the retrieved SettingsProfile object, or an empty Optional if no matching profile is found
     @throws SQLException if there is an error accessing the database
     */
    public Optional<SettingsProfile> getProfileByName(String name) throws SQLException {
        setStmt(getConnection().prepareStatement("SELECT * FROM Setting_Profiles_Authoringtool WHERE `name` = ?"));
        getStmt().setString(1, name);
        ResultSet rs = getStmt().executeQuery();

        if (!rs.next()){
            return Optional.empty();
        }

        return Optional.ofNullable(new SettingsProfile(rs));
    }
}
