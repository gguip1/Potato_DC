package Main;

import crawler.Doing.NSMCrawler;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class NaverSeriesMovieMain {

    public static void main(String[] args){
        String WEB_DRIVER_ID = "webdriver.chrome.driver";
        String WEB_DRIVER_PATH = "util/driver/chromedriver.exe";

        System.setProperty(WEB_DRIVER_ID, WEB_DRIVER_PATH);

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-popup-blocking");

        WebDriver webDriver = new ChromeDriver(options);

        NSMCrawler nsmCrawler = new NSMCrawler(webDriver, "https://serieson.naver.com/v3/movie/products?tag=&sortType=UPDATE_DESC&price=all");
        nsmCrawler.doALL();
    }
}
