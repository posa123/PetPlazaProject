package petplaza.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import petplaza.model.dto.BusinessDto;
import petplaza.service.BusinessService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/business")
public class BusinessController {
    @Autowired
    private BusinessService businessService;

    //1. 금년도 총 매출액 출력
    @GetMapping("/totalSales")
    public List<Map<Object,Object>> getTotalSales( @RequestParam int year){
        //System.out.println("총매출출력컨트롤러진입> "+year);
        return businessService.getTotalSales(year);
    }
    //2. 상품 누적 판매량
    @GetMapping("/salesVolume")
    public List<Map<Object,Object>> getSalesVolume(){
        //System.out.println("누적판매량컨트롤러진입 ");
        return businessService.getSalesVolume(); 
    }

    //3. 인기 상품 top5
    @GetMapping("/topSales")
    public List <Map<Object,Object>> topSalesVolume(){
        //System.out.println("인기상품 컨트롤러 진입");
        return businessService.topSalesVolume();
    }
    //4. 매출관리 상품 정보
    @GetMapping("/saleInfo")
    public BusinessDto saleInfo(@RequestParam int pno){
        //System.out.println("상품정보출력 컨트롤러 진입");
        return businessService.saleInfo(pno);
    }
}//c
