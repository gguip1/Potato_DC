package Main;

import crawler.Done.KPCrawler;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class KakaoPageNovelsMain {
    public static void main(String[] args){
        String WEB_DRIVER_ID = "webdriver.chrome.driver";
        String WEB_DRIVER_PATH = "util/driver/chromedriver.exe";

        System.setProperty(WEB_DRIVER_ID, WEB_DRIVER_PATH);

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-popup-blocking");

        WebDriver webDriver = new ChromeDriver(options);

        KPCrawler kpCrawler = new KPCrawler(webDriver, "https://page.kakao.com/menu/10011/screen/84");
        kpCrawler.doALL();
    }
}
