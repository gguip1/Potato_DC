package crawler;

import crawler.base.CrawlerBase;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;

public class CoupangCrawler extends CrawlerBase{

    public CoupangCrawler(String URL) {
        super(URL);
    }
    private WebDriver webDriver = super.getWebDriver();
    private WebElement element;

    public ArrayList<String> activate(){
        ArrayList<String> results = new ArrayList<>();
        try{
            webDriver.get(super.getURL());
            Thread.sleep(2000);

            /**
             * html의 xpath를 이용해서 크롤링
             * 플랫폼 별 xpath 위치를 설정해서 자동화할 필요가 있음
             */
            element = webDriver.findElement(By.xpath("/html/body/div/div[1]/div[2]/div[2]/div[3]/div[1]/div[2]/div/span[1]"));
            String description = element.getText();
            element = webDriver.findElement(By.xpath("/html/body/div/div[1]/div[2]/div[2]/div[3]/div[2]/div[2]/div[1]/span[2]"));
            String genre = element.getText();
            element = webDriver.findElement(By.xpath("/html/body/div/div[1]/div[2]/div[2]/div[3]/div[2]/div[2]/div[2]/span[2]"));
            String actor = element.getText();
            element = webDriver.findElement(By.xpath("/html/body/div/div[1]/div[2]/div[2]/div[3]/div[2]/div[2]/div[3]/span[2]"));
            String director = element.getText();
            element = webDriver.findElement(By.xpath("/html/body/div/div[1]/div[2]/div[2]/div[3]/div[2]/div[2]/div[4]/span[2]"));
            String writer = element.getText();

            /**
             * 크롤링 출력
             */
            System.out.println(description);
            System.out.println(genre);
            System.out.println(actor);
            System.out.println(director);
            System.out.println(writer);

            results.add(description);
            results.add(actor);
            results.add(director);
            results.add(actor);

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        finally{
            webDriver.close();
        }
        return results;
    }
}
