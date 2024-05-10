package crawler.coupang;

import crawler.Content;
import crawler.ContentGenre;
import crawler.base.Crawler;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CPCrawler implements Crawler {
    private WebDriver webDriver;
    private String URL;
    private List<WebElement> element;

    private ArrayList<String> links = new ArrayList<>();
    private ArrayList<String> titles = new ArrayList<>();
    private ArrayList<String> imgs = new ArrayList<>();
    private ArrayList<ArrayList<String>> descriptions = new ArrayList<>();
    private ArrayList<ArrayList<String>> genres = new ArrayList<>();
    private ArrayList<HashMap<String, String>> metaInfo = new ArrayList<>();

    public CPCrawler(WebDriver webDriver, String URL){
        this.webDriver = webDriver;
        this.URL = URL;
    }

    /**
     * 쿠팡 플레이의 장르, 감독, 출연은 metaInfo에 HashMap으로 저장
     * @return
     */
    @Override
    public Object[] getResult() {

        try{
            webDriver.get(URL);
            Thread.sleep(200);

            links = getLinks();
            titles = getTitles();
            imgs = getImgs();

            for(int link_index = 0; link_index < links.size(); link_index++){
                webDriver.get(links.get(link_index));
                Thread.sleep(300);

                descriptions.add(getDescriptions());
                metaInfo.add(getMetaInfo()) ;

            }

            System.out.println(links.size());
            System.out.println(titles.size());
            System.out.println(descriptions.size());
            System.out.println(metaInfo.size());

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        finally {
//            webDriver.close();
        }

        Object[] results = new Object[2];

        results[0] = setContents(titles, imgs, descriptions, metaInfo);
        results[1] = setGenres(genres);

        return results;
    }

    private Object setContents(ArrayList<String> titles, ArrayList<String> imgs, ArrayList<ArrayList<String>> descriptions, ArrayList<HashMap<String, String>> metaInfo) {
        ArrayList<Content> contents = new ArrayList<>();

        for(int index = 0; index < titles.size(); index++){
            Content content = new Content();

            content.setTitle(titles.get(index));
            for(int descriptions_index = 0; descriptions_index < descriptions.get(index).size(); descriptions_index++){
                content.setDescription(descriptions.get(index).get(descriptions_index));
            }
            content.setImg(imgs.get(index));
            for(int metaInfos_index = 0; metaInfos_index < metaInfo.get(index).size(); metaInfos_index++) {
                content.setActor(metaInfo.get(index).get("actor"));// 네이버웹툰의 경우 감독, 출연을 작가로 통일해서 director에 저장
                content.setDirector(metaInfo.get(index).get("director"));// 네이버웹툰의 경우 감독, 출연을 작가로 통일해서 director에 저장
            }

            contents.add(content);
        }

        return contents;
    }

    private Object setGenres(ArrayList<ArrayList<String>> genres) {
        ArrayList<ContentGenre> contentGenres = new ArrayList<>();

        for(int index = 0; index < genres.size(); index++){
            ContentGenre contentGenre = new ContentGenre();

            contentGenre.setGenre(genres.get(index));

            contentGenres.add(contentGenre);
        }

        return  contentGenres;
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

        element = webDriver.findElements(By.className("OpenTitleHeroSection_heroTagDetails__O5T5N"));
        List<WebElement> tagElement = webDriver.findElements(By.className("OpenTitleHeroSection_heroTagName__dZeHv"));

        ArrayList<String> tagList = new ArrayList<>();

        for(int index = 0; index < tagElement.size(); index++){
            tagList.add(tagElement.get(index).getText());
        }

        for(int index = 0; index < element.size(); index++){
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
