package crawler.base;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

public class LinkCrawler{
    private String URL;
    private WebDriver webDriver;
    private List<WebElement> element;

    public LinkCrawler(String URL, WebDriver webDriver) {
        this.URL = URL;
        this.webDriver = webDriver;
    }

    public WebDriver getWebDriver(){
        return webDriver;
    }

    public List<String> getLink(String className) {
        List<String> links = new ArrayList<>();

        webDriver.get(URL);

        element = webDriver.findElements(By.className(className));
        for (int index = 0; index < element.size(); index++) {
            links.add(element.get(index).getAttribute("href"));
        }

        return links;
    }
}
