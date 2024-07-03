import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.xml.*;
import org.jsoup.Jsoup;
import org.jsoup.Connection;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
public class CIOCrawler {
    static String resultPath = "./articles/";
    static String fileName = "CIO Korea";

    static int fileCount = 1;
    static int pageSize = 1;

    public static void crawlArticles(String articleUrl){
        String formattedDatetime = String.valueOf(fileCount++);
        String extension = "md";
        String filePath = String.format("%s/%s_%s.%s", resultPath, fileName, formattedDatetime, extension);

        PrintWriter out = null;
        try {
            FileWriter fw = new FileWriter(filePath, true);
            out = new PrintWriter(fw, true);    // out인 이유 : out.print로 사용할 수 있음
        } catch (IOException e) {
            System.err.println("크롤링 중 에러 발생");
            e.printStackTrace();
        }

        Connection conn = Jsoup.connect(articleUrl);
        try {
            Document doc = conn.get();
            Elements elem = doc.select(".row>.col-12"); //set 자료형으로 관리해도 됨
            if (out != null) {   //PrintWriter out를 이용해 파일에 작성가능
                out.println(elem.select(".pb-5>p.font-color-primary-2>a>small.font-color-primary-2").first().text());   // 카테고리 1번째
                out.println(elem.select("#node_title").text()); //기사 제목
                out.println("https://www.ciokorea.com" + elem.select(".image>img").attr("src"));  //기사 내부 이미지
                out.println(elem.select(".node-body").text());  //기사 내용
            }
        } catch (IOException e) {
            System.err.println("페이지 요청 중 에러 발생");
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        String URL = "https://www.ciokorea.com/rss/feed/index.php"; // CioKorea의 RSS 링크

        // 시간을 비교하기 위한 Formatter
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime today = LocalDateTime.parse(LocalDateTime.now().format(formatter), formatter);
        LocalDateTime yesterday = today.minusDays(1);

        int a = 1;
        Connection conn = Jsoup.connect(URL);
        try {
            Document doc = conn.get();
            Elements elems = doc.select("item"); //set 자료형으로 관리해도 됨
            //System.out.println(elems);
            for (Element elem : elems) {
                //System.out.println(elem.select("pubDate").text());
                String link = elem.select("link").text();   // 기사의 링크
                String pubDate = elem.select("pubDate").text(); // 기사 발행 시간
                LocalDateTime articleTime = LocalDateTime.parse(pubDate, formatter);
                // 시간 비교
                if(articleTime.isBefore(today) && articleTime.isAfter(yesterday)) {
                    // 크롤링 가능한 기간
                    System.out.println("크롤링 가능합니다 : " + a++ + "번째");
                    crawlArticles(link);
                }
                else {
                    System.out.println("날짜가 지났습니다.");
                    break;
                }
            }
        } catch (IOException e) {
            System.err.println("페이지 요청 중 에러 발생");
            e.printStackTrace();
        }
    }   // end of main method
}
