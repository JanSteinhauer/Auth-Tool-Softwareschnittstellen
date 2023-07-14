package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;

/**
 * An abstract class that provides functionality for editing, creating, and deleting instances of a certain type T.
 * Extends EditController and implements Initializable.
 *
 * @param <T> the type of object to edit, create, and delete
 */
public abstract class EditAndCreateAndDeleteController<T> extends EditController<T> implements Initializable {

    /**
     * TableColumn for the delete button.
     */
    @FXML
    protected TableColumn deleteColumn;

    /**
     * Getter for the deleteColumn.
     *
     * @return the deleteColumn TableColumn
     */
    public TableColumn getDeleteColumn() {
        return deleteColumn;
    }

    /**
     * Overrides setAdditionalContent() in EditController.
     * Sets the cell factory for both the edit and delete columns.
     */
    @Override
    protected void setAdditionalContent(){
        getEditColumn().setCellFactory(createEditButtonCallback());
        getDeleteColumn().setCellFactory(createCreateButtonCallback());
    }

    /**
     * Creates a callback for the delete button cell factory.
     *
     * @return a callback for the delete button cell factory
     */
    protected Callback<TableColumn<T, String>, TableCell<T, String>> createCreateButtonCallback(){
        return new Callback<TableColumn<T, String>, TableCell<T, String>>() {
            @Override
            public TableCell<T, String> call(TableColumn<T, String> ingredientStringTableColumn) {
                TableCell<T, String> deleteCell = new TableCell<T, String>() {
                    Button deleteButton = new Button("X");

                    @Override
                    public void updateItem(String item, boolean empty) {
                        deleteButton.getStyleClass().add("deleteButton");
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                            setText(null);
                        } else {
                            deleteButton.setOnAction(event -> {
                                setSelectedProperty(getTableView().getItems().get(getIndex()));
                                deleteSelectedProperty();
                            });
                            setGraphic(deleteButton);
                            setText(null);
                        }
                    }
                };
                deleteCell.setAlignment(Pos.CENTER);
                return deleteCell;
            }
        };
    }

    /**
     * This method is called when the delete button is pressed in the TableView.
     * It first calls the abstract method deleteSelectedPropertyFromDB() to delete the selected property from the database.
     * If the deletion is successful, it resets the edit area, shows a success message in the message field, and refreshes the TableView.
     * If the deletion is unsuccessful, it does not perform any action.
     */
    protected void deleteSelectedProperty(){
        if (deleteSelectedPropertyFromDB()){
            resetEditArea();
            showSuccessInMessageField(getNameOfSelectedProperty() +  " was deleted successfully!");
        }
        refresh();
    }

    /**
     * Deletes the selected property from the database.
     *
     * @return true if the property was successfully deleted, false otherwise
     */
    protected abstract boolean deleteSelectedPropertyFromDB();

    /**
     * Creates a new instance of the selected property.
     * If the updateSelectedProperty() method returns false, the selected property is cleared.
     * If the updateSelectedProperty() method returns true and createNewPropertyInDB() returns true,
     * the edit area is reset, a success message is displayed in the message field, and the table is refreshed.
     */
    @FXML
    void createNewProperty() {
        if (!updateSelectedProperty()){
            clearSelectedProperty();
        }
        if (updateSelectedProperty() && createNewPropertyInDB()){
            resetEditArea();
            showSuccessInMessageField(getNameOfSelectedProperty() +  " was added successfully!");
            refresh();
        }
    }

    /**
     * Creates a new instance of the selected property in the database.
     *
     * @return true if the new instance was successfully created, false otherwise
     */
    protected abstract boolean createNewPropertyInDB();
}
