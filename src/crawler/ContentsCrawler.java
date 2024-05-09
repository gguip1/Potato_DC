package crawler;

import crawler.base.Crawler;
import crawler.base.LinkCrawler;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class ContentsCrawler implements Crawler {

    private LinkCrawler linkCrawler;

    private WebDriver webDriver;
//    private List<WebElement> element;

    public ContentsCrawler(WebDriver webDriver){
        this.webDriver = webDriver;
    }

    @Override
    public String getDescription(String className, String link) {
        webDriver.get(link);
        String description = "";
        List<WebElement> element = webDriver.findElements(By.className(className));
        for(int index = 0; index < element.size(); index++){
            description += element.get(index).getText();
        }
        return description;
    }

    @Override
    public String getTitle(String className, String link) {
        webDriver.get(link);
        WebElement element = webDriver.findElement(By.className(className));
        String title = element.getText();
        return title;
    }

    @Override
    public String getImg(String className, String link) {
        webDriver.get(link);
        WebElement element = webDriver.findElement(By.className(className));
        String img = element.getAttribute("src");
        return img;
    }

    @Override
    public String getDirector(String className, String link) {
        webDriver.get(link);
        WebElement element = webDriver.findElement(By.className(className));
        String director = element.getText();
        return director;
    }

    @Override
    public String getActor(String className, String link) {
        webDriver.get(link);
        WebElement element = webDriver.findElement(By.className(className));
        String actor = element.getText();
        return actor;
    }
}
