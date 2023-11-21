package petplaza.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import petplaza.model.dto.CrawlingDto;
import petplaza.service.CrawlingService;

import java.util.List;

// 크롤링 컨트롤러
@RestController
@RequestMapping("/crawling")
public class CrawlingController {

    @Autowired
    private final CrawlingService crawlingService;

    public CrawlingController(CrawlingService crawlingService){
        this.crawlingService = crawlingService;
    }
    @GetMapping("/news")
    public List<CrawlingDto> news( ) throws Exception {
        List<CrawlingDto> crawlingDtoList = crawlingService.getCrawlingDtoList();
        return crawlingDtoList;
    }
    // 추후 날씨 추가 예정

}
