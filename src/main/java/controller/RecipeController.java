package controller;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Recipe;
import model.databaseManager.RecipeDatabaseManager;

import java.sql.SQLException;
/**
 The RecipeController class is a subclass of EditAndCreateAndDeleteController that controls the UI of the Recipe table.
 It includes methods for adding, deleting, and editing recipes as well as loading recipe data from the database.
 This class also provides functionality for updating the UI when changes are made to the recipe table.
 */
public class RecipeController extends EditAndCreateAndDeleteController<Recipe> {
    @FXML
    private TableColumn<Recipe, String> nameColumn;
    @FXML
    private TableColumn<Recipe, Integer> brewtimeColumn, coffeeBeansColumn, milkColumn, waterPressureColumn;
    @FXML
    private TextField nameField, brewtimeField, coffeeBeanField, milkField, waterPressureField;

    private final RecipeDatabaseManager recipeDatabaseManager = new RecipeDatabaseManager();
    /**
     * This method is called when a user selects the delete button from the UI.
     * It attempts to delete the selected recipe from the database.
     * If the deletion is successful, this method returns true, else it shows a warning message in the message field and returns false.
     *
     * @return true if the selected recipe was successfully deleted from the database, false otherwise.
     */
    @Override
    protected boolean deleteSelectedPropertyFromDB() {
        try {
            recipeDatabaseManager.deletePropertyFromDB(getSelectedProperty());
            return true;
        } catch (SQLException sqlException) {
            showWarningInMessageField("Deleting " + getNameOfSelectedProperty() + " failed on database side");
            return false;
        }
    }
    /**
     * This method is called when a user selects the create button from the UI.
     * It attempts to create a new recipe in the database using the selected recipe's data.
     * If the creation is successful, this method returns true, else it shows a warning message in the message field and returns false.
     *
     * @return true if the new recipe was successfully created in the database, false otherwise.
     */
    @Override
    protected boolean createNewPropertyInDB() {
        try {
            recipeDatabaseManager.createNewPropertyInDB(getSelectedProperty());
            return true;
        } catch (SQLException sqlException) {
            showWarningInMessageField("Creating " + getNameOfSelectedProperty() + " failed on database side");
            sqlException.printStackTrace();
            return false;
        }
    }
    /**
     * This method sets all text fields used in the UI for the recipe table.
     */
    @Override
    protected void setAllTextFields() {
        getAllTextFields().add(nameField);
        getAllTextFields().add(brewtimeField);
        getAllTextFields().add(coffeeBeanField);
        getAllTextFields().add(milkField);
        getAllTextFields().add(waterPressureField);
    }
    /**
     * This method loads the data of the selected recipe into the text fields used in the UI for editing the recipe's data.
     */
    @Override
    protected void loadSelectedPropertyInEditField() {
        nameField.setText(getNameOfSelectedProperty());
        brewtimeField.setText("" + getSelectedProperty().getBrewtime());
        coffeeBeanField.setText("" + getSelectedProperty().getAmountOfBeans());
        milkField.setText("" + getSelectedProperty().getAmountOfMilk());
        waterPressureField.setText("" + getSelectedProperty().getWaterPressure());
    }
    /**
     * Updates the selected recipe property with the values entered in the corresponding text fields.
     * If the update is successful, returns true. Otherwise, returns false and displays a warning message
     * in the message field.
     *
     * @return true if the selected recipe property is updated successfully; false otherwise.
     */
    @Override
    protected boolean updateSelectedProperty() {
        try {
            tryUpdateSelectedProperty();
            return true;
        } catch (NumberFormatException numberFormatException) {
            showWarningInMessageField("Brewtime, Water Pressure and the ingredients have to be positive numbers!");
            return false;
        } catch (NullPointerException nullPointerException) {
            showWarningInMessageField("The name field cannot be empty!");
            return false;
        } catch (IllegalArgumentException illegalArgumentException) {
            showWarningInMessageField("A recipe with the name " + nameField.getText() + "already exists! Choose another name!");
            return false;
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            return false;
        }
    }
    /**

     * Updates the selected Recipe's properties based on the user's input in the GUI.
     * This method parses the values of the brewtime, coffee beans, milk, and water pressure fields
     * from the GUI and sets them as the new property values for the selected Recipe.
     * If any of the input values are invalid, a NumberFormatException will be thrown.
     * If the new Recipe name is empty, a NullPointerException will be thrown.
     * If the new Recipe name is not unique and not equal to the original name of the selected Recipe,
     * an IllegalArgumentException will be thrown.
     * The former name of the Recipe is set to the name of the selected Recipe before the name is updated.
     * The image URL for the Recipe is set to the default image URL.
     * @throws SQLException if there is an error in the database during the update process
     * @throws NumberFormatException if any of the input values are not valid numbers
     * @throws NullPointerException if the new Recipe name is empty
     * @throws IllegalArgumentException if the new Recipe name is not unique and not equal to the original name of the selected Recipe

     **/
    private void tryUpdateSelectedProperty() throws SQLException {
        int brewtime = Integer.parseInt(brewtimeField.getText());
        int beans = Integer.parseInt(coffeeBeanField.getText());
        int milk = Integer.parseInt(milkField.getText());
        int waterPressure = Integer.parseInt(waterPressureField.getText());
        if (!isInputNumbersValid(brewtime, beans, milk, waterPressure)) {
            throw new NumberFormatException();
        }
        String newName = nameField.getText();
        if (newName.isEmpty()) {
            throw new NullPointerException();
        } else if (recipeDatabaseManager.findRecipeByName(newName).isPresent() && !newName.equals(getSelectedProperty().getName())) {
            throw new IllegalArgumentException();
        }
        getSelectedProperty().setBrewtime(brewtime);
        getSelectedProperty().setAmountOfBeans(beans);
        getSelectedProperty().setAmountOfMilk(milk);
        getSelectedProperty().setWaterPressure(waterPressure);
        getSelectedProperty().setFormerName(getNameOfSelectedProperty());
        getSelectedProperty().setName(nameField.getText());
        getSelectedProperty().setImageURL(Recipe.DEFAULT_IMAGE_URL);
    }
    /**

     Checks if the input numbers are valid for a coffee recipe.
     @param brewtime the time in seconds that it takes to brew the coffee
     @param beans the amount of coffee beans used in grams
     @param milk the amount of milk used in milliliters
     @param waterPressure the water pressure used to brew the coffee
     @return true if all input numbers are positive, false otherwise
     */
    private boolean isInputNumbersValid(int brewtime, int beans, int milk, int waterPressure) {
        return brewtime > 0 && beans > 0 && milk >= 0 && waterPressure > 0;
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
            recipeDatabaseManager.updatePropertyInDB(getSelectedProperty());
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
     * Sets the regular table columns describing a recipe.
     */
    @Override
    protected void setRegularTableColumns() {
        nameColumn.setCellValueFactory(new PropertyValueFactory<Recipe, String>("name"));
        brewtimeColumn.setCellValueFactory(new PropertyValueFactory<Recipe, Integer>("brewtime"));
        coffeeBeansColumn.setCellValueFactory(new PropertyValueFactory<>("amountOfBeans"));
        milkColumn.setCellValueFactory(new PropertyValueFactory<>("amountOfMilk"));
        waterPressureColumn.setCellValueFactory(new PropertyValueFactory<>("waterPressure"));
    }
    /**
     * Clears the selected property by creating a new Recipe object with default values and setting it as the selected property.
     */
    @Override
    protected void clearSelectedProperty() {
        setSelectedProperty(new Recipe());
    }
    /**
     * Retrieves all recipes from the database and returns them as an ObservableList.
     * If an SQLException occurs, returns null.
     * @return an ObservableList containing all recipes from the database, or null if an SQLException occurs.
     */
    @Override
    protected ObservableList<Recipe> getAllFromSelectedProperty() {
        try {
            return recipeDatabaseManager.getAllFromProperty();
        } catch (SQLException sqlException){
            return null;
        }
    }
}
