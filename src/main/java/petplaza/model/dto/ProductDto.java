package petplaza.model.dto;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;
import petplaza.model.entity.BaseTime;
import petplaza.model.entity.ProductEntity;
import petplaza.model.entity.UnitpriceEntity;

import javax.persistence.*;
import java.time.LocalDateTime;


// 야 이 멍청한 commit아
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class ProductDto extends BaseTime {
    private int pno; // 1. 제품번호
    String pname; // 2. 제품이름
    int pprice; // 3. 제품가격
    private LocalDateTime cdate;
    private LocalDateTime udate;

    private int uincrease; // 4. 재고수량

    // + 등록용
    private MultipartFile fileList; // 첨부파일

    // + 출력용
    private String imageUrl; // ProductDto에 이미지 URL 필드 추가

    // Dto -> Entity
    public ProductEntity productEntity(){
        return ProductEntity.builder()
                .pno(this.pno)
                .pname(this.pname)
                .pprice(this.pprice)
                .imageURL(this.imageUrl)
                .build();

    }



}
