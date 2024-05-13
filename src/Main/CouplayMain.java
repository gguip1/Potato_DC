package Main;

import crawler.Done.CPCrawler;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class CouplayMain {
    public static void main(String[] args){
        String WEB_DRIVER_ID = "webdriver.chrome.driver";
        String WEB_DRIVER_PATH = "util/driver/chromedriver.exe";

        System.setProperty(WEB_DRIVER_ID, WEB_DRIVER_PATH);

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-popup-blocking");

        WebDriver webDriver = new ChromeDriver(options);

        String coupang_URL = "https://www.coupangplay.com/catalog";
        CPCrawler cpCrawler = new CPCrawler(webDriver, coupang_URL);

        cpCrawler.doALL();
    }
}
