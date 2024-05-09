package crawler.base;


public interface Crawler {
    String getDescription(String className, String link);
    String getTitle(String className, String link);
    String getImg(String className, String link);
    String getDirector(String className, String director);
    String getActor(String className, String actor);

}
