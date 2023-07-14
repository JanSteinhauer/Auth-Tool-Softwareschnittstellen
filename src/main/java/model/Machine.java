package model;

import java.sql.ResultSet;
import java.sql.SQLException;
/**

 This class represents a Machine entity, with an id, name, and description.
 It provides methods to get and set these attributes, and a clear method to reset them.
 A Machine can be created with the default constructor, which initializes its attributes to default values,
 or with a constructor that takes an id, name, and description as arguments.
 This class also provides a constructor that takes a ResultSet object as argument and extracts the values of its
 fields from it. The ResultSet object is expected to contain the following fields: "idMachine", "name", and "decription".

 */
public class Machine {

    private int id;
    private String name, description;

    public Machine(){
        clear();
    }

    public Machine(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public Machine(ResultSet rs) throws SQLException {
        this.id = rs.getInt("idMachine");
        this.name = rs.getString("name");
        this.description = rs.getString("decription");

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public void clear(){
        this.name="";
        this.id = Integer.MAX_VALUE;
        this.description = "";
    }
}
