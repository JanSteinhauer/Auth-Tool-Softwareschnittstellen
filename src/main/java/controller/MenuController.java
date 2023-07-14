package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import model.SettingsApplier;
import model.SettingsProfile;
import model.databaseManager.SettingsProfileDatabaseManager;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;
/**
 * The MenuController class manages the menu bar and the content pane of the main application window.
 * It implements the Initializable interface, so its initialize method is called when the FXML file is loaded.
 * The class is responsible for loading the different panes when the corresponding menu items are clicked.
 */
public class MenuController implements Initializable {
    @FXML
    private AnchorPane contentPane;

    private Pane usersPane, recipePane, ingredientPane, machinePane, currentPane, settingsPane, welcomePane;

    /**
     * This method is called by the FXMLLoader when the FXML file is loaded. It loads the different panes from the FXML files.
     * It then applies the current settings to the content pane and loads the welcome pane.
     *
     * @param url the URL of the FXML file
     * @param resourceBundle the ResourceBundle object associated with the FXML file
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            usersPane = FXMLLoader.load(getClass().getResource("/view/user.fxml"));
            recipePane = FXMLLoader.load(getClass().getResource("/view/recipe.fxml"));
            ingredientPane = FXMLLoader.load(getClass().getResource("/view/Ingredient.fxml"));
            machinePane = FXMLLoader.load(getClass().getResource("/view/machine.fxml"));
            settingsPane = FXMLLoader.load(getClass().getResource("/view/settings.fxml"));
            welcomePane = FXMLLoader.load(getClass().getResource("/view/WelcomePage.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        SettingsApplier.applyCurrentSettings(contentPane);
        loadWelcomePane();
    }

    /**
     * This method removes the current pane from the content pane, if it exists.
     */
    private void removeCurrentContent(){
        if (currentPane != null) {
            contentPane.getChildren().removeAll(currentPane);
        }
    }

    /**
     * This method adds the specified pane to the content pane and sets it as the current pane.
     *
     * @param pane the pane to add to the content pane
     */
    private void addContentToContentPane(Pane pane){
        contentPane.getChildren().add(pane);
        currentPane = pane;
    }

    /**
     * This method loads the users pane into the content pane when the Users menu item is clicked.
     */
    @FXML
    void loadUsersPane(){
        removeCurrentContent();
        SettingsApplier.applyCurrentSettings(contentPane);
        addContentToContentPane(usersPane);
    }

    /**
     * This method loads the recipe pane into the content pane when the Recipes menu item is clicked.
     */
    @FXML
    void loadRecipePane(){
        removeCurrentContent();
        SettingsApplier.applyCurrentSettings(contentPane);
        addContentToContentPane(recipePane);
    }

    /**
     * This method loads the ingredient pane into the content pane when the Ingredients menu item is clicked.
     */
    @FXML
    void loadIngredientPane(){
        removeCurrentContent();
        SettingsApplier.applyCurrentSettings(contentPane);
        addContentToContentPane(ingredientPane);
    }

    /**
     * This method loads the machine pane into the content pane when the Machines menu item is clicked.
     */
    @FXML
    void loadMachinePane() {
        removeCurrentContent();
        SettingsApplier.applyCurrentSettings(contentPane);
        addContentToContentPane(machinePane);
    }
    /**
     * This method loads the settings pane into the content pane when the Settings menu item is clicked.
     */
    @FXML
    void loadSettingsPane() {
        removeCurrentContent();
        SettingsApplier.applyCurrentSettings(contentPane);
        addContentToContentPane(settingsPane);
    }
    /**
     * This method loads the welcome pane into the content pane when the initialize method is called.
     */
    @FXML
    void loadWelcomePane() {
        removeCurrentContent();
        SettingsApplier.applyCurrentSettings(contentPane);
        addContentToContentPane(welcomePane);
    }
}
