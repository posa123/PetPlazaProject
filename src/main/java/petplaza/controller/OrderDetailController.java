package petplaza.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import petplaza.model.dto.OrderDetailDto;
import petplaza.model.dto.OrderDetailPageDto;
import petplaza.service.OrderDetailService;


import java.util.List;

@RestController
@RequestMapping("/orderdetail")
public class OrderDetailController {

    // 상세정보 관리 - 상세관리번호 , 가격 , 수량 , 제품번호 , 주문번호

    @Autowired
    private OrderDetailService orderDetailService;

    // 상세 관리 출력
    @GetMapping("/get")
    public List<OrderDetailDto> getDetail(@RequestParam int ono) {
        return orderDetailService.getDetail(ono);
    }
    // 상세 관리 수정
    @PutMapping("/put")
    public boolean putDetail(@RequestBody OrderDetailDto orderDetailDto) {
        return orderDetailService.putDetail(orderDetailDto);
    }
    // 상세 관리 삭제
    @DeleteMapping("/delete")
    public boolean deleteDetail(@RequestParam int dno) {
        return orderDetailService.removeDetail(dno);
    }
}
