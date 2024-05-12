import crawler.Content;
import crawler.ContentGenre;
import crawler.coupang.CPCrawler;
import crawler.naverWebtoon.NWCrawler;
import db.DBInsert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.sql.Array;
import java.sql.SQLException;
import java.sql.SQLOutput;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws SQLException {

        String WEB_DRIVER_ID = "webdriver.chrome.driver";
        String WEB_DRIVER_PATH = "util/driver/chromedriver.exe";

        System.setProperty(WEB_DRIVER_ID, WEB_DRIVER_PATH);

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-popup-blocking");

        WebDriver webDriver = new ChromeDriver(options);

        String coupang_URL = "https://www.coupangplay.com/catalog";
        CPCrawler cpCrawler = new CPCrawler(webDriver, coupang_URL);
        Object[] cpResult = cpCrawler.getResult();
        ArrayList<Content> cpContents = (ArrayList<Content>) cpResult[0];
        ArrayList<ContentGenre> cpGenres = (ArrayList<ContentGenre>) cpResult[1];
//        System.out.println(cpResult);

        for(int i = 0; i < cpGenres.size(); i++){
            System.out.println("CP : " + cpGenres.get(i).getGenre());
        }

        String naverWebtoon_URL = "https://comic.naver.com/webtoon";
        NWCrawler nwCrawler = new NWCrawler(webDriver, naverWebtoon_URL);
        Object[] nwResult = nwCrawler.getResult();
        ArrayList<Content> nwContents = (ArrayList<Content>) nwResult[0];
        ArrayList<ContentGenre> nwGenres = (ArrayList<ContentGenre>) nwResult[1];
        System.out.println(nwGenres.get(0).getGenre());
//        System.out.println(nwResult);
//
//        for(int i = 0; i < nwGenres.size(); i++){
//            System.out.println("NW : " + nwGenres.get(i).getGenre());
//        }

        webDriver.close();

        DBInsert dbInsert = new DBInsert();
        for(int index = 0; index < cpContents.size(); index++){
            dbInsert.contentInsert(cpContents.get(index).getTitle(), cpContents.get(index).getImg(), cpContents.get(index).getDescription(), cpContents.get(index).getDirector(), cpContents.get(index).getActor(), "couplay");
        }

        for(int index = 0; index < nwContents.size(); index++){
            dbInsert.contentInsert(nwContents.get(index).getTitle(), nwContents.get(index).getImg(), nwContents.get(index).getDescription(), nwContents.get(index).getDirector(), nwContents.get(index).getActor(), "naverwebtoon");
        }

//        ArrayList<String> test = new ArrayList<>();
//        test.add("공포");
//        test.add("감자");
//        test.add("로맨스");
//        test.add("고구마");
//        test.add("대박");

        for(int i = 0; i < cpContents.size(); i++){
            String genres = cpGenres.get(i).getGenre().toString();
            String[] genres_ = genres.replace("[","").replace("]","").split(", ");
            Integer movie_id = dbInsert.searchMovieID("couplay", cpContents.get(i).getTitle());
            for(String genre : genres_){
                System.out.println(movie_id + " : " + genre);
                Integer genre_id = dbInsert.searchGenreID(genre);
                if(movie_id != 0 && genre_id != null){
                    dbInsert.genreInsert(movie_id, genre_id, "couplay_genre");
                } else {
                    System.out.println("----");
                    System.out.println(genre_id);
                    System.out.println(genre);
                    System.out.println();
                    System.out.println(movie_id);
                    System.out.println(cpContents.get(i).getTitle());
                    System.out.println("----");
                }
            }
        }

        for(int i = 0; i < nwContents.size(); i++){
            String genres = nwGenres.get(i).getGenre().toString();
            String[] genres_ = genres.replace("[","").replace("]","").split(", ");
            Integer movie_id = dbInsert.searchMovieID("naverwebtoon", nwContents.get(i).getTitle());
            for(String genre : genres_){
                System.out.println(movie_id + " : " + genre);
                Integer genre_id = dbInsert.searchGenreID(genre);
                if(movie_id != 0 && genre_id != null){
                    dbInsert.genreInsert(movie_id, genre_id, "naverwebtoon_genre");
                } else {
                    System.out.println("----");
                    System.out.println(genre_id);
                    System.out.println(genre);
                    System.out.println();
                    System.out.println(movie_id);
                    System.out.println(nwContents.get(i).getTitle());
                    System.out.println("----");
                }
            }
        }
    }
}