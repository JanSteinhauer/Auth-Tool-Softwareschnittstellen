package controller;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Ingredient;
import model.databaseManager.IngredientDatabaseManager;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
/**
 The IngredientController class is a subclass of EditController that controls the UI of the ingredient table.
 It includes methods for editing ingredients as well as loading ingredient data from the database.
 This class also provides functionality for updating the UI when changes are made to the ingredient table.
 */
public class IngredientController extends EditController<Ingredient> implements Initializable {
    @FXML
    private TableColumn<Ingredient, String> nameColumn, descriptionColumn, unitColumn;
    @FXML
    private TextField nameField, descriptionField, unitField;

    private final IngredientDatabaseManager ingredientDatabaseManager = new IngredientDatabaseManager();
    /**
     * This method loads the data of the selected ingredient into the text fields used in the UI for editing the ingredient's data.
     */
    @Override
    protected void loadSelectedPropertyInEditField() {
        nameField.setText(getNameOfSelectedProperty());
        descriptionField.setText(getSelectedProperty().getDescription());
        unitField.setText(getSelectedProperty().getUnit());
    }
     /** Updates the selected ingredient property with the values entered in the corresponding text fields.
      * If the update is successful, returns true. Otherwise, returns false and displays a warning message
      * in the message field.
      *
      * @return true if the selected ingredient property is updated successfully; false otherwise.
     */
    @Override
    protected boolean updateSelectedProperty() {
        try {
            tryUpdateSelectedProperty();
            return true;
        } catch (NullPointerException nullPointerException) {
            showWarningInMessageField("The description field should not be empty!");
            return false;
        } catch (IllegalArgumentException iae) {
            showWarningInMessageField("The description cannot be longer than 45 characters!");
            return false;
        }
    }
    /**

     * Updates the selected ingredient's properties based on the user's input in the GUI.
     * If the new ingredient name or unit is empty, a NullPointerException will be thrown.
     * If the new ingredient name or unit is longer than 45 chars,
     * an IllegalArgumentException will be thrown.
     * @throws NullPointerException if the new ingredient name or unt is empty
     * @throws IllegalArgumentException if the new ingredient name or unit is longer than 45 chars

     **/
    private void tryUpdateSelectedProperty() {
        String description = descriptionField.getText();
        String unit = unitField.getText();
        if (description.isEmpty() || unit.isEmpty()) {
            throw new NullPointerException();
        } else if (description.length() > 45 ||unit.length() > 45) {
            throw new IllegalArgumentException();
        }
        getSelectedProperty().setDescription(description);
        getSelectedProperty().setUnit(unit);
        System.out.println(getNameOfSelectedProperty() + ", " + getSelectedProperty().getDescription() + ", " + getSelectedProperty().getUnit());
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
            ingredientDatabaseManager.updatePropertyInDB(getSelectedProperty());
            return true;
        } catch (SQLException sqlException) {
            showWarningInMessageField("Updating " + getNameOfSelectedProperty() + " failed on database side");
            sqlException.printStackTrace();
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
     * Sets the regular table columns describing an ingredient.
     */
    @Override
    protected void setRegularTableColumns() {
        nameColumn.setCellValueFactory(new PropertyValueFactory<Ingredient, String>("name"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<Ingredient, String>("description"));
        unitColumn.setCellValueFactory(new PropertyValueFactory<Ingredient, String>("unit"));
    }
    /**
     * This method sets all text fields used in the UI for the ingredient table.
     */
    @Override
    protected void setAllTextFields() {
        getAllTextFields().add(nameField);
        getAllTextFields().add(descriptionField);
        getAllTextFields().add(unitField);
    }
    /**
     * Clears the selected property by creating a new Ingredient object with default values and setting it as the selected property.
     */
    @Override
    protected void clearSelectedProperty() {
        Ingredient emptyIngredient = new Ingredient("", "", "");
        setSelectedProperty(emptyIngredient);
    }
    /**
     * Retrieves all ingredients from the database and returns them as an ObservableList.
     * If an SQLException occurs, returns null.
     * @return an ObservableList containing all ingredients from the database, or null if an SQLException occurs.
     */
    @Override
    protected ObservableList<Ingredient> getAllFromSelectedProperty() {
        try {
            return ingredientDatabaseManager.getAllFromProperty();
        } catch (SQLException sqlException){
            return null;
        }
    }

}
