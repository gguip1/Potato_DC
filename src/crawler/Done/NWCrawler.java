package crawler.Done;

import db.DBInsert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class NWCrawler {

    private WebDriver webDriver;
    private String URL;
    private List<WebElement> element;

    private DBInsert dbInsert = new DBInsert();

    public NWCrawler(WebDriver webDriver, String URL){
        this.webDriver = webDriver;
        this.URL = URL;
    }

    public void doALLNW(){
        login();

        try{
            webDriver.get(URL);
            Thread.sleep(200);

            new Actions(webDriver).sendKeys(Keys.END).perform();
            Thread.sleep(200);

            ArrayList<String> links = getLinks();

            int links_size = links.size();

            System.out.println("Links : " + links_size);

            ArrayList<String> null_genre = new ArrayList<>();

            for(int link_index = 0; link_index < links_size; link_index++){
                System.out.println("Starting..." + link_index);

                webDriver.get(links.get(link_index));
                Thread.sleep(200);

                String title = getTitles();
                String img = getImg();
                String description = "";
                for(String description_ : getDescriptions()){
                    description += description_;
                }
                ArrayList<String> genre = getGenres();
                String director = getMetaInfo();

                try{
                    dbInsert.contentInsert(title, img, description, director, null, links.get(link_index),"naverwebtoon");
                    for(int index = 0; index < genre.size(); index++){
                        try{
                            dbInsert.genreInsert(dbInsert.searchMovieID("naverwebtoon", title), dbInsert.searchGenreID(genre.get(index)), "naverwebtoon_genre");
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        } catch(NullPointerException e){
                            null_genre.add(genre.get(index));
                        }
                    }

                    System.out.println("Done...");
                } catch (NullPointerException e){
                    System.out.println("NullPointerException 발생");
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }

            for(int index = 0; index < null_genre.size(); index++){
                System.out.println(null_genre.get(index));
            }

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private void login(){
        String login_url = "https://nid.naver.com/nidlogin.login?url=https%3A%2F%2Fcomic.naver.com%2Fwebtoon";
        try{
            webDriver.get(login_url);
            Thread.sleep(20000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private ArrayList<String> getLinks(){
        ArrayList<String> links = new ArrayList<>();

        element = webDriver.findElements(By.className("Poster__link--sopnC"));
        for(int index = 0; index < element.size(); index++){
            links.add(element.get(index).getAttribute("href"));
        }

        return links;
    }

    private String getTitles(){
        String title = null;

        element = webDriver.findElements(By.className("EpisodeListInfo__title--mYLjC"));
        for(int index = 0; index < element.size(); index++){
            title = element.get(index).getText();
        }

        return title;
    }

    private String getImg(){
        String img = null;

        element = webDriver.findElements(By.xpath("//*[@id=\"content\"]/div[1]/button/div/img"));
        for(int index = 0; index < element.size(); index++){
            img = element.get(index).getAttribute("src");
        }

        return img;
    }

    private ArrayList<String> getDescriptions(){
        ArrayList<String> descriptions = new ArrayList<>();

        element = webDriver.findElements(By.className("EpisodeListInfo__summary--Jd1WG"));
        for(int index = 0; index < element.size(); index++){
            descriptions.add(element.get(index).getText());
        }

        return descriptions;
    }

    private String getMetaInfo(){
        String metaInfo = null;

        element = webDriver.findElements(By.className("ContentMetaInfo__link--xTtO6"));

        for(int index = 0; index < element.size(); index++){
            metaInfo = (element.get(index).getText());
        }

        return metaInfo;
    }

    private ArrayList<String> getGenres(){
        ArrayList<String> genre = new ArrayList<>();

        element = webDriver.findElements(By.className("TagGroup__tag--xu0OH"));
        for(int index = 0; index < element.size(); index++){
            genre.add(element.get(index).getText().replace("#", ""));
        }

        return genre;
    }
}
