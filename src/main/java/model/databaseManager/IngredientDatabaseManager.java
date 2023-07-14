package model.databaseManager;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Ingredient;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 This class represents the database manager for the Ingredient objects.
 It extends the abstract class DatabaseManagerWithEdit and overrides its getAllFromProperty and updatePropertyInDB methods.
 The getAllFromProperty method retrieves all ingredients from the Ingredient table in the database and returns them as an ObservableList of Ingredient objects.
 The updatePropertyInDB method updates the description of an ingredient in the database.
 */
public class IngredientDatabaseManager extends DatabaseManagerWithEdit<Ingredient> {

    @Override
    public void updatePropertyInDB(Ingredient updatedProperty) throws SQLException {
        setStmt(getConnection().prepareStatement("UPDATE Ingredient SET `description` = ?, `unit` = ? WHERE `name` = ? "));
        getStmt().setString(1, updatedProperty.getDescription());
        getStmt().setString(2, updatedProperty.getUnit());
        getStmt().setString(3, updatedProperty.getName());
        getStmt().executeUpdate();
        getStmt().close();
    }

    @Override
    public ObservableList<Ingredient> getAllFromProperty() throws SQLException {
        ObservableList<Ingredient> ingredients = FXCollections.observableArrayList();
        setStmt(getConnection().prepareStatement("SELECT * FROM Ingredient"));
        ResultSet rs = getStmt().executeQuery();
        while (rs.next()) {
            Ingredient ingredient = new Ingredient(rs);
            ingredients.add(ingredient);
        }
        getStmt().close();
        return ingredients;
    }
}
