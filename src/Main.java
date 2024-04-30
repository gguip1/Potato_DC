import crawler.CoupangCrawler;
import db.DBInsert;

import java.sql.SQLException;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws SQLException {

        /**
         * 크롤링할 URL
         */
        String URL = "https://www.coupangplay.com/content/a37abef6-2043-40e4-ab89-d11ae2f04b6e";
        CoupangCrawler coupangCrawler = new CoupangCrawler(URL);

        ArrayList<String> output = coupangCrawler.activate();

        DBInsert insert = new DBInsert();
        insert.MovieTestInsert(5, output.get(0), output.get(1), output.get(2), output.get(3), "actor");
    }
}