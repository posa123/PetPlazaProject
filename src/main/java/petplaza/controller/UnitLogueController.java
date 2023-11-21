package petplaza.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import petplaza.model.dto.UnitLogueDto;
import petplaza.model.dto.UnitLoguePageDto;
import petplaza.model.entity.UnitLogueEntity;
import petplaza.service.UnitLogueService;

import java.util.List;

@RestController
@RequestMapping("/unitlogue")
public class UnitLogueController {
    @Autowired
    private UnitLogueService unitLogueService; // UnitLogueService 객체 생성

    //sdfsdfsdf
    // 1. 재고로그 출력
    @GetMapping("/get")
    public UnitLoguePageDto UnitLogueAll(@RequestParam int page , @RequestParam String key , @RequestParam String keyword, @RequestParam int view){

        return unitLogueService.UnitLogueAll(page, key, keyword, view);
    }
    // 2. 재고로그 수정
    @PutMapping("/put")
    public boolean  UnitLoguePut(@RequestBody UnitLogueDto logueDto){
        System.out.println(logueDto);
        return unitLogueService.UnitLoguePut(logueDto);
    }

    // 3. 재고로그 삭제
    @DeleteMapping("/delete")
    public boolean  UnitLogueDelete(@RequestParam int ulno){
        System.out.println("js에서넘어온 ulno"+ulno);
        return unitLogueService.UnitLogueDelete(ulno);
    }

    // 4. 현재제품재고수량
    @GetMapping("/nowcount")
    public int nowCount(@RequestParam int pno){
        return unitLogueService.nowCount(pno);
    }
}
