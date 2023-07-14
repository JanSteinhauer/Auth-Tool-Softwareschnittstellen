package controller;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import model.User;
import model.databaseManager.UserDatabaseManager;
import scala.Int;

import java.sql.SQLException;
/**
 The UserController class is a subclass of EditAndCreateAndDeleteController that controls the UI of the User table.
 It includes methods for adding, deleting, and editing users as well as loading user data from the database.
 This class also provides functionality for updating the UI when changes are made to the user table.
 */

public class UserController extends EditAndCreateAndDeleteController<User>{
    @FXML
    private TableColumn<User, Integer> idColumn, highscoreColumn;
    @FXML
    private TableColumn<User, String> usernameColumn, mailColumn, passwordColumn;
    @FXML
    private TextField idField, usernameField, mailField, passwordField;

    private final UserDatabaseManager userDatabaseManager = new UserDatabaseManager();

    /**
     * This method is called when a user selects the delete button from the UI.
     * It attempts to delete the selected user from the database.
     * If the deletion is successful, this method returns true, else it shows a warning message in the message field and returns false.
     *
     * @return true if the selected user was successfully deleted from the database, false otherwise.
     */
    @Override
    protected boolean deleteSelectedPropertyFromDB() {
        try{
            userDatabaseManager.deletePropertyFromDB(getSelectedProperty());
            return true;
        } catch (SQLException sqlException){
            showWarningInMessageField("Deleting " + getNameOfSelectedProperty() + " failed on database side");
            return false;
        }
    }

    /**
     * This method is called when a user selects the create button from the UI.
     * It attempts to create a new user in the database using the selected user's data.
     * If the creation is successful, this method returns true, else it shows a warning message in the message field and returns false.
     *
     * @return true if the new user was successfully created in the database, false otherwise.
     */
    @Override
    protected boolean createNewPropertyInDB() {
        try{
            userDatabaseManager.createNewPropertyInDB(getSelectedProperty());
            return true;
        } catch (SQLException sqlException){
            showWarningInMessageField("Deleting " + getNameOfSelectedProperty() + " failed on database side");
            return false;
        }
    }

    /**
     * This method sets all text fields used in the UI for the user table.
     */
    @Override
    protected void setAllTextFields() {
        getAllTextFields().add(idField);
        getAllTextFields().add(usernameField);
        getAllTextFields().add(mailField);
        getAllTextFields().add(passwordField);
    }

    /**
     * This method loads the data of the selected user into the text fields used in the UI for editing the user's data.
     */
    @Override
    protected void loadSelectedPropertyInEditField() {
        idField.setText("" + getSelectedProperty().getId());
        usernameField.setText(getNameOfSelectedProperty());
        mailField.setText(getSelectedProperty().getMail());
        passwordField.setText(getSelectedProperty().getPassword());
    }
    /**
     * Updates the selected user property with the values entered in the corresponding text fields.
     * If the update is successful, returns true. Otherwise, returns false and displays a warning message
     * in the message field.
     *
     * @return true if the selected user property is updated successfully; false otherwise.
     */
    @Override
    protected boolean updateSelectedProperty() {
        try {
            tryUpdateSelectedProperty();
            return true;
        } catch (NullPointerException nullPointerException){
            showWarningInMessageField("The editable fields cannot be empty!");
            return false;
        } catch (IllegalArgumentException illegalArgumentException){
            showWarningInMessageField("A User with the username " + usernameField.getText() + " already exists! Choose another username!");
            return false;
        } catch (SQLException sqlException){
            showWarningInMessageField("Something went wrong on database side!");
            return false;
        }
    }

    /**
     * Tries to update the selected user property with the values entered in the corresponding text fields.
     * If any of the fields are empty or if the entered username is not unique, throws a NullPointerException
     * or an IllegalArgumentException, respectively. Otherwise, sets the values of the selected user property
     * to the entered values.
     *
     * @throws NullPointerException if any of the editable fields are empty
     * @throws IllegalArgumentException if the entered username is not unique
     * @throws SQLException if there is an error while accessing the user database
     */
    private void tryUpdateSelectedProperty() throws SQLException {
        String newUsername = usernameField.getText();
        String newMail = mailField.getText();
        String newPassword = passwordField.getText();

        if (newUsername.isEmpty() || newMail.isEmpty() || newPassword.isEmpty()){
            throw new NullPointerException();
        } else if (!isNameUnique(newUsername) && !newUsername.equals(getSelectedProperty().getUsername())) {
            throw new IllegalArgumentException();
        }
        getSelectedProperty().setUsername(newUsername);
        getSelectedProperty().setMail(newMail);
        getSelectedProperty().setPassword(newPassword);
    }
    /**
     * Checks if the given username is unique among all the users in the user database.
     *
     * @param name the username to check
     * @return true if the username is unique; false otherwise.
     * @throws SQLException if there is an error while accessing the user database
     */
    private boolean isNameUnique(String name) throws SQLException {
        return userDatabaseManager.findUserByName(name).isEmpty();
    }
    /**
     * Attempts to update the selected property in the database.
     * If successful, returns true.
     * If unsuccessful, shows a warning message in the message field and returns false.
     * @return true if the update was successful, false otherwise.
     */
    @Override
    protected boolean updateSelectedPropertyInDB() {
        try {
            userDatabaseManager.updatePropertyInDB(getSelectedProperty());
            return true;
        } catch (SQLException sqlException) {
            showWarningInMessageField("Updating " + getNameOfSelectedProperty() + " failed on database side");
            return false;
        }
    }

    /**
     * Returns the name of the currently selected property.
     * @return the name of the currently selected property.
     */
    @Override
    protected String getNameOfSelectedProperty() {
        return getSelectedProperty().getUsername();
    }

    /**
     * Sets the regular table columns.
     */
    @Override
    protected void setRegularTableColumns() {
        idColumn.setCellValueFactory(new PropertyValueFactory<User, Integer>("id"));
        usernameColumn.setCellValueFactory(new PropertyValueFactory<User, String>("username"));
        mailColumn.setCellValueFactory(new PropertyValueFactory<User, String>("mail"));
        passwordColumn.setCellValueFactory(new PropertyValueFactory<User, String>("password"));
        highscoreColumn.setCellValueFactory(new PropertyValueFactory<User, Integer>("highscore"));
    }

    /**
     * Clears the selected property by creating a new User object with default values and setting it as the selected property.
     */
    @Override
    protected void clearSelectedProperty() {
        User emptyUser = new User(Integer.MAX_VALUE, "", "", "");
        setSelectedProperty(emptyUser);
    }

    /**
     * Retrieves all users from the database and returns them as an ObservableList.
     * If an SQLException occurs, returns null.
     * @return an ObservableList containing all users from the database, or null if an SQLException occurs.
     */
    @Override
    protected ObservableList<User> getAllFromSelectedProperty() {
        try {
            return userDatabaseManager.getAllFromProperty();
        } catch (SQLException sqlException){
            return null;
        }
    }
}
