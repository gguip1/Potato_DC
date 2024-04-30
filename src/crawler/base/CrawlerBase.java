package crawler.base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class CrawlerBase {
    private String URL;
    private WebDriver webDriver;

    /**
     * 웹 드라이버 설정
     */
    private String WEB_DRIVER_ID = "webdriver.chrome.driver";
    private String WEB_DRIVER_PATH = "util/driver/chromedriver.exe";

    /**
     * 크롤러 기본 옵션 세팅
     */
    public CrawlerBase(String URL){
        this.URL = URL;
        System.setProperty(WEB_DRIVER_ID, WEB_DRIVER_PATH);

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-popup-blocking");

        webDriver = new ChromeDriver(options);
    }

    public String getURL() {
        return URL;
    }

    public WebDriver getWebDriver() {
        return webDriver;
    }
}
