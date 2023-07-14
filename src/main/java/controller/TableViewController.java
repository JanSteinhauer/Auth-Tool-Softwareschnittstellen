package controller;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Abstract class for a table view controller. Defines common functionality for displaying and refreshing table data.
 *
 * @param <T> the type of data displayed in the table view
 */
public abstract class TableViewController <T> implements Initializable {

    /**
     * The TableView used by this controller.
     */
    @FXML
    private TableView<T> tableView;

    /**
     * The StackPane used as the background for warning messages.
     */
    @FXML
    private StackPane warningBackground;

    /**
     * The Text node used to display warning or success messages.
     */
    @FXML
    private Text messageField;

    /**
     * The currently selected property.
     */
    private T selectedProperty;

    /**
     * Sets the selected property to the given value.
     *
     * @param selectedProperty the value to set the selected property to
     */
    protected void setSelectedProperty(T selectedProperty) {
        this.selectedProperty = selectedProperty;
    }

    /**
     * Gets the currently selected property.
     *
     * @return the currently selected property
     */
    protected T getSelectedProperty() {
        return selectedProperty;
    }

    /**
     * Called by JavaFX to initialize this controller after all the FXML fields have been injected.
     *
     * @param url            the URL location of the FXML file used to create this controller
     * @param resourceBundle the ResourceBundle instance for this controller
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        refresh();
    }

    /**
     * Refreshes the table view with the latest data from the selected property.
     */
    protected void refresh(){
        setRegularTableColumns();
        setAdditionalContent();

        try {
            tableView.getItems().setAll(getAllFromSelectedProperty());
        } catch (NullPointerException nullPointerException){
            showWarningInMessageField("There are no properties in the corresponding table! You have to create one!");
        }

        clearSelectedProperty();
    }

    /**
     * Sets the regular columns in the table view.
     */
    protected abstract void setRegularTableColumns();

    /**
     * Sets any additional content in the table view.
     */
    protected abstract void setAdditionalContent();

    /**
     * Clears the selected property.
     */
    protected abstract void clearSelectedProperty();

    /**
     * Gets all the data from the selected property as an ObservableList.
     *
     * @return an ObservableList of all the data from the selected property
     */
    protected abstract ObservableList<T> getAllFromSelectedProperty();

    /**
     * Shows a warning message in the message field.
     *
     * @param warningMessage the warning message to display
     */
    protected void showWarningInMessageField(String warningMessage){
        warningBackground.setStyle("-fx-background-color: coral; -fx-font-size: 15.0");
        messageField.setText(warningMessage);
    }

    /**
     * Shows a success message in the message field.
     *
     * @param successMessage the success message to display
     */
    protected void showSuccessInMessageField(String successMessage){
        warningBackground.setStyle("-fx-background-color: #8ce854; -fx-font-size: 15.0");
        messageField.setText(successMessage);
    }
}
