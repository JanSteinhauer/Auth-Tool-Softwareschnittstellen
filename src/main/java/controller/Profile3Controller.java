package controller;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
/**
 This class represents the controller for "Profile 3" in the application.
 It extends the abstract class ProfileController and overrides its initialize method to initialize
 the profile settings for "Profile 3". It sets the profile and new profile settings to the ones
 stored in the database, sets the dark mode box, and sets the selected buttons according to the
 saved settings.
 */
public class Profile3Controller extends ProfileController {
    /**
     Initializes the controller by setting the profile and new profile settings to the ones
     stored in the database for "Profile 3", setting the dark mode box, and setting the selected
     buttons according to the saved settings.
     @param url the URL location of the FXML file to initialize.
     @param resourceBundle the ResourceBundle instance associated with the FXML file.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            setProfile(getProfileDatabaseManager().getProfileByName("Profile 3").get());
            setNewProfileSettings(getProfile().copy());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        //loadCurrentProfileConfiguration();
        setDarkModeBox();
        setSelectedButtons();
    }
}
