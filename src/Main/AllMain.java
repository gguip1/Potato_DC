package Main;

import crawler.Done.CPCrawler;
import crawler.Done.KPCrawler;
import crawler.Done.NWCrawler;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class AllMain {
    public static void main(String[] args){
        String WEB_DRIVER_ID = "webdriver.chrome.driver";
        String WEB_DRIVER_PATH = "util/driver/chromedriver.exe";

        System.setProperty(WEB_DRIVER_ID, WEB_DRIVER_PATH);

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-popup-blocking");

        WebDriver nwWebDriver = new ChromeDriver(options);
        WebDriver cpWebDriver = new ChromeDriver(options);
        WebDriver kpWebDriver = new ChromeDriver(options);

        String naverWebtoon_URL = "https://comic.naver.com/webtoon";
        NWCrawler nwCrawler = new NWCrawler(nwWebDriver, naverWebtoon_URL);

        String coupang_URL = "https://www.coupangplay.com/catalog";
        CPCrawler cpCrawler = new CPCrawler(cpWebDriver, coupang_URL);

        String kpNovel_URL = "https://page.kakao.com/menu/10011/screen/84";
        KPCrawler kpCrawler = new KPCrawler(kpWebDriver, kpNovel_URL);

        nwCrawler.doALLNW();
        cpCrawler.doALL();
        kpCrawler.doALL();
    }
}
