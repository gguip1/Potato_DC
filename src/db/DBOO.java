package db;

import java.sql.*;

public class DBOO {
    public static void main(String[] args) throws SQLException {

        String driver = "com.mysql.cj.jdbc.Driver";
        String url = "jdbc:mysql://gguip7554.cafe24.com:3306/gguip7554?serverTimezone=UTC&useUniCode=yes&characterEncoding=UTF-8";
        String user = "gguip7554";
        String password = "p7554!potato";

        Connection con = null;
        ResultSet rs = null;
        PreparedStatement psmt = null;

        try {
            Class.forName(driver);
            System.out.println("DB정상연결");
            try {
                con = DriverManager.getConnection(url,user,password);
                System.out.println("DB계정일치");
            } catch (SQLException e) {
                System.out.println("DB계정불일치");
                e.printStackTrace();
            }
        } catch (ClassNotFoundException e) {
            System.out.println("DB연결실패");
            e.printStackTrace();
        }

        try{
            String sql = "select * from movie_test";
            psmt = con.prepareStatement(sql);
            rs = psmt.executeQuery();

            while(rs.next()){
                int a = rs.getInt("movie_id");
                String b = rs.getString("title");
                String c = rs.getString("description");
                System.out.println("id : " + a);
                System.out.println("title : " + b);
                System.out.println("description : " + c);
            }
        }catch(Exception e){
            e.printStackTrace();
            System.out.println("쿼리실패");
        }
    }
}
