package model.databaseManager;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Database;
import model.Recipe;
import model.User;

import java.sql.*;
import java.util.Optional;
/**

 This class handles the CRUD operations for the Recipe object in the database. It extends the DatabaseManagerWithEditAndCreateAndDelete class,
 which allows it to create, read, update, and delete recipe objects in the database. It also includes a method to find a recipe by their name in the database.
 */
public class RecipeDatabaseManager extends DatabaseManagerWithEditAndCreateAndDelete<Recipe> {
    /**
     * Returns an observable list of all the recipes in the database.
     *
     * @return an observable list of all the recipes in the database
     * @throws SQLException if there is a problem with the database connection or the SQL query
     */
    @Override
    public ObservableList<Recipe> getAllFromProperty() throws SQLException {
        ObservableList<Recipe> recipes = FXCollections.observableArrayList();
        setStmt(getConnection().prepareStatement("SELECT * FROM Recipe"));
        ResultSet rs = getStmt().executeQuery();
        while (rs.next()) {
            Recipe recipe = new Recipe(rs);
            recipes.add(recipe);
        }
        getStmt().close();
        return recipes;
    }
    /**
     * Updates the given recipe object in the database.
     *
     * @param updatedProperty the recipe object to update in the database
     * @throws SQLException if there is a problem with the database connection or the SQL query
     */
    @Override
    public void updatePropertyInDB(Recipe updatedProperty) throws SQLException {
        setStmt(getConnection().prepareStatement("UPDATE Recipe SET `name` = ?, `brewtime` = ?, `amount_beans` = ?, `amount_added_water` = ?, `amount_added_milk` = ?, `water_pressure` = ?, `image` = ? WHERE `name` = ?"));
        getStmt().setString(1, updatedProperty.getName());
        getStmt().setInt(2, updatedProperty.getBrewtime());
        getStmt().setInt(3, updatedProperty.getAmountOfBeans());
        getStmt().setInt(4, 0);
        getStmt().setInt(5, updatedProperty.getAmountOfMilk());
        getStmt().setInt(6, updatedProperty.getWaterPressure());
        getStmt().setString(7, updatedProperty.getImageURL());
        getStmt().setString(8, updatedProperty.getFormerName());
        getStmt().executeUpdate();
        getStmt().close();
    }
    /**
     * Creates a new recipe object in the database.
     *
     * @param newProperty the recipe object to create in the database
     * @throws SQLException if there is a problem with the database connection or the SQL query
     */
    @Override
    public void createNewPropertyInDB(Recipe newProperty) throws SQLException {
        setStmt(getConnection().prepareStatement("INSERT INTO Recipe (`name`, `brewtime`, `amount_beans`, `amount_added_water`, `amount_added_milk`, `water_pressure`, `filling_capacity`, `water_temperature`, `Spoon_idspoon`, `Cup_idcup`, `image`) VALUES (?, ?, ?, ?, ?, ?, 25, 90, 2, 2, ?)"));
        getStmt().setString(1, newProperty.getName());
        getStmt().setInt(2, newProperty.getBrewtime());
        getStmt().setInt(3, newProperty.getAmountOfBeans());
        getStmt().setInt(4, 0);
        getStmt().setInt(5, newProperty.getAmountOfMilk());
        getStmt().setInt(6, newProperty.getWaterPressure());
        getStmt().setString(7, newProperty.getImageURL());

        getStmt().executeUpdate();
        getStmt().close();
    }
    /**
     * Deletes the given recipe object from the database.
     *
     * @param propertyToDelete the recipe object to delete from the database
     * @throws SQLException if there is a problem with the database connection or the SQL query
     */
    @Override
    public void deletePropertyFromDB(Recipe propertyToDelete) throws SQLException {
        setStmt(getConnection().prepareStatement("DELETE FROM Recipe WHERE `name` = ?"));
        getStmt().setString(1, propertyToDelete.getName());
        getStmt().execute();
        getStmt().close();
    }
    /**

     Searches for a recipe in the database by their name.
     @param name the name of the recipe to search for
     @return an Optional containing the recipe if found, or  Optional.empty() if not found
     @throws SQLException if there is an error accessing the database
     */
    public Optional<Recipe> findRecipeByName(String name) throws SQLException {
        setStmt(getConnection().prepareStatement("SELECT * FROM Recipe WHERE `name` = ?"));
        getStmt().setString(1, name);
        ResultSet rs = getStmt().executeQuery();
        if (!rs.next()){
            return Optional.empty();
        }

        return Optional.ofNullable(new Recipe(rs));
    }
}
