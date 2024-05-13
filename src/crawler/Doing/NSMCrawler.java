package crawler.Doing;

import db.DBInsert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

public class NSMCrawler {
    private WebDriver webDriver;
    private String URL;
    private List<WebElement> element;

    private DBInsert dbInsert = new DBInsert();

    public NSMCrawler(WebDriver webDriver, String URL){
        this.webDriver = webDriver;
        this.URL = URL;
    }

    public void doALL(){
        login();

        try{
            webDriver.get(URL);
            Thread.sleep(200);

            for(int i = 0; i < 15; i ++){
                WebElement button = webDriver.findElement(By.className("ViewMoreButton_view_more_button__eGoJu"));

                button.click();

                System.out.println("click_count : " + i);

                Thread.sleep(300);
            }

            ArrayList<String> links = getLinks();

            System.out.println(links.size());

            for(int link_index = 0; link_index < links.size(); link_index++){
                System.out.println("Starting..." + link_index);

                webDriver.get(links.get(link_index));

                String title = getTitle();
                String director = getDirector();
                String actor = getActor();

                System.out.println("title : " + title);
                System.out.println("director : " + director);
                System.out.println("actor : " + actor);

                Thread.sleep(300);
            }

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private void login(){
        String url = "https://nid.naver.com/nidlogin.login";
        try{
            webDriver.get(url);
            Thread.sleep(20000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private ArrayList<String> getLinks(){
        ArrayList<String> links = new ArrayList<>();

        List<WebElement> price;

        price = webDriver.findElements(By.className("Price_text__AoIDs"));

        element = webDriver.findElements(By.className("ExpandedPoster_item__97XcO"));
        for(int index = 1; index < element.size(); index++){
            if(price.get(index).getText().equals("구매")){
                links.add(element.get(index).getAttribute("href"));
            }
        }

        return links;
    }

    private String getTitle(){
        String title = null;

        element = webDriver.findElements(By.xpath("//*[@id=\"content\"]/div[2]/div/div[1]/div[1]/strong"));

        for(int index = 0; index < element.size(); index++){
            title = element.get(index).getText();
        }

        return title;
    }

    private String getDirector(){
        String director = null;

        element = webDriver.findElements(By.xpath("//*[@id=\"content\"]/div[2]/ul/li[1]/div[1]/div/a/span"));

        for(int index = 0; index < element.size(); index++){
            director = element.get(index).getText();
        }

        return director;
    }

    private String getActor(){
        String actor = "";

        element = webDriver.findElements(By.xpath("//*[@id=\"content\"]/div[2]/ul/li[1]/div[2]/div"));

        for(int index = 0; index < element.size(); index++){
            actor += element.get(index).getText();
        }

        return actor;
    }

    private String getDescription(){
        String description = null;

        element = webDriver.findElements(By.xpath("//*[@id=\"content\"]/div[2]/ul/li[1]/div[3]/p"));

        for(int index = 0; index < element.size(); index++){
            description = element.get(index).getText();
        }

        return description;
    }
}
