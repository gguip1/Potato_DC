package crawler;

import crawler.base.CrawlerBase;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

/*
CoupangPlay 크롤링할 사이트 형식 : https://www.coupangplay.com/content/a37abef6-2043-40e4-ab89-d11ae2f04b6e
 */
public class CoupangCrawler extends CrawlerBase{

    /**
     * 크롤링할 때 ClassName으로 Element(요소)들을 찾을때 사용하는 ClassName
     */
    private String top20_link_ClassName = "OpenTop20Carousel_clipsItemWrapper__PS3gO"; // top 20의 세부 정보를 확인하기 위한 링크 ClassName -- 이 부분은 크롤링하는 부분마다 다름
    private String top20_img_ClassName = "OpenTop20Carousel_clipsThumbnail__XVX_F"; // top 20의 img ClassName -- 이 부분은 크롤링하는 부분마다 다름
    private String tagDetails_ClassName = "OpenTitleHeroSection_heroTagDetails__O5T5N"; // 세부 정보를 위한 페이지에서 장르, 작가, 감독, 출연을 확인할 때 사용하는 ClassName
    private String tagName_ClassName = "OpenTitleHeroSection_heroTagName__dZeHv"; // 세부 정보를 위한 페이지에서 장르, 작가, 감독, 출연을 확인할 때 사용하는 ClassName
    private String description_ClassName = "__react-ellipsis-js-content"; // 세부 정보를 위한 페이지에서 description을 확인할 때 사용하는 ClassName

    private WebDriver webDriver = super.getWebDriver(); // WebDriver 설정
    private List<WebElement> element; //기본 Element
    private List<WebElement> tagElement; // tag Element
    private List<WebElement> imgElement; // img Element
    private List<WebElement> descriptionElement; // description Element

    /**
     * URL 설정
     * @param URL
     */
    public CoupangCrawler(String URL) {
        super(URL);
    }

    /**
     * Top 20 크롤링
     * @return
     */
    public Object[] scrapTop20(){
        Object[] result = new Object[2];
        ArrayList<Content> cResult = new ArrayList<>();
        ArrayList<ContentGenre> gResult = new ArrayList<>();

        try{
            webDriver.get(super.getURL());
            Thread.sleep(2000);
            element = webDriver.findElements(By.className(top20_link_ClassName));
            imgElement = webDriver.findElements(By.className(top20_img_ClassName));
            List<String> links = new ArrayList<>();
            List<String> imgs = new ArrayList<>();
            List<String> titles = new ArrayList<>();

            for(int index = 0; index < element.size(); index++){
                links.add(element.get(index).getAttribute("href"));
            }

            /**
             * Img
             */
            for(int index = 0; index < imgElement.size(); index++){
                if(index % 2 == 1){
                    imgs.add(imgElement.get(index).getAttribute("src"));
                    titles.add(imgElement.get(index).getAttribute("alt"));
                }
            }

            for(int index = 0; index < imgs.size(); index++){
                System.out.println(index + " :" + imgs.get(index));
            }

            /**
             * 장르, 출연, 감독, 작가
             */
            for(int index = 0; index < links.size(); index++){
                String src = imgs.get(index);
                String title = titles.get(index);
                Object[] iResult = coupangIn(links.get(index), src, title);
                cResult.add((Content) iResult[0]);
                gResult.add((ContentGenre) iResult[1]);
            }

            result[0] = cResult;
            result[1] = gResult;

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        finally{
            webDriver.close();
        }

        return result;
    }

    /**
     * 크롤링할 Contents 세부 정보 페이지에 관한 Method
     */
    public Object[] coupangIn(String link, String src, String title){
        Object[] result = new Object[2];
        Content content = new Content();
        ContentGenre contentGenre = new ContentGenre();

        webDriver.get(link);
        element = webDriver.findElements(By.className(tagDetails_ClassName));

        descriptionElement = webDriver.findElements(By.className(description_ClassName));
        String description = "";
        
        for(int index = 0; index < descriptionElement.size(); index++){
            description += descriptionElement.get(index).getText();
        }

        content.setTitle(title);
        content.setImg(src);
        content.setDescription(description);
        
        for(int index = 0; index < element.size(); index++){

            tagElement = webDriver.findElements(By.className(tagName_ClassName));
            String tagName = tagElement.get(index).getText();

            switch (tagName){
                case "장르": contentGenre.setGenre(element.get(index).getText()); break;
                case "출연": content.setActor(element.get(index).getText()); break;
                case "감독": content.setDirector(element.get(index).getText()); break;
                case "작가": content.setWriter(element.get(index).getText()); break;
                default: System.out.println("생각하지 못한 예외가 장르, 출연, 감독, 작가 크롤링하는 부분에서 발생");
            }
        }

        result[0] = content;
        result[1] = contentGenre;

        return result;
    }
}
