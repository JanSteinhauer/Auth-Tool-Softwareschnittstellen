package model.databaseManager;

import java.sql.SQLException;

/**

 An abstract class representing a Database Manager with Edit capabilities for a specific type of object T.
 This class extends the DatabaseManager abstract class and adds an abstract method for editing a property in the database.
 Subclasses must implement the updatePropertyInDB method to handle the update operation for a specific property.
 @param <T> The type of object that this DatabaseManagerWithEdit manages.
 */
 public abstract class DatabaseManagerWithEdit<T> extends DatabaseManager<T>{

 /**
 An abstract method that updates a specific property in the database.
 Subclasses must implement this method to handle the update operation for a specific property.
 @param updatedProperty The updated version of the property to be updated in the database.
 @throws SQLException If there is an error updating the property in the database.
 */
    public abstract void updatePropertyInDB(T updatedProperty) throws SQLException;

}
