package crawler.Doing;

import db.DBInsert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

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
        try{
            webDriver.get(URL);
            Thread.sleep(200);

            System.out.println(getLinks());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
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
}
