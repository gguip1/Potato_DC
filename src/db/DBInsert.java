package db;

import db.base.DBBase;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DBInsert extends DBBase {

    /**
     * MovieTest 테이블 INSERT 메소드 테스트용
     * @param movie_id
     * @param title
     * @param img
     * @param description
     * @param director
     * @param actor
     * @throws SQLException
     */
    public void MovieTestInsert(int movie_id, String title, String img, String description, String director, String actor) throws SQLException {
        /**
         * INSERT SQL
         */
        String sql = "INSERT INTO movie_test(movie_id, title, img, description, director, actor)" +
                "VALUES (?,?,?,?,?,?)";

        super.setStatement(super.getConnection().prepareStatement(sql));
        PreparedStatement state = super.getStatement();

        /**
         * VALUES (?,?,?,?,?,?)
         * 각 INDEX INSERT DATA 설정
         */
        state.setInt(1, movie_id);
        state.setString(2, title);
        state.setString(3, img);
        state.setString(4, description);
        state.setString(5, director);
        state.setString(6, actor);

        state.executeUpdate();
    }
}
