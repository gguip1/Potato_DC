package db;

import db.base.DBBase;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DBInsert extends DBBase {

    public void MovieTestInsert(int movie_id, String title, String img, String description, String director, String actor) throws SQLException {
        String sql = "insert into movie_test(movie_id, title, img, description, director, actor)" +
                "values (?,?,?,?,?,?)";

        super.setPsmt(super.getCon().prepareStatement(sql));
        PreparedStatement psmt = super.getPsmt();

        psmt.setInt(1, movie_id);
        psmt.setString(2, title);
        psmt.setString(3, img);
        psmt.setString(4, description);
        psmt.setString(5, director);
        psmt.setString(6, actor);
        psmt.executeUpdate();
    }
}
