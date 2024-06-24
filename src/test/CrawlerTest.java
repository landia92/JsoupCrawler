package src.test;

import src.Crawling.Crawler;

public class CrawlerTest {
    public static void main(String[] args) {
        Crawler crawler = new Crawler();
        crawler.crawlURL("https://www.ciokorea.com/news", ".col .card-title", 1);
    }

}
