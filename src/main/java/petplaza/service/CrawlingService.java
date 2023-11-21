package petplaza.service;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import petplaza.model.dto.CrawlingDto;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


// 크롤링 서비스
@Service
public class CrawlingService {
    // 크롤링할 URL(네트워크 상에서 자원이 어디 있는지를 알려주기 위한 규약)
    private static String  News_URL = "https://www.pet-news.or.kr/news/articleList.html?sc_sub_section_code=S2N102&view_type=sm";
    // 네이버 날씨 URL 추후 추가 예정
    private static String  WeatherURL = "https://weather.naver.com/today";

    @PostConstruct
    public List<CrawlingDto> getCrawlingDtoList() throws IOException {

        System.out.println("News_URL = " + News_URL);

        // 1. 뉴스 데이터를 담을 리스트를 생성합니다.
        List<CrawlingDto> crawlingDtos = new ArrayList<>();
        // 2. Jsoup을 사용하여 지정된 URL에서 HTML 문서를 가져옴.
        Document document = Jsoup.connect(News_URL).get();
        System.out.println("document = " + document);
        System.out.println("crawlingDtos = " + crawlingDtos);

        // 3. HTML 문서에서 특정 선택자를 사용하여 원하는 데이터를 추출
        Elements contents = document.select("section ul.type2 li");
        System.out.println("contents = " + contents);
        
        // 4. 추출한 데이터를 객체로 변환하여 리스트에 추가.
        for(Element content : contents){
            CrawlingDto crawlingDto = CrawlingDto.builder()
                    .cimg(content.select("a img").attr("abs:src"))
                    .ctitle(content.select("h4 a").text())
                    .curl(content.select("a").attr("abs:href"))
                    .build();
            crawlingDtos.add(crawlingDto);
        }
        System.out.println("crawlingDtos11111= " + crawlingDtos);
        // 크롤링Dtos 반환 
        return crawlingDtos;
    }

}
