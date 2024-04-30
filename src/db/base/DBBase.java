package db.base;

import java.sql.*;

public class DBBase {
    private String driver = "com.mysql.cj.jdbc.Driver";
    private String url = "jdbc:mysql://gguip7554.cafe24.com:3306/gguip7554?serverTimezone=UTC&useUniCode=yes&characterEncoding=UTF-8";
    private String user = "gguip7554";
    private String password = "p7554!potato";
    private Connection con = null;
    private ResultSet rs = null;
    private PreparedStatement psmt = null;
    public Connection getCon() {
        return con;
    }

    public void setCon(Connection con) {
        this.con = con;
    }

    public ResultSet getRs() {
        return rs;
    }

    public void setRs(ResultSet rs) {
        this.rs = rs;
    }

    public PreparedStatement getPsmt() {
        return psmt;
    }

    public void setPsmt(PreparedStatement psmt) {
        this.psmt = psmt;
    }
    public DBBase(){
        try {
            Class.forName(driver);
            try {
                con = DriverManager.getConnection(url,user,password);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
