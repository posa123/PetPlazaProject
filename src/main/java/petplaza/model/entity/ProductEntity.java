package petplaza.model.entity;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;
import petplaza.model.dto.ProductDto;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity // 해당 클래스를 DB테이블과 매핑 [엔티티클래스 <---> db테이블(엔티티 1개 객체 <---> 테이블내 레코드 1개)]
@Table(name = "product") // db테이블명 정의 [ 생략시 해당 클래스명이 곧 db테이블 명으로 자동 생성]
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class ProductEntity extends BaseTime { // 커밋용
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto.incerment
    private int pno; // 1. 제품번호
    @Column(name="pname", length = 20, nullable = false, unique = true)
    String pname; // 2. 제품이름
    @Column(name = "pprice", nullable = false)
    int pprice; // 3. 제품가격
    // 수정 중
    @Column(name="imageURL")
    private String imageURL;

    @Builder.Default
    @OneToMany ( mappedBy = "productEntity",cascade = CascadeType.ALL)
    private List<UnitLogueEntity> unitLogueEntityList = new ArrayList<>();

    @Builder.Default
    @OneToMany ( mappedBy = "productEntity",cascade = CascadeType.ALL)
    private List<OrderDetailEntity> orderDetailEntityList = new ArrayList<>();

    // + 첨부파일 [spring 지원하는 첨부파일 라이브러리]
    // private MultipartFile file;


    // Entity -> Dto
    public ProductDto allDto(){
        return ProductDto.builder()
                .pno(this.pno)
                .pname(this.pname)
                .pprice(this.pprice)
                .imageUrl( this.imageURL )
                .cdate(this.getCdate())
                .udate(this.getUdate())
                .build();

    }



}