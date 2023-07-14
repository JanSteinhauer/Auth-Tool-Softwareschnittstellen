package controller;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Machine;
import model.databaseManager.MachineDatabaseManager;

import java.sql.SQLException;

public class MachineController extends EditController<Machine> {
    @FXML
    private TableColumn<Machine, Integer> id;
    @FXML
    private TableColumn<Machine, String> nameColumn, descriptionColumn;
    @FXML
    private TextField idField, nameField, descriptionField;

    private final MachineDatabaseManager machineDatabaseManager = new MachineDatabaseManager();
    /**
     * This method sets all text fields used in the UI for the machine table.
     */
    @Override
    protected void setAllTextFields() {
        getAllTextFields().add(idField);
        getAllTextFields().add(nameField);
        getAllTextFields().add(descriptionField);
    }
    /**
     * This method loads the data of the selected machine into the text fields used in the UI for editing the machine's data.
     */
    @Override
    protected void loadSelectedPropertyInEditField() {
        idField.setText("" + getSelectedProperty().getId());
        nameField.setText(getNameOfSelectedProperty());
        descriptionField.setText(getSelectedProperty().getDescription());
    }
    /** Updates the selected machine property with the values entered in the corresponding text fields.
     * If the update is successful, returns true. Otherwise, returns false and displays a warning message
     * in the message field.
     *
     * @return true if the selected machine property is updated successfully; false otherwise.
     */
    @Override
    protected boolean updateSelectedProperty() {
        try {
            tryUpdateSelectedMachine();
            return true;
        } catch (NullPointerException nullPointerException){
            showWarningInMessageField("The description field should not be empty!");
            return false;
        } catch (IllegalArgumentException iae){
            showWarningInMessageField("The description cannot be longer than 45 characters!");
            return false;
        }
    }
    /**

     * Updates the selected machine's properties based on the user's input in the GUI.
     * If the new machine name is empty, a NullPointerException will be thrown.
     * If the new machine name is longer than 45 chars, an IllegalArgumentException will be thrown.
     * @throws NullPointerException if the new machine name or unt is empty
     * @throws IllegalArgumentException if the new machine name or unit is longer than 45 chars

     **/
    private void tryUpdateSelectedMachine() {
        String description = descriptionField.getText();
        if (description.isEmpty()){
            throw new NullPointerException();
        } else if(description.length() > 45){
            throw new IllegalArgumentException();
        }
        getSelectedProperty().setDescription(description);
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
            machineDatabaseManager.updatePropertyInDB(getSelectedProperty());
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
        return getSelectedProperty().getName();
    }
    /**
     * Sets the regular table columns describing a machine.
     */
    @Override
    protected void setRegularTableColumns() {
        id.setCellValueFactory(new PropertyValueFactory<Machine, Integer>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<Machine, String>("name"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<Machine, String>("description"));
    }
    /**
     * Clears the selected property by creating a new Machine object with default values and setting it as the selected property.
     */
    @Override
    protected void clearSelectedProperty() {
        setSelectedProperty(new Machine(Integer.MAX_VALUE, "", ""));
    }
    /**
     * Retrieves all machines from the database and returns them as an ObservableList.
     * If an SQLException occurs, returns null.
     * @return an ObservableList containing all machines from the database, or null if an SQLException occurs.
     */
    @Override
    protected ObservableList<Machine> getAllFromSelectedProperty() {
        try {
            return machineDatabaseManager.getAllFromProperty();
        } catch (SQLException sqlException){
            return null;
        }
    }
}
