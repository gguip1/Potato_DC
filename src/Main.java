import crawler.Content;
import crawler.ContentGenre;
import crawler.coupang.CPCrawler;
import crawler.naverWebtoon.NWCrawler;
import db.DBInsert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.*;
import java.sql.SQLException;
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
        System.out.println(cpResult);


        String naverWebtoon_URL = "https://comic.naver.com/webtoon";
        NWCrawler nwCrawler = new NWCrawler(webDriver, naverWebtoon_URL);
        Object[] nwResult = nwCrawler.getResult();
        ArrayList<Content> nwContents = (ArrayList<Content>) nwResult[0];
        ArrayList<ContentGenre> nwGenres = (ArrayList<ContentGenre>) nwResult[1];
        System.out.println(nwResult);

        DBInsert dbInsert = new DBInsert();
        for(int index = 0; index < cpContents.size(); index++){
            dbInsert.contentInsert(cpContents.get(index).getTitle(), cpContents.get(index).getImg(), cpContents.get(index).getDescription(), cpContents.get(index).getDirector(), cpContents.get(index).getActor(), "movie_test");
        }

        for(int index = 0; index < nwContents.size(); index++){
            dbInsert.contentInsert(nwContents.get(index).getTitle(), nwContents.get(index).getImg(), nwContents.get(index).getDescription(), nwContents.get(index).getDirector(), nwContents.get(index).getActor(), "movie_test");
        }
    }
}