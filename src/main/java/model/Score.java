package model;

import java.sql.ResultSet;
import java.sql.SQLException;
/**

 The Score class represents a highscore in the game.
 It contains a unique identifier and a highscore value.
 */
public class Score {
    private int id, highscore;

    public Score(int id, int highscore) {
        this.id = id;
        this.highscore = highscore;
    }

    public Score(ResultSet resultSet) throws SQLException {
        this.id = resultSet.getInt("idScore");
        this.highscore = resultSet.getInt("highscore");
    }

    public int getId() {
        return id;
    }

    public int getHighscore() {
        return highscore;
    }
}
