package Main;

import crawler.Done.NWCrawler;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class NaverWebtoonMain {
    public static void main(String[] args){
        String WEB_DRIVER_ID = "webdriver.chrome.driver";
        String WEB_DRIVER_PATH = "util/driver/chromedriver.exe";

        System.setProperty(WEB_DRIVER_ID, WEB_DRIVER_PATH);

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-popup-blocking");

        WebDriver webDriver = new ChromeDriver(options);

        String naverWebtoon_URL = "https://comic.naver.com/webtoon";
        NWCrawler nwCrawler = new NWCrawler(webDriver, naverWebtoon_URL);

        nwCrawler.doALLNW();
    }
}
