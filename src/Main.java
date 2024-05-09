import crawler.coupang.CouplayCrawler;
import crawler.*;
import crawler.naverWebtoon.NaverWebtoon;
import db.DBInsert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

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

//        String coupang_URL = "https://www.coupangplay.com/catalog";
//
//        CouplayCrawler couplayCrawler = new CouplayCrawler(webDriver, coupang_URL);
//
//        Object[] couplay = couplayCrawler.scrapCouplay(
//                "OpenFeedCarousel_clipsThumbnail__Ixioy"
//                ,"OpenFeedCarousel_clipsItemWrapper__1FNyR",
//                "__react-ellipsis-js-content",
//                "OpenTitleHeroSection_heroTagName__dZeHv",
//                "OpenTitleHeroSection_heroTagDetails__O5T5N"
//        ); //coupang ClassName
//
//        ArrayList<Content> couplayContent = (ArrayList<Content>) couplay[0];
//        ArrayList<ContentGenre> couplayGenre = (ArrayList<ContentGenre>) couplay[1];
//
//        DBInsert dbInsert = new DBInsert();
//
//        for(int index = 0; index < couplayContent.size(); index++){
//            String title = couplayContent.get(index).getTitle().replace("[","").replace("]", "");
//            String description =  couplayContent.get(index).getDescription().replace("[","").replace("]", "");
//            String img = couplayContent.get(index).getImg().replace("[","").replace("]", "");
//            String actor = couplayContent.get(index).getActor().replace("[","").replace("]", "");
//            String director = couplayContent.get(index).getDirector().replace("[","").replace("]", "");
//            System.out.println("index : " + index);
//            System.out.println("title : " + title);
//            System.out.println("description : " + description);
//            System.out.println("img : " + img);
//            System.out.println("actor : " + actor);
//            System.out.println("director : " + director);
//            dbInsert.contentInsert(title, img, description, director, actor, "movie_test");
//        }

        String naverWebtoon_URL = "https://comic.naver.com/webtoon";

        NaverWebtoon naverWebtoon = new NaverWebtoon(webDriver, naverWebtoon_URL);

        Object[] naverWeb = naverWebtoon.scrapCouplay(
                "Poster__image--d9XTI",
                "Poster__link--sopnC",
                "EpisodeListInfo__summary--Jd1WG",
                "ContentMetaInfo__profile--b6niL",
                "TagGroup__tag--xu0OH"
        );

        System.out.println(naverWeb);
    }
}