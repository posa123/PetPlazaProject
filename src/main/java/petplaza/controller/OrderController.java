package petplaza.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import petplaza.model.dto.OrderDetailDto;
import petplaza.model.dto.OrderDto;
import petplaza.model.dto.OrderPageDto;
import petplaza.model.dto.ProductDto;
import petplaza.service.OrderService;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;
    
    @PostMapping("/post") // 주문 등록하기
    public boolean postOrder(OrderDto orderDto, OrderDetailDto orderDetailDto) {
        System.out.println("orderDto = " + orderDto + ", orderDetailDto = " + orderDetailDto);
        return orderService.postOrder(orderDto, orderDetailDto);
    }

    @GetMapping("/get") // 주문 관리 리스트 전체 출력
    public OrderPageDto getOrder(@RequestParam int page , @RequestParam String key , @RequestParam String keyword, @RequestParam int view) {
        return orderService.getOrder(page, key, keyword, view);
    }

    @GetMapping("/get/product")
    public List<ProductDto> getProduct() {
        return orderService.getProduct();
    }


    @PutMapping("/put") // 주문 관리 리스트 상태 변경
    public boolean putOrder(@RequestBody OrderDto orderDto) {
        return orderService.putOrder(orderDto);
    }
}
