package crawler.Done;

import db.DBInsert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CPCrawler {
    private WebDriver webDriver;
    private String URL;
    private List<WebElement> element;

    private DBInsert dbInsert = new DBInsert();

    public CPCrawler(WebDriver webDriver, String URL){
        this.webDriver = webDriver;
        this.URL = URL;
    }

    public void doALL(){
//        login();

        try{
            webDriver.get(URL);
            Thread.sleep(200);

            for(int i = 0; i < 5; i++){
                new Actions(webDriver).sendKeys(Keys.END).perform();

                Thread.sleep(200);

                System.out.println("End Count : " + i);
            }

            ArrayList<String> links = getLinks();
            ArrayList<String> titles = getTitles();
            ArrayList<String> imgs = getImgs();

            int links_size = links.size();

            System.out.println("Links : " + links_size);

            ArrayList<String> null_genre = new ArrayList<>();

            for(int link_index = 0; link_index < links_size; link_index++){
                System.out.println("Starting..." + link_index);

                webDriver.get(links.get(link_index));
                Thread.sleep(500);

                String description = "";
                for(String description_ : getDescriptions()){
                    description += description_;
                }

                HashMap<String, String> metaInfo = getMetaInfo();
                String director = metaInfo.get("director");
                String actor = metaInfo.get("actor");
                String genres = metaInfo.get("genre");

                String[] genre = genres.replace("[","").replace("]","").split(", ");

                try{
                    dbInsert.contentInsert(titles.get(link_index), imgs.get(link_index), description, director, actor, links.get(link_index),"couplay");
                    for(int index = 0; index < genre.length; index++){
                        try{
                            dbInsert.genreInsert(dbInsert.searchMovieID("couplay", titles.get(link_index)), dbInsert.searchGenreID(genre[index]), "couplay_genre");
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        } catch(NullPointerException e){
                            null_genre.add(genre[index]);
                        }
                    }

                    System.out.println("Done...");
                } catch (NullPointerException e){
                    System.out.println("NullPointerException 발생");
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private void login(){
        String url = "https://login.coupang.com/login/playLogin.pang?platform=pc&target=OTT&rtnUrl=https%3A%2F%2Fwww.coupangplay.com%2Flogin%2Fcoupang%3Freturnpath%3D%252F%26sourcePage%3Dpage_home_pre_login";
        try{
            webDriver.get(url);
            Thread.sleep(20000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private ArrayList<String> getLinks(){
        ArrayList<String> links = new ArrayList<>();

        element = webDriver.findElements(By.className("OpenFeedCarousel_clipsItemWrapper__1FNyR"));
        for(int index = 0; index < element.size(); index++){
            links.add(element.get(index).getAttribute("href"));
        }

        return links;
    }

    private ArrayList<String> getTitles(){
        ArrayList<String> titles = new ArrayList<>();

        element = webDriver.findElements(By.className("OpenFeedCarousel_clipsThumbnail__Ixioy"));
        for(int index = 0; index < element.size(); index++){
            if(index % 2 == 1) {
                titles.add(element.get(index).getAttribute("alt"));
            }
        }

        return titles;
    }

    private ArrayList<String> getImgs(){
        ArrayList<String> imgs = new ArrayList<>();

        element = webDriver.findElements(By.className("OpenFeedCarousel_clipsThumbnail__Ixioy"));
        for(int index = 0; index < element.size(); index++){
            if(index % 2 == 1) {
                imgs.add(element.get(index).getAttribute("src"));
            }
        }

        return imgs;
    }

    private ArrayList<String> getDescriptions(){
        ArrayList<String> descriptions = new ArrayList<>();

        element = webDriver.findElements(By.className("__react-ellipsis-js-content"));
        for(int index = 0; index < element.size(); index++){
            descriptions.add(element.get(index).getText());
        }

        return descriptions;
    }

    private HashMap<String, String> getMetaInfo(){
        HashMap<String, String> metaInfo = new HashMap<>();

        List<WebElement> tagElement = webDriver.findElements(By.className("OpenTitleHeroSection_heroTagName__dZeHv"));
        element = webDriver.findElements(By.className("OpenTitleHeroSection_heroTagDetails__O5T5N"));

        ArrayList<String> tagList = new ArrayList<>();

        for(int index = 0; index < tagElement.size(); index++){
            tagList.add(tagElement.get(index).getText());
        }

        for(int index = 0; index < tagElement.size(); index++){
            switch (tagList.get(index)){
                case "장르": metaInfo.put("genre", element.get(index).getText()); break;
                case "출연": metaInfo.put("actor", element.get(index).getText()); break;
                case "감독": metaInfo.put("director", element.get(index).getText()); break;
                case "작가": metaInfo.put("writer", element.get(index).getText()); break;
                default: System.out.println("생각하지 못한 예외가 장르, 출연, 감독, 작가 크롤링하는 부분에서 발생");
            }
        }

        return metaInfo;
    }

}
