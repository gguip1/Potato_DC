import crawler.*;

import java.sql.SQLException;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws SQLException {

        /**
         * 크롤링할 URL
         */
        String URL = "https://www.coupangplay.com/catalog";
        CoupangCrawler coupangCrawler = new CoupangCrawler(URL);

        Object[] result = coupangCrawler.scrapTop20();

        ArrayList<Content> cOutput = (ArrayList<Content>) result[0];
        ArrayList<ContentGenre> gOutput = (ArrayList<ContentGenre>) result[1];

        for(int i = 0; i < cOutput.size(); i++){
            System.out.println("---------------------------------------");
            System.out.println(i + " : Genre : " + gOutput.get(i).getGenre());
            System.out.println(i + " : Title : " + cOutput.get(i).getTitle());
            System.out.println(i + " : Img : " + cOutput.get(i).getImg());
            System.out.println(i + " : Description : " + cOutput.get(i).getDescription());
            System.out.println(i + " : Actor : " + cOutput.get(i).getActor());
            System.out.println(i + " : Director : " + cOutput.get(i).getDirector());
            System.out.println("---------------------------------------");
        }

        /**
         * Insert 할때 중복되는 데이터 있는지 확인하고 Insert 해야함
         */
//        DBInsert insert = new DBInsert();
//        insert.MovieTestInsert(5, output.get(0), output.get(1), output.get(2), output.get(3), "actor");
    }
}