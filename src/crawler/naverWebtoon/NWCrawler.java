package crawler.naverWebtoon;

import crawler.Content;
import crawler.ContentGenre;
import crawler.base.Crawler;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

public class NWCrawler implements Crawler {

    private WebDriver webDriver;
    private String URL;
    private List<WebElement> element;

    private ArrayList<String> links = new ArrayList<>();
    private ArrayList<ArrayList<String>> titles = new ArrayList<>();
    private ArrayList<ArrayList<String>> imgs = new ArrayList<>();
    private ArrayList<ArrayList<String>> descriptions = new ArrayList<>();
    private ArrayList<ArrayList<String>> genres = new ArrayList<>();
    private ArrayList<ArrayList<String>> metaInfo = new ArrayList<>();

    public NWCrawler(WebDriver webDriver, String URL){
        this.webDriver = webDriver;
        this.URL = URL;
    }

    @Override
    public Object[] getResult() {

        try{
            webDriver.get(URL);
            Thread.sleep(300);

            links = getLinks();

//            System.out.println(links);

            for(int link_index = 0; link_index < links.size(); link_index++){
                webDriver.get(links.get(link_index));
                Thread.sleep(800);

                titles.add(getTitles());
                imgs.add(getImgs());
                descriptions.add(getDescriptions());
                metaInfo.add(getMetaInfo());
                genres.add(getGenres());

//                System.out.println("---------------------------------");
//                System.out.println("제목 : " + titles.get(link_index));
//                System.out.println("설명 : " + descriptions.get(link_index));
//                if(imgs.get(link_index).size() != 0){
//                    System.out.println("이미지 : " + imgs.get(link_index).get(0));
//                }
//                else{
//                    System.out.println("이미지 : " + imgs.get(link_index));
//                }
//                System.out.println("메타 정보 : " + metaInfo.get(link_index));
//                System.out.println("장르 : " + genres.get(link_index));
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }finally {
//            webDriver.close();
        }

//        System.out.println(titles.size());
//        System.out.println(imgs.size());
//        System.out.println(descriptions.size());
//        System.out.println(metaInfo.size());
//        System.out.println(genres.size());

        Object[] results = new Object[2];

        results[0] = setContents(titles, descriptions, imgs, metaInfo);
        results[1] = setGenres(genres);

        return results;
    }

    private ArrayList<String> getLinks(){
        ArrayList<String> links = new ArrayList<>();

        element = webDriver.findElements(By.className("Poster__link--sopnC"));
        for(int index = 0; index < element.size(); index++){
            links.add(element.get(index).getAttribute("href"));
        }

        return links;
    }

    private ArrayList<String> getTitles(){
        ArrayList<String> titles = new ArrayList<>();

        element = webDriver.findElements(By.className("EpisodeListInfo__title--mYLjC"));
        for(int index = 0; index < element.size(); index++){
            titles.add(element.get(index).getText());
        }

        return titles;
    }

    private ArrayList<String> getImgs(){
        ArrayList<String> imgs = new ArrayList<>();

        element = webDriver.findElements(By.className("Poster__image--d9XTI"));
        for(int index = 0; index < element.size(); index++){
            imgs.add(element.get(index).getAttribute("src"));
        }

        return imgs;
    }

    private ArrayList<String> getDescriptions(){
        ArrayList<String> descriptions = new ArrayList<>();

        element = webDriver.findElements(By.className("EpisodeListInfo__summary--Jd1WG"));
        for(int index = 0; index < element.size(); index++){
            descriptions.add(element.get(index).getText());
        }

        return descriptions;
    }

    private ArrayList<String> getMetaInfo(){
        ArrayList<String> metaInfo = new ArrayList<>();

        element = webDriver.findElements(By.className("ContentMetaInfo__link--xTtO6"));

        for(int index = 0; index < element.size(); index++){
            metaInfo.add(element.get(index).getText());
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

    private ArrayList<Content> setContents(ArrayList<ArrayList<String>> titles, ArrayList<ArrayList<String>> descriptions, ArrayList<ArrayList<String>> imgs, ArrayList<ArrayList<String>> metaInfos){
        ArrayList<Content> contents = new ArrayList<>();

        for(int index = 0; index < titles.size(); index++){
            Content content = new Content();

            for(int titles_index = 0; titles_index < titles.get(index).size(); titles_index++){
                content.setTitle(titles.get(index).get(titles_index));
            }
            for(int descriptions_index = 0; descriptions_index < descriptions.get(index).size(); descriptions_index++){
                content.setDescription(descriptions.get(index).get(descriptions_index));
            }
            for(int imgs_index = 0; imgs_index < imgs.get(index).size(); imgs_index++){
                content.setImg(imgs.get(index).get(0));
            }
            for(int metaInfo_index = 0; metaInfo_index < metaInfos.get(index).size(); metaInfo_index++){
                content.setDirector(metaInfos.get(index).get(metaInfo_index));
            } // 네이버웹툰의 경우 감독, 출연을 작가로 통일해서 director에 저장

            contents.add(content);
        }

        return contents;
    }

    private ArrayList<ContentGenre> setGenres(ArrayList<ArrayList<String>> genre){
        ArrayList<ContentGenre> contentGenres = new ArrayList<>();

        for(int index = 0; index < genre.size(); index++){
            ContentGenre contentGenre = new ContentGenre();

            contentGenre.setGenre(genre.get(index));

            contentGenres.add(contentGenre);
        }

        return contentGenres;
    }
}
