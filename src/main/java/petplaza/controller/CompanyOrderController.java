package petplaza.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import petplaza.model.dto.CompanyOrderDto;
import petplaza.model.dto.CompanyOrderPageDto;
import petplaza.service.CompanyOrderService;
import java.util.List;

@RestController
@RequestMapping("/companyOrder")
public class CompanyOrderController { // 발주 컨트롤러

    // 해당 클래스에 서비스 사용위해 전역변수 선언
    @Autowired
    private CompanyOrderService companyOrderService;

    // 1. 발주 등록 [C]
    @PostMapping("")
    public boolean postCompany(@RequestBody CompanyOrderDto companyOrderDto){
        System.out.println("postCompanyController");

        boolean result = companyOrderService.postCompany(companyOrderDto);

        return result;
    }

    // 2. 발주 호출 [R]
    @GetMapping("/get")
    public CompanyOrderPageDto getCompany(@RequestParam int page ,
                                          @RequestParam String key ,
                                          @RequestParam String keyword ,
                                          @RequestParam int view){
        System.out.println("page = " + page + ", key = " + key + ", keyword = " + keyword + ", view = " + view);


        return companyOrderService.getCompany(page , key , keyword , view);
    }

    // 2-2 개별 거래처목록 출력
    @GetMapping("/doGet")
    public CompanyOrderDto doGetCompany( @RequestParam int cono ){
        System.out.println("doGetCompanyController");

        return companyOrderService.doGetCompany(cono);
    }

    // 3. 발주 수정 [U]
    @PutMapping("")
    public boolean putCompany(@RequestBody CompanyOrderDto companyOrderDto){
        System.out.println("putCompanyController");

        return companyOrderService.putCompany(companyOrderDto);
    }

}// class e
