import crawler.Content;
import crawler.ContentGenre;
import crawler.base.CrawlerBase;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

public class NaverCrawler extends CrawlerBase {

    /**
     * 크롤러 기본 옵션 세팅
     *
     * @param URL
     */
    public NaverCrawler(String URL) {
        super(URL);
    }

    private String link_ClassName = "Poster__link--sopnC";
    private WebDriver webDriver = super.getWebDriver();
    private List<WebElement > descriptionElement;
    private List<WebElement> element;
    private List<WebElement> titleElement;

    public Object[] scrap(){
        Object[] result = new Object[2];
        ArrayList<Content> cResult = new ArrayList<>();
        ArrayList<ContentGenre> gResult = new ArrayList<>();

        try{
         webDriver.get(super.getURL());
         Thread.sleep(20000);

         element = webDriver.findElements(By.className(link_ClassName));

         List<String> links = new ArrayList<>();

         for(int index = 0; index < element.size(); index++){
             links.add(element.get(index).getAttribute("href"));
         }

         for(int index = 0; index < element.size(); index++){
             System.out.println(links.get(index));
         }

         for(int index = 0; index < links.size(); index++){
             Object[] iResult = webtoonIn(links.get(index));
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

    public Object[] webtoonIn(String link){
        Object[] result = new Object[2];
        Content content = new Content();
        ContentGenre contentGenre = new ContentGenre();

        webDriver.get(link);
        element = webDriver.findElements(By.className("Poster__image--d9XTI"));
        titleElement = webDriver.findElements(By.className("EpisodeListInfo__title--mYLjC"));
        descriptionElement = webDriver.findElements(By.className("EpisodeListInfo__summary--Jd1WG"));

        String description = "";
        String title = String.valueOf(titleElement.get(0));
        String img = element.get(0).getAttribute("href");

        for(int index = 0; index < descriptionElement.size(); index++){
            description += descriptionElement.get(index).getText();
        }

        content.setTitle(title);
        content.setImg(img);
        content.setDescription(description);

        result[0] = content;
        result[1] = contentGenre;

        return result;
    }
}
