package crawler.coupang;

import crawler.Content;
import crawler.ContentGenre;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;


public class CouplayCrawler {
    private WebDriver webDriver;
    private String URL;
    private List<WebElement> element;

    public CouplayCrawler(WebDriver webDriver, String URL){
        this.webDriver = webDriver;
        this.URL = URL;
    }

    public Object[] scrapCouplay(String imgClassName, String linkClassName, String descriptionClassName, String tagNameClassName, String tagDetailsClassName){
        ArrayList<String> title;
        ArrayList<String> img;
        ArrayList<String> genre = new ArrayList<>();
        ArrayList<String> description = new ArrayList<>();
        ArrayList<String> director = new ArrayList<>();
        ArrayList<String> actor = new ArrayList<>();
        ArrayList<String> writer = new ArrayList<>();

        try {
            webDriver.get(URL);
            Thread.sleep(200);
            /**
             * 기본 쿠팡 링크에서 img url, title 스크랩
             */
            Object[] imgsandtitles = imgsandtitles(imgClassName);

            title = (ArrayList<String>) imgsandtitles[0];
            img = (ArrayList<String>) imgsandtitles[1];

            System.out.println(title);
            System.out.println(img);

            /**
             * 기본 쿠팡 링크에서 link 스크랩
             */
            ArrayList<String> links = links(linkClassName);
            System.out.println(links);

            /**
             * 스크랩한 link 안으로 들어가서 세부내용 스크랩
             */
            for(int index = 0; index < links.size(); index++){
                webDriver.get(links.get(index));
                element = webDriver.findElements(By.className(descriptionClassName));
                String description_ = "";

                for(int i = 0; i < element.size(); i++){
                    description_ += element.get(i).getText();
                }
                description.add("[" + description_ + "]");

                element = webDriver.findElements(By.className(tagNameClassName));
                ArrayList<String> tagName_ = new ArrayList<>();

                for(int i = 0; i < element.size(); i++){
                    List<WebElement> tagElement;
                    tagElement = webDriver.findElements(By.className(tagDetailsClassName));

                    String tagName = element.get(i).getText();

                    tagName_.add(tagName);

                    switch (tagName){
                        case "장르": genre.add("[" + tagElement.get(i).getText() + "]"); break;
                        case "출연": actor.add("[" + tagElement.get(i).getText() + "]"); break;
                        case "감독": director.add("[" + tagElement.get(i).getText() + "]"); break;
                        case "작가": writer.add("[" + tagElement.get(i).getText() + "]"); break;
                        default: System.out.println("생각하지 못한 예외가 장르, 출연, 감독, 작가 크롤링하는 부분에서 발생" + " tagName : " + tagName + " index : " + index + " i : " + i);
                    }
                }

                if(!tagName_.contains("장르")){
                    genre.add("NULL");
                }
                if(!tagName_.contains("출연")){
                    actor.add("NULL");
                }
                if(!tagName_.contains("감독")){
                    director.add("NULL");
                }
                if(!tagName_.contains("작가")){
                    writer.add("NULL");
                }

                tagName_.clear();
//                System.out.println("제목 리스트 : " + title + "index : " + title.size());
//                System.out.println("이미지 리스트 : " + img + "img : " + img.size());
//                System.out.println("장르 리스트 : " + genre + "genre : " + genre.size());
//                System.out.println("출연 리스트 : " + actor + "actor : " + actor.size());
//                System.out.println("감독 리스트 : " + director + "director : " + director.size());
//                System.out.println("작가 리스트 : " + writer + "writer : " + writer.size());
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        finally {
            webDriver.close();
        }

        Object[] result = new Object[2];
        result[0] = couplayContents(title, description, img, director, actor);
        result[1] = couplayGenre(genre);

        return result;
    }
    /**
     * 기본 쿠팡 링크에서 img url, title 스크랩
     */
    private Object[] imgsandtitles(String imgClassName){
        ArrayList<String> title = new ArrayList<>();
        ArrayList<String> img = new ArrayList<>();

//        element = webDriver.findElements(By.className("OpenTop20Carousel_clipsThumbnail__XVX_F"));
        element = webDriver.findElements(By.className(imgClassName));
        for(int index = 0; index < element.size(); index++){
            if(index % 2 == 1){
                title.add(element.get(index).getAttribute("alt"));
                img.add(element.get(index).getAttribute("src"));
            }
        }

        Object[] results = new Object[2];
        results[0] = title;
        results[1] = img;

        return results;
    }

    /**
     * 기본 쿠팡 링크에서 link 스크랩
     */
    private ArrayList<String> links(String linkClassName){
        ArrayList<String> links = new ArrayList<>();

        element = webDriver.findElements(By.className(linkClassName));
        System.out.println(element);
//        element = webDriver.findElements(By.className("OpenTop20Carousel_clipsItemWrapper__PS3gO"));
        for(int index = 0; index < element.size(); index++){
            links.add(element.get(index).getAttribute("href"));
        }

        return links;
    }

    private ArrayList<Content> couplayContents(ArrayList<String> title, ArrayList<String> description, ArrayList<String> img, ArrayList<String> director, ArrayList<String> actor){
        ArrayList<Content> output = new ArrayList<>();

        for(int index = 0; index < title.size(); index++){
            Content content = new Content();

//            content.setTitle(title.get(index));
//            content.setDescription(description.get(index));
//            content.setImg(img.get(index));
//            content.setDirector(director.get(index));
//            content.setActor(actor.get(index));

            output.add(content);
        }

        return output;
    }

    public ArrayList<ContentGenre> couplayGenre(ArrayList<String> genre){
        ArrayList<ContentGenre> output = new ArrayList<>();

        for(int index = 0; index < genre.size(); index++){
            ContentGenre contentGenre = new ContentGenre();

//            contentGenre.setGenre(genre.get(index));

            output.add(contentGenre);
        }

        return output;
    }
}
