package model;

import model.databaseManager.ScoreDatabaseManager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class User {

    private int id;
    public String username;
    private String mail;
    private String password;

    private int score_id;
    private int highscore = 0;

    private final ScoreDatabaseManager scoreDatabaseManager = new ScoreDatabaseManager();

    public User(int id, String username, String mail, String password) {
        this.id = id;
        this.username = username;
        this.mail = mail;
        this.password = password;
    }

    public User(ResultSet rs) throws SQLException {
        this.id = rs.getInt("idUser");
        this.username = rs.getString("name");
        this.mail = rs.getString("mail");
        this.score_id = rs.getInt("Score_idScore");
        this.password = rs.getString("password");
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getScore_id() {
        return score_id;
    }

    public String getUsername() {
        return username;
    }

    public String getMail() {
        return mail;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getHighscore() throws SQLException {
        return getHighscoreFromDB();
    }

    public int getHighscoreFromDB() throws SQLException {
        Optional<Score> score = scoreDatabaseManager.getScoreById(getScore_id());
        if (score.isPresent()) {
            return score.get().getHighscore();
        } else {
            return 0;
        }
    }

}
