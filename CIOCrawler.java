import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.jsoup.Jsoup;
import org.jsoup.Connection;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
public class CIOCrawler {
    static String resultPath = "./";
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
            Elements elems = doc.select(".row>.col-12"); //set 자료형으로 관리해도 됨
            for (Element elem : elems) {
                if (out != null) {   //PrintWriter out를 이용해 파일에 작성가능
                    out.println(elem.text());
                }
            }
        } catch (IOException e) {
            System.err.println("페이지 요청 중 에러 발생");
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        String URL = "https://www.ciokorea.com/news";
        for (int i = 1; i < pageSize+1; i++) {
            String params = "?page=" + i;
            Connection conn = Jsoup.connect(URL + params);
            try {
                Document doc = conn.get();
                Elements elems = doc.select(".col .card-title"); //set 자료형으로 관리해도 됨
                //System.out.println(elems);
                for (Element elem : elems) {
                    crawlArticles("https://www.ciokorea.com" + elem.select("a[href]").attr("href"));
                }
            } catch (IOException e) {
                System.err.println("페이지 요청 중 에러 발생");
                e.printStackTrace();
            }
        }
    }   // end of main method
}
