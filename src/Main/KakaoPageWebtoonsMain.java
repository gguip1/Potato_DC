package Main;

import crawler.Doing.KPWCrawler;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class KakaoPageWebtoonsMain {
    public static void main(String[] args){
        String WEB_DRIVER_ID = "webdriver.chrome.driver";
        String WEB_DRIVER_PATH = "util/driver/chromedriver.exe";

        System.setProperty(WEB_DRIVER_ID, WEB_DRIVER_PATH);

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-popup-blocking");

        WebDriver webDriver = new ChromeDriver(options);

        KPWCrawler kpwCrawler = new KPWCrawler(webDriver, "https://page.kakao.com/menu/10010/screen/82");
        kpwCrawler.doAll();
    }
}
