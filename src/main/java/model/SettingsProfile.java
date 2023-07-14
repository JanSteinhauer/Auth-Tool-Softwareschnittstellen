package model;

import java.sql.ResultSet;
import java.sql.SQLException;
/**

 The SettingsProfile class represents a user's settings profile, which includes properties such as name, whether the user
 is using dark mode, font size, and font family.
 */
public class SettingsProfile {
    public static final int SMALL_FONT_SIZE = 12;
    public static final int MEDIUM_FONT_SIZE = 14;
    public static final int BIG_FONT_SIZE = 18;
    private String name;
    private boolean inDarkMode;
    private int fontSize;
    private FontFamily fontFamily;

    public SettingsProfile(String name, boolean inDarkMode, int fontSize, FontFamily fontFamily) {
        this.name = name;
        this.inDarkMode = inDarkMode;
        this.fontSize = fontSize;
        this.fontFamily = fontFamily;
    }
    /**
     * Creates a new SettingsProfile object based on the specified ResultSet.
     *
     * @param resultSet the ResultSet object to create the SettingsProfile from
     */
    public SettingsProfile(ResultSet resultSet){
        try {
            this.name = resultSet.getString("name");
            this.inDarkMode = convertIntToBoolean(resultSet.getInt("inDarkMode"));
            this.fontSize = resultSet.getInt("font_size");
            this.fontFamily = convertStringToFontFamily(resultSet.getString("font_family"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    /**
     * Creates a new SettingsProfile object with default values.
     */
    public SettingsProfile(){
        this.name = "Default";
        this.inDarkMode = false;
        this.fontSize = MEDIUM_FONT_SIZE;
        this.fontFamily = FontFamily.Arial;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isInDarkMode() {
        return inDarkMode;
    }
    /**
     * Switches the dark mode on/off.
     */
    public void switchDarkMode(){
        this.inDarkMode = !isInDarkMode();
    }
    /**
     * Returns whether the user is using dark mode as an integer value (1 if true, 0 if false).
     *
     * @return 1 if the user is using dark mode, 0 otherwise
     */
    public int isInDarkModeAsInt(){
        if (inDarkMode) {
            return 1;
        } else {
            return 0;
        }
    }

    private boolean convertIntToBoolean(int integer){
        return integer != 0;
    }

    public void setInDarkMode(boolean inDarkMode) {
        this.inDarkMode = inDarkMode;
    }

    public int getFontSize() {
        return fontSize;
    }

    public void setFontSize(int fontSize) {
        this.fontSize = fontSize;
    }

    public FontFamily getFontFamily() {
        return fontFamily;
    }

    public void setFontFamily(FontFamily fontFamily) {
        this.fontFamily = fontFamily;
    }
    /**

     Converts a string representation of a font family into the corresponding FontFamily enum value.
     @param string the string representation of the font family to convert
     @return the FontFamily enum value corresponding to the input string, or Arial if the string is not recognized
     */
    public FontFamily convertStringToFontFamily(String string){
        switch (string){
            case "ComicSansMS": return FontFamily.ComicSansMS;
            case "TimesNewRoman": return FontFamily.TimesNewRoman;
            default: return FontFamily.Arial;
        }
    }
    /**

     Creates a deep copy of this SettingsProfile object.

     @return a new SettingsProfile object with the same properties as this one
     */
    public SettingsProfile copy(){
        SettingsProfile copy = new SettingsProfile();
        copy.setInDarkMode(isInDarkMode());
        copy.setName(getName());
        copy.setFontSize(getFontSize());
        copy.setFontFamily(getFontFamily());

        return copy;
    }
}
