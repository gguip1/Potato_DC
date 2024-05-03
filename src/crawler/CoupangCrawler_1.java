package crawler;

import crawler.base.CrawlerBase;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/*
CoupangPlay 크롤링할 사이트 형식 : https://www.coupangplay.com/content/a37abef6-2043-40e4-ab89-d11ae2f04b6e
 */
public class CoupangCrawler_1 extends CrawlerBase{

    public CoupangCrawler_1(String URL) {
        super(URL);
    }
    private WebDriver webDriver = super.getWebDriver();
    private List<WebElement> element;
    private List<WebElement> element_list;

    /**
     * 크롤링 데이터 ArrayList<String>으로 리턴
     * @return
     */
    public ArrayList<String> activate(){
        ArrayList<String> results = new ArrayList<>();
        try{
            webDriver.get(super.getURL());
            Thread.sleep(2000);

            /**
             * html의 xpath를 이용해서 크롤링
             * 플랫폼 별 xpath 위치를 설정해서 자동화할 필요가 있음
             * 수정 필요
             */
            element = webDriver.findElements(By.className("OpenTop20Carousel_clipsItemWrapper__PS3gO"));

            List<String> links = new ArrayList<>();

            List<String> genre = new ArrayList<>();

            List<String> title = new ArrayList<>();

            List<String> description = new ArrayList<>();

            List<String> director = new ArrayList<>();

            List<String> actor = new ArrayList<>();

            /**
             * DB 고려 하지 않은 부분
             */
            List<String> writer = new ArrayList<>();

            for(int i = 0; i < element.size(); i++){
                links.add(element.get(i).getAttribute("href"));
                System.out.println(links);
            }
            System.out.println(links);

            /**
             * for문을 3번이나 쓴다고 쓰레기같은걸
             */
            for(int i = 0; i < links.size(); i++){
                webDriver.get(links.get(i));
                element = webDriver.findElements(By.className("OpenTitleHeroSection_heroTagDetails__O5T5N"));
                for(int j = 0; j < element.size(); j++){
                    element_list = webDriver.findElements(By.className("OpenTitleHeroSection_heroTagName__dZeHv"));
//                    for(int o = 0; o < element_list.size(); o++){
                        if(element_list.get(j).getText().equals("장르")){
                            genre.add(i + ": [" + element.get(j).getText() + "]");
                        }
                        else if (element_list.get(j).getText().equals("출연")) {
                            actor.add(i + ": [" + element.get(j).getText() + "]");
                        }
                        else if(element_list.get(j).getText().equals("감독")){
                            director.add(i + ": [" + element.get(j).getText() + "]");
                        }
                        else if(element_list.get(j).getText().equals("작가")){
                            writer.add(i + ": [" + element.get(j).getText() + "]");
                        }
                        else{
                            System.out.println("실패 : " + element_list.get(j).getText());
                            System.out.println("실패 : " + element.get(i).getText());
                        }
//                    }
                }
            }

            System.out.println("장르 : " + genre);
            System.out.println("출연 : " + actor);
            System.out.println("감독 : " + director);

            /**
             * 테스트용 출력부
             */
//            System.out.println(description);
//            System.out.println(genre);
//            System.out.println(actor);
//            System.out.println(director);
//            System.out.println(writer);

            /**
             * 수정 필요
             */

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        finally{
            webDriver.close();
        }
        return results;
    }
}
