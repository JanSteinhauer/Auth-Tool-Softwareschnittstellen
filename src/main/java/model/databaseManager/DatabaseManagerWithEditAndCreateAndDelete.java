package model.databaseManager;

import java.sql.SQLException;

/**

 This abstract class extends DatabaseManagerWithEdit and adds functionality for creating and deleting entries in the database.
 It declares two abstract methods: createNewPropertyInDB and deletePropertyFromDB which are implemented by its subclasses to
 create and delete entries in the database respectively.
 @param <T> the type of the property being managed by this database manager.
 */
public abstract class DatabaseManagerWithEditAndCreateAndDelete<T> extends DatabaseManagerWithEdit<T> {

    /**
     Creates a new entry for the specified property in the database.
     @param newProperty the new property to be added to the database.
     @throws SQLException if an error occurs while accessing the database.
     */
    public abstract void createNewPropertyInDB(T newProperty) throws SQLException;

    /**
     Deletes the specified property from the database.
     @param propertyToDelete the property to be deleted from the database.
     @throws SQLException if an error occurs while accessing the database.
     */
    public abstract void deletePropertyFromDB(T propertyToDelete) throws SQLException;
}
