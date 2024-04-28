import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class CoupangScrapEX {

    private WebDriver driver;
    private WebElement element;
    private String url;
    public static String WEB_DRIVER_ID = "webdriver.chrome.driver";
    public static String WEB_DRVIER_PATH = "chromedriver/chromedriver.exe";
    public CoupangScrapEX(){
        System.setProperty(WEB_DRIVER_ID, WEB_DRVIER_PATH);

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-popup-blocking");

        driver = new ChromeDriver(options);

        url = "https://www.coupangplay.com/content/a37abef6-2043-40e4-ab89-d11ae2f04b6e";
    }

    public void activate(){
        try{
            driver.get(url);
            Thread.sleep(2000);

            element = driver.findElement(By.xpath("/html/body/div/div[1]/div[2]/div[2]/div[3]/div[1]/div[2]/div/span[1]"));
            String description = element.getText();
            element = driver.findElement(By.xpath("/html/body/div/div[1]/div[2]/div[2]/div[3]/div[2]/div[2]/div[1]/span[2]"));
            String genre = element.getText();
            element = driver.findElement(By.xpath("/html/body/div/div[1]/div[2]/div[2]/div[3]/div[2]/div[2]/div[2]/span[2]"));
            String actor = element.getText();
            element = driver.findElement(By.xpath("/html/body/div/div[1]/div[2]/div[2]/div[3]/div[2]/div[2]/div[3]/span[2]"));
            String director = element.getText();
            element = driver.findElement(By.xpath("/html/body/div/div[1]/div[2]/div[2]/div[3]/div[2]/div[2]/div[4]/span[2]"));
            String writer = element.getText();

            System.out.println(description);
            System.out.println(genre);
            System.out.println(actor);
            System.out.println(director);
            System.out.println(writer);

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        finally{
            driver.close();
        }
    }
}