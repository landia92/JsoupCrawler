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
    public static void main(String[] args) {
        String resultPath =  "/Users/hyoji/OneDrive/바탕 화면/KDT/crawler";
        String fileName = "뉴스";
        //"GeekNews_crawling";
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyMMdd_HHmmss");
        String formattedDatetime = now.format(formatter);
        String extension = "md";
        String filePath = String.format("%s/%s_%s.%s", resultPath, fileName, formattedDatetime, extension);
        //System.out.println(filePath);
        PrintWriter out = null;
        try {
            FileWriter fw = new FileWriter(filePath, true);
            out = new PrintWriter(fw, true);    // out인 이유 : out.print로 사용할 수 있음
        } catch (IOException e) {
            System.err.println("크롤링 중 에러 발생");
            e.printStackTrace();
        }
        String URL = "https://www.ciokorea.com/news";
        //"https://news.hada.io/";       // 해당 사이트만 사용하므로 상수처럼 사용
        int articleCnt = 0;     //아직 크롤링하지 않았으므로 0 초기화
        for(int i = 1; i < 4; i++){
            String params = "?page=" + i;
            Connection conn = Jsoup.connect(URL+params);
            try{
                Document doc = conn.get();
                Elements elems= doc.select(".col .card-title"); //set 자료형으로 관리해도 됨
                //System.out.println(elems);
                for (Element elem : elems){
                    if(out != null) {   //PrintWriter out를 이용해 파일에 작성가능
                        out.println("## " + elem.text());
                        out.println("- link: https://www.ciokorea.com" + elem.select("a[href]").attr("href"));
                    }
                }
            } catch (IOException e) {
                System.err.println("페이지 요청 중 에러 발생");
                e.printStackTrace();
            }
            // user-agent 사용자 브라우저의 정보
            // 원래는 브라우저가 curl로 여러 정보를 가져오고 파싱해서 화면에 표시해준다
        }   // end of for statement
    }   // end of main method
}
