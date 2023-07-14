package controller;

import com.google.protobuf.MapEntry;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.AnchorPane;
import model.FontFamily;
import model.SettingsApplier;
import model.SettingsProfile;
import model.databaseManager.SettingsProfileDatabaseManager;

import java.net.URL;
import java.sql.SQLException;
import java.util.*;

/**
 * The ProfileController class provides methods and functionality for configuring the user's profile settings.
 * This abstract class implements the Initializable interface and contains several FXML elements, including
 * CheckBox, Button, and AnchorPane, which are initialized using the FXML annotation. It also contains an instance
 * of the SettingsProfileDatabaseManager class, which provides access to the database where user profile settings are stored.
 * The class contains several methods for configuring the user's profile settings, including methods for loading the current
 * profile configuration, setting all buttons to their default settings, selecting the current font size and font family,
 * setting the dark mode, and updating the current settings in the database. It also contains methods for saving and resetting
 * changes made to the user's profile settings. These methods are annotated with the @FXML annotation, allowing them to be
 * called from the FXML file.
 */
public abstract class ProfileController implements Initializable {
    @FXML
    private CheckBox darkModeBox;
    @FXML
    private Button smallFontSizeButton, mediumFontSizeButton, bigFontSizeButton, arialButton, comicSansMSButton, timesNewRomanButton, resetChangesButton, resetToDefaultButton, submitButton;

    private final SettingsProfileDatabaseManager profileDatabaseManager = new SettingsProfileDatabaseManager();
    private SettingsProfile profile;
    private SettingsProfile newProfileSettings;

    /**
     * Returns the instance of the SettingsProfileDatabaseManager class used by this ProfileController.
     *
     * @return the instance of the SettingsProfileDatabaseManager class used by this ProfileController.
     */
    public SettingsProfileDatabaseManager getProfileDatabaseManager() {
        return profileDatabaseManager;
    }

    /**
     * Returns the current SettingsProfile object.
     *
     * @return the current SettingsProfile object.
     */
    public SettingsProfile getProfile() {
        return profile;
    }

    /**
     * Sets the current SettingsProfile object.
     *
     * @param profile the SettingsProfile object to set as current.
     */
    public void setProfile(SettingsProfile profile) {
        this.profile = profile;
    }

    /**
     * Returns the current unsaved SettingsProfile object.
     *
     * @return the current unsaved SettingsProfile object.
     */
    public SettingsProfile getNewProfileSettings() {
        return newProfileSettings;
    }

    /**
     * Sets the current unsaved SettingsProfile object.
     *
     * @param newProfileSettings the SettingsProfile object to set as the current unsaved settings.
     */
    public void setNewProfileSettings(SettingsProfile newProfileSettings) {
        this.newProfileSettings = newProfileSettings;
    }

