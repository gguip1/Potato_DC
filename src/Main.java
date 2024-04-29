public class Main {
    public static void main(String[] args) {

        String URL = "https://www.coupangplay.com/content/1c6b40a9-2c02-4d34-af8e-ec3af17118a3";
        CoupangCrawler coupangCrawler = new CoupangCrawler(URL);
        coupangCrawler.activate();

    }
}