package model;

import java.sql.ResultSet;
import java.sql.SQLException;
/**

 The Ingredient class represents an ingredient that can be used in a recipe. It contains information about the name,
 description, and unit of the ingredient.
 */
public class Ingredient {
    private String name, description, unit;
    /**

     Constructs an Ingredient object with the specified name, description, and unit.
     @param name the name of the ingredient
     @param description the description of the ingredient
     @param unit the unit of measurement for the ingredient
     */
    public Ingredient(String name, String description, String unit) {
        this.name = name;
        this.description = description;
        this.unit = unit;
    }
    /**

     Constructs an Ingredient object by extracting data from the specified ResultSet object.
     @param rs the ResultSet object containing the data for the Ingredient
     @throws SQLException if a database access error occurs or the ResultSet object's cursor is before the first row or after the last row
     */
    public Ingredient(ResultSet rs) throws SQLException {
        this.name = rs.getString("name");
        this.description = rs.getString("description");
        this.unit = rs.getString("unit");


    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }


}
