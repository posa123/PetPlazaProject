package petplaza.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.*;
import petplaza.model.dto.ProductDto;
import petplaza.model.dto.ProductPageDto;
import petplaza.service.ProductService;

import java.util.List;


@RestController
@RequestMapping("/product")
public class ProductController {


    @Autowired
    private ProductService productService;

    // 1. 제품출력
    @GetMapping("/get")
    public ProductPageDto getAllProducts(@RequestParam int page, @RequestParam int view, @RequestParam String key, @RequestParam String keyword){
        return productService.getAllProducts(page,view,key,keyword);

    }
    // 1-1. 개별출력
    @GetMapping("/doget")
    public ProductDto doGet(@RequestParam int pno){
        return productService.doGet(pno);
    }


    // 2. 제품등록
    @PostMapping("/doPost")
    public boolean postProduct(ProductDto productDto){
        System.out.println("productDto = " + productDto);
        boolean result = productService.postProduct(productDto);
        
        return result;}

    // 3. 제품수정
    @PutMapping("/put")
    public boolean putProduct(@RequestBody ProductDto productDto){
        System.out.println("productDto = " + productDto);
        boolean result = productService.putProduct(productDto);
        return result;
    }
    // 4. 제품삭제
    @DeleteMapping("/delete")
    public boolean deleteProduct(@RequestParam int pno){
        boolean result = productService.deleteProduct(pno);
        return result;
    }


}
