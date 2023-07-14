package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.util.Callback;
import org.w3c.dom.Text;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * This abstract class extends TableViewController and provides functionality for editing T objects.
 * It contains a TableColumn for editing T objects, a list of all the text fields in the edit area, and methods
 * for setting up and managing the edit functionality, such as loading the selected property into the edit area,
 * updating the selected property, and resetting the edit area. Subclasses should implement the abstract methods to
 * define the behavior for loading, updating, and getting the name of the selected property.
 * @param <T> The type of object being edited.
 */
public abstract class EditController<T> extends TableViewController<T> {
    /**
     * The TableColumn that contains the edit button.
     */
    @FXML
    protected TableColumn editColumn;

    /**
     * A list of all TextFields used for editing properties.
     */
    private List<TextField> allTextFields = new ArrayList<>();

    /**
     * Initializes the controller after its root element has been completely processed.
     *
     * @param url The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setAllTextFields();
        super.initialize(url, resourceBundle);
    }

    /**
     * Returns the TableColumn that contains the edit button.
     *
     * @return The TableColumn that contains the edit button.
     */
    public TableColumn getEditColumn() {
        return editColumn;
    }

    /**
     * Returns a list of all TextFields used for editing properties.
     *
     * @return A list of all TextFields used for editing properties.
     */
    public List<TextField> getAllTextFields() {
        return allTextFields;
    }

    /**
     * Sets additional content for the TableView, such as the edit table column and all TextFields.
     */
    @Override
    protected void setAdditionalContent() {
        setEditTableColumn();
        setAllTextFields();
    }

    /**
     * Sets up the edit table column, which contains the edit button.
     */
    private void setEditTableColumn() {
        getEditColumn().setCellFactory(createEditButtonCallback());
    }

    /**
     * Sets all TextFields used for editing properties.
     */
    protected abstract void setAllTextFields();

    /**
     * Creates a callback function that returns a TableCell with an edit button.
     *
     * @return A callback function that returns a TableCell with an edit button.
     */
    protected Callback<TableColumn<T, String>, TableCell<T, String>> createEditButtonCallback() {
        return new Callback<TableColumn<T, String>, TableCell<T, String>>() {
            @Override
            public TableCell<T, String> call(TableColumn<T, String> ingredientStringTableColumn) {
                TableCell<T, String> editCell = new TableCell<T, String>() {
                    Button editButton = new Button("Edit");

                    @Override
                    public void updateItem(String item, boolean empty) {
                        editButton.getStyleClass().add("editButton");
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                            setText(null);
                        } else {
                            editButton.setOnAction(event -> {
                                onEditButtonClicked(this);
                            });
                            setGraphic(editButton);
                            setText(null);
                        }
                    }
                };
                editCell.setAlignment(Pos.CENTER);
                return editCell;
            }
        };
    }

    /**
     * Called when the edit button is clicked. Sets the selected property and loads its information into the edit fields.
     *
     * @param editCell The TableCell that contains the edit button that was clicked.
     */
    private void onEditButtonClicked(TableCell<T, String> editCell){
        setSelectedProperty(editCell.getTableView().getItems().get(editCell.getIndex()));
        loadSelectedPropertyInEditField();
    }
    /**
     * Loads the selected property into the edit field(s), enabling the user to modify its values.
     * This method should be implemented by subclasses to handle the specific details of how the selected
     * property is loaded into the edit field(s).
     */
    protected abstract void loadSelectedPropertyInEditField();


    /**
     * Handles the editing of the selected property, updating its values based on the current values in the
     * edit field(s). If the update is successful (both locally and in the database), the edit area is reset,
     * the success message is displayed in the message field, and the table view is refreshed to display the
     * updated property.
     */
    @FXML
    void editProperty(){
        if (updateSelectedProperty() && updateSelectedPropertyInDB()){
            resetEditArea();
            showSuccessInMessageField(getNameOfSelectedProperty() +  " was edited successfully!");
            refresh();
        }
    }

    /**
     * Updates the selected property based on the current values in the edit field(s). This method should be
     * implemented by subclasses to handle the specific details of how the selected property is updated based
     * on the edit field(s).
     *
     * @return true if the update is successful, false otherwise.
     */
    protected abstract boolean updateSelectedProperty();

    /**
     * Updates the selected property in the database based on the current values in the edit field(s). This method
     * should be implemented by subclasses to handle the specific details of how the selected property is updated
     * in the database.
     *
     * @return true if the update is successful, false otherwise.
     */
    protected abstract boolean updateSelectedPropertyInDB();

    /**
     * Resets the edit area by clearing the values in all of the text fields.
     */
    protected void resetEditArea(){
        for (TextField field : allTextFields){
            field.setText("");
        }
    }

    /**
     * Gets the name of the selected property. This method should be implemented by subclasses to return the name
     * of the selected property, which will be used in success messages.
     *
     * @return the name of the selected property.
     */
    protected abstract String getNameOfSelectedProperty();
}
