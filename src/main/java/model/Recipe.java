package model;

import java.sql.ResultSet;
import java.sql.SQLException;
/**

 The Recipe class represents a recipe for making coffee. It contains information such as the name of the recipe,
 the amount of beans and milk needed, the time it takes to brew the coffee, the water pressure required, and an image URL.
 */
public class Recipe {
    /**
     * The default image URL for a recipe.
     */
    public static final String DEFAULT_IMAGE_URL = "default";
    private String name, formerName, imageURL;
    private int brewtime, amountOfBeans, amountOfMilk, waterPressure = 0;
    /**
     * Creates a new Recipe object with default values.
     */
    public Recipe(){
        clear();
    }

    public Recipe(String name, int brewtime, int amountOfBeans, int amountOfMilk, int waterPressure, String imageURL) {
        this.name = name;
        this.brewtime = brewtime;
        this.amountOfBeans = amountOfBeans;
        this.amountOfMilk = amountOfMilk;
        this.waterPressure = waterPressure;
        this.imageURL = imageURL;
    }
    /**
     * Creates a new Recipe object from the given ResultSet object.
     * @param rs A ResultSet object containing the data for the Recipe.
     * @throws SQLException If an error occurs while retrieving data from the ResultSet.
     */
    public Recipe(ResultSet rs) throws SQLException {
        this.name = rs.getString("name");
        this.brewtime = rs.getInt("brewtime");
        this.amountOfBeans = rs.getInt("amount_beans");
        this.amountOfMilk = rs.getInt("amount_added_milk");
        this.waterPressure = rs.getInt("water_pressure");
        this.imageURL = rs.getString("image");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFormerName() {
        return formerName;
    }

    public void setFormerName(String formerName) {
        this.formerName = formerName;
    }

    public int getBrewtime() {
        return brewtime;
    }

    public void setBrewtime(int brewtime) {
        this.brewtime = brewtime;
    }

    public int getAmountOfBeans() {
        return amountOfBeans;
    }


    public int getAmountOfMilk() {
        return amountOfMilk;
    }

    public int getWaterPressure() {
        return waterPressure;
    }

    public void setAmountOfBeans(int amountOfBeans) {
        this.amountOfBeans = amountOfBeans;
    }


    public void setAmountOfMilk(int amountOfMilk) {
        this.amountOfMilk = amountOfMilk;
    }

    public void setWaterPressure(int waterPressure) {
        this.waterPressure = waterPressure;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public void clear(){
        this.name = "";
        this.brewtime = Integer.MAX_VALUE;
        this.amountOfBeans = Integer.MAX_VALUE;
        this.amountOfMilk = Integer.MAX_VALUE;
        this.waterPressure = Integer.MAX_VALUE;
        this.formerName = "";
        this.imageURL = DEFAULT_IMAGE_URL;
    }
}
