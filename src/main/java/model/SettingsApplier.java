package model;

import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.Parent;
import model.databaseManager.SettingsProfileDatabaseManager;

import java.sql.SQLException;
/**

 The SettingsApplier class is responsible for applying the current user settings to the GUI. It reads the current user
 settings from the database, and then sets the appropriate style, font size, and font family for the GUI components.
 The class uses JavaFX to apply the changes, and runs the necessary operations on the JavaFX application thread
 using Platform.runLater().
 */
public class SettingsApplier {
    // An instance of the SettingsProfileDatabaseManager class used to access the user settings in the database.
    private static final SettingsProfileDatabaseManager profileDatabaseManager = new SettingsProfileDatabaseManager();
    /**
     * This method applies the current user settings to the specified node. It reads the user settings from the database
     * using the SettingsProfileDatabaseManager, and then sets the appropriate style, font size, and font family for
     * the GUI components.
     *
     * @param node The node to which the current settings will be applied.
     */
    public static void applyCurrentSettings(Node node){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                SettingsProfile currentSettings = null;
                try {
                    currentSettings = profileDatabaseManager.getCurrentSettings().get();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                Parent root = node.getScene().getRoot();
                if (currentSettings.isInDarkMode()) {
                    root.getStylesheets().clear();
                    root.getStylesheets().add(SettingsApplier.class.getResource("/css/darkMode.css").toString());
                } else {
                    root.getStylesheets().clear();
                    root.getStylesheets().add(SettingsApplier.class.getResource("/css/stylesheet.css").toString());
                }
                root.getStyleClass().clear();
                root.setStyle("");
                root.getStyleClass().add("root");
                root.getStyleClass().add("root" + currentSettings.getFontFamily());
                root.setStyle("-fx-font-size: " + currentSettings.getFontSize());
            }
        });
    }
}
