package crawler.Doing;

import db.DBInsert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class KPWCrawler {
    private WebDriver webDriver;
    private String URL;
    private List<WebElement> element;

    private DBInsert dbInsert = new DBInsert();

    public KPWCrawler(WebDriver webDriver, String URL){
        this.webDriver = webDriver;
        this.URL = URL;
    }

    public void doAll(){

        login();

        try{
            webDriver.get(URL);
            Thread.sleep(300);

            for(int i = 0; i < 10; i++){
                new Actions(webDriver).sendKeys(Keys.END).perform();

                Thread.sleep(100);

                System.out.println("End Count : " + i);
            }

            ArrayList<String> links = getLinks();
            int links_size = links.size();
            System.out.println("Links : " + links_size);

            for(int link_index = 0; link_index < links_size; link_index++){
                System.out.println("Starting..." + link_index);

                webDriver.get(links.get(link_index));
                Thread.sleep(500);

                String title = getTitle();
                String img = getImg();
                String genre = getGenre();
                String director = getDirector();
                String description = getDescription();

                if(description == null || title == null || img == null || genre == null || director == null){
                    System.out.println(description);
                    System.out.println(title);
                    System.out.println(img);
                    System.out.println(genre);
                    System.out.println(description);
                }

                try{
                    dbInsert.contentInsert(title, img, description, director, null, links.get(link_index), "kpwebtoon");
                    dbInsert.genreInsert(dbInsert.searchMovieID("kpwebtoon", title), dbInsert.searchGenreID(genre), "kpwebtoon_genre");

                    System.out.println("Done...");
                }catch (NullPointerException e){
                    System.out.println("NullPointerException 발생");
                }
            }

        } catch (InterruptedException | SQLException e) {
            throw new RuntimeException(e);
        } finally {
//            webDriver.close();
        }
    }

    private void login() {
        String url = "https://accounts.kakao.com/login/?continue=https%3A%2F%2Fkauth.kakao.com%2Foauth%2Fauthorize%3Fis_popup%3Dfalse%26ka%3Dsdk%252F2.1.0%2520os%252Fjavascript%2520sdk_type%252Fjavascript%2520lang%252Fko-KR%2520device%252FWin32%2520origin%252Fhttps%25253A%25252F%25252Fpage.kakao.com%26auth_tran_id%3DEZib0L4Tm9ITmJputGQUFFWvJN11bOq2EzAM7vWKg1FPjT~c2jlAAfrzi6st%26response_type%3Dcode%26state%3Dhttps%25253A%25252F%25252Fpage.kakao.com%25252Fmenu%25252F10010%25252Fscreen%25252F82%26redirect_uri%3Dhttps%253A%252F%252Fpage.kakao.com%252Frelay%252Flogin%26through_account%3Dtrue%26client_id%3D49bbb48c5fdb0199e5da1b89de359484&talk_login=hidden#login";
        try{
            webDriver.get(url);
            Thread.sleep(20000);
        }catch (InterruptedException e){
            throw new RuntimeException(e);
        }
    }

    private ArrayList<String> getLinks(){
        ArrayList<String> links = new ArrayList<>();

        element = webDriver.findElements(By.xpath("//*[@id=\"__next\"]/div/div[2]/div/div[2]/div[1]/div/div[4]/div/div/div/div"));

        WebElement link_element;

        for(int index = 1; index < element.size() + 1; index++){
            link_element = webDriver.findElement(By.xpath("//*[@id=\"__next\"]/div/div[2]/div/div[2]/div[1]/div/div[4]/div/div/div/div[" + index + "]/div/a"));

            links.add(link_element.getAttribute("href"));

            System.out.println("link_index : " + index);
        }

        return links;
    }

    private String getTitle(){
        String title = null;

        element = webDriver.findElements(By.xpath("//*[@id=\"__next\"]/div/div[2]/div[1]/div[1]/div[1]/div/div[2]/a/div/span[1]"));

        for(WebElement webElement : element){
            title = webElement.getText();
        }

        return title;
    }

    private String getImg(){
        String img = null;

        element = webDriver.findElements(By.xpath("//*[@id=\"__next\"]/div/div[2]/div[1]/div[1]/div[1]/div/div[1]/div[2]/div/div/div[2]/img"));

        for(WebElement webElement : element){
            img = webElement.getAttribute("src");
        }

        return img;
    }

    private String getGenre(){
        String genre = null;

        element = webDriver.findElements(By.xpath("//*[@id=\"__next\"]/div/div[2]/div[1]/div[1]/div[1]/div/div[2]/a/div/div[1]/div[1]/div/span[2]"));

        for(WebElement webElement : element){
            genre = webElement.getText();
        }

        return genre;
    }

    private String getDirector(){
        String director = null;

        element = webDriver.findElements(By.xpath("//*[@id=\"__next\"]/div/div[2]/div[1]/div[1]/div[1]/div/div[2]/a/div/span[2]"));

        for(WebElement webElement : element){
            director = webElement.getText();
        }

        return director;
    }

    private String getDescription() throws InterruptedException {
        String description = null;

        WebElement button = webDriver.findElement(By.xpath("//*[@id=\"__next\"]/div/div[2]/div[1]/div[2]/div[1]/div/div/div[2]/a/div"));
        button.click();

        Thread.sleep(1500);

        element = webDriver.findElements(By.xpath("//*[@id=\"__next\"]/div/div[2]/div[1]/div[2]/div[2]/div/div/div[1]/div/div[2]/div/div/span"));

        for (WebElement webElement : element) {
            description = webElement.getText();
        }

        return description;
    }
}
