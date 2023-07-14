package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.layout.AnchorPane;


/**

 The SettingsController class is responsible for managing the settings of different profiles.

 It contains methods to switch between profiles and apply unsaved settings.
 */
public class SettingsController {
    @FXML
    private Profile1Controller profile1Controller;
    @FXML
    private Profile2Controller profile2Controller;
    @FXML
    private Profile3Controller profile3Controller;
    @FXML
    private Tab tabProfile1, tabProfile2, tabProfile3;
    @FXML
    private AnchorPane paneProfile1, paneProfile2, paneProfile3;

    private boolean isFirstTime = true;

    /**
     Switches to the first profile and applies unsaved settings if there are any.
     If it's the first time switching to the profile, it skips the unsaved settings.
     */
    @FXML
    void switchToProfile1(){
        if (isFirstTime){
            isFirstTime = false;
            return;
        }
        profile1Controller.applyUnsavedSettings();

    }

    /**
     Switches to the second profile and applies unsaved settings if there are any.
     */
    @FXML
    void switchToProfile2(){
        profile2Controller.applyUnsavedSettings();
    }

    /**
     Switches to the third profile and applies unsaved settings if there are any.
     */
    @FXML
    void switchToProfile3(){
        profile3Controller.applyUnsavedSettings();
    }
}
