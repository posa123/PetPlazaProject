package petplaza.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import petplaza.model.dto.UnitPricePageDto;
import petplaza.model.dto.UnitpriceDto;
import petplaza.service.UnitpriceService;

import java.util.List;

@RestController
@RequestMapping("/unitprice")
public class UnitpriceController {
    @Autowired
    private UnitpriceService unitpriceService;
    @PostMapping("/post")
    // 1. 거래처 재고 등록
    public boolean UnitpriceWrite(UnitpriceDto unitpriceDto){
        boolean result = unitpriceService.UnitpriceWrite(unitpriceDto);
        return result;
    }
    @GetMapping("/get")
    // 2. 거래처 재고 리스트 및 페이징 처리 출력
    public UnitPricePageDto UnitpriceAll(@RequestParam int page , @RequestParam String key , @RequestParam String keyword, @RequestParam int view){
        System.out.println("Controller view: "+view);
        return unitpriceService.UnitpriceAll(page , key , keyword , view);
    }

    @PutMapping("/put")
    // 3. 거래처 재고 수정
    public boolean Unitprice(@RequestBody UnitpriceDto dto){
        boolean result = unitpriceService.UnitpriceUpdate(dto);
        return result;
    }

    @DeleteMapping("/delete")
    // 4. 거래처 재고 삭제
    public boolean Unitpricedelete(@RequestParam int uno){
        boolean result = unitpriceService.Unitpricedelete(uno);
        return result;
    }
}
