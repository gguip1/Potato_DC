package db;

import db.base.DBBase;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DBInsert extends DBBase {

    /**
     * MovieTest 테이블 INSERT 메소드 테스트용
     * @param title
     * @param img
     * @param description
     * @param director
     * @param actor
     * @throws SQLException
     */
    public void MovieTestInsert(String title, String img, String description, String director, String actor) throws SQLException {
        /**
         * INSERT SQL
         */
        String sql = "INSERT INTO movie_test(title, img, description, director, actor)" +
                "VALUES (?,?,?,?,?)";

        super.setStatement(super.getConnection().prepareStatement(sql));
        PreparedStatement state = super.getStatement();

        /**
         * VALUES (?,?,?,?,?)
         * 각 INDEX INSERT DATA 설정
         */
        state.setString(1, title);
        state.setString(2, img);
        state.setString(3, description);
        state.setString(4, director);
        state.setString(5, actor);

        state.executeUpdate();
    }
}