    /**
     * Initializes the controller after its root element has been completely processed.
     *
     * @param url            the location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle the resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public abstract void initialize(URL url, ResourceBundle resourceBundle);

    /**
     * Loads the current user profile configuration.
     */
    protected void loadCurrentProfileConfiguration() {
        setDarkModeBox();
        setAllButtonsToDefault();
        setSelectedButtons();
    }
    /**
     * Sets all button styles to the corresponding default values.
     */
    private void setAllButtonsToDefault() {
        if (getNewProfileSettings().isInDarkMode()){
            arialButton.getScene().getRoot().getStylesheets().clear();
            arialButton.getScene().getRoot().getStylesheets().add(getClass().getResource("/css/darkMode.css").toString());
        }
        smallFontSizeButton.getStyleClass().clear();
        smallFontSizeButton.getStyleClass().add("smallFontSizeButton");
        smallFontSizeButton.getStyleClass().add("button");

        mediumFontSizeButton.getStyleClass().clear();
        mediumFontSizeButton.getStyleClass().add("mediumFontSizeButton");
        mediumFontSizeButton.getStyleClass().add("button");

        bigFontSizeButton.getStyleClass().clear();
        bigFontSizeButton.getStyleClass().add("button");
        bigFontSizeButton.getStyleClass().add("bigFontSizeButton");

        arialButton.getStyleClass().clear();
        arialButton.getStyleClass().add("button");
        arialButton.getStyleClass().add("arialButton");

        comicSansMSButton.getStyleClass().clear();
        comicSansMSButton.getStyleClass().add("button");
        comicSansMSButton.getStyleClass().add("comicSansMSButton");

        timesNewRomanButton.getStyleClass().clear();
        timesNewRomanButton.getStyleClass().add("button");
        timesNewRomanButton.getStyleClass().add("timesNewRomanButton");

    }
    /**

     Sets the selected buttons for the font family and font size.
     */
    protected void setSelectedButtons() {
        getSelectedFontSizeButton().getStyleClass().add("selectedButton");
        getSelectedFontFamilyButton().getStyleClass().add("selectedButton");
    }
    /**

     Returns the button for the selected font family.
     @return the button for the selected font family
     */
    private Button getSelectedFontFamilyButton() {
        return switch (getNewProfileSettings().getFontFamily()) {
            case ComicSansMS -> comicSansMSButton;
            case TimesNewRoman -> timesNewRomanButton;
            default -> arialButton;
        };
    }
    /**

     This method returns the button for the selected font size.
     @return the button for the selected font size
     */
    private Button getSelectedFontSizeButton() {
        final int smallFontSize = SettingsProfile.SMALL_FONT_SIZE;
        final int bigFontSize = SettingsProfile.BIG_FONT_SIZE;
        return switch (getNewProfileSettings().getFontSize()) {
            case smallFontSize -> smallFontSizeButton;
            case bigFontSize -> bigFontSizeButton;
            default -> mediumFontSizeButton;
        };
    }
    /**

     This method sets the dark mode box according to the current profile settings.
     */
    protected void setDarkModeBox() {
        darkModeBox.selectedProperty().setValue(getNewProfileSettings().isInDarkMode());
    }
    /**

     This method resets the changes made to the profile settings to the original state.
     @throws SQLException if there is a problem with the SQL query
     */
    @FXML
    void resetChanges() throws SQLException {
        setNewProfileSettings(getProfile().copy());
        profileDatabaseManager.updatePropertyInDB(newProfileSettings);
        applyUnsavedSettings();
        loadCurrentProfileConfiguration();
    }
    /**

     This method resets the profile settings to the default values stored in the database.
     @throws SQLException if there is a problem with the SQL query
     */
    @FXML
    void resetToDefault() throws SQLException {
        setNewProfileSettings(profileDatabaseManager.getDefaultSettings().get());
        getNewProfileSettings().setName(getProfile().getName());
        applyUnsavedSettings();
        loadCurrentProfileConfiguration();
    }
    /**

     This method saves the current profile configuration to the database.
     @throws SQLException if there is a problem with the SQL query
     */
    @FXML
    void saveCurrentConfiguration() throws SQLException {
        profileDatabaseManager.updatePropertyInDB(newProfileSettings);
        setProfile(newProfileSettings.copy());
        updateCurrentSettingsInDb();
        SettingsApplier.applyCurrentSettings(arialButton);
        loadCurrentProfileConfiguration();
    }
    /**

     This method updates the current settings in the database.
     @throws SQLException if there is a problem with the SQL query
     */
    private void updateCurrentSettingsInDb() throws SQLException {
        getNewProfileSettings().setName("Current");
        profileDatabaseManager.updatePropertyInDB(getNewProfileSettings());
        getNewProfileSettings().setName(getProfile().getName());
    }
    /**

     This method switches the dark mode on or off and applies the changes to the current settings.
     */
    @FXML
    void switchDarkMode() {
        getNewProfileSettings().switchDarkMode();
        if (newProfileSettings.isInDarkMode()) {
            arialButton.getScene().getRoot().getStylesheets().add(getClass().getResource("/css/darkMode.css").toString());
        } else {
            arialButton.getScene().getRoot().getStylesheets().clear();
            arialButton.getScene().getRoot().getStylesheets().add(getClass().getResource("/css/stylesheet.css").toString());
        }
        loadCurrentProfileConfiguration();
    }
    /**

     Sets the font size of the profile's current configuration to small.
     */
    @FXML
    void setSmallFontSize() {
        getNewProfileSettings().setFontSize(SettingsProfile.SMALL_FONT_SIZE);
        applyUnsavedSettings();
        loadCurrentProfileConfiguration();
    }
    /**

     Sets the font size of the profile's current configuration to medium.
     */
    @FXML
    void setMediumFontSize() {
        getNewProfileSettings().setFontSize(SettingsProfile.MEDIUM_FONT_SIZE);
        applyUnsavedSettings();
        loadCurrentProfileConfiguration();
    }
    /**

     Sets the font size of the profile's current configuration to big.
     */
    @FXML
    void setBigFontSize() {
        getNewProfileSettings().setFontSize(SettingsProfile.BIG_FONT_SIZE);
        applyUnsavedSettings();
        loadCurrentProfileConfiguration();
    }
    /**

     Sets the font family of the profile's configuration to Arial.
     */
    @FXML
    void setArialFamily() {
        getNewProfileSettings().setFontFamily(FontFamily.Arial);
        applyUnsavedSettings();
        loadCurrentProfileConfiguration();
    }/**

     Sets the font family of the profile's configuration to ComicSansMS.
     */
    @FXML
    void setComicSansMSFamily() {
        getNewProfileSettings().setFontFamily(FontFamily.ComicSansMS);
        applyUnsavedSettings();
        loadCurrentProfileConfiguration();
    }
    /**

     Sets the font family of the profile's configuration to TimesNewRoman.
     */
    @FXML
    void setTimesNewRomanFamily() {
        getNewProfileSettings().setFontFamily(FontFamily.TimesNewRoman);
        applyUnsavedSettings();
        loadCurrentProfileConfiguration();
    }
    /**

     Applies any unsaved settings to the profile's configuration and updates the UI to reflect the changes.
     */
    public void applyUnsavedSettings() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Parent root = arialButton.getScene().getRoot();
                if (getNewProfileSettings().isInDarkMode()) {
                    root.getStylesheets().clear();
                    root.getStylesheets().add(SettingsApplier.class.getResource("/css/darkMode.css").toString());
                } else {
                    root.getStylesheets().clear();
                    root.getStylesheets().add(SettingsApplier.class.getResource("/css/stylesheet.css").toString());
                }
                root.getStyleClass().clear();
                root.setStyle("");
                root.getStyleClass().add("root");
                root.getStyleClass().add("root" + getNewProfileSettings().getFontFamily());
                root.setStyle("-fx-font-size: " + getNewProfileSettings().getFontSize());
            }
        });
    }
}
