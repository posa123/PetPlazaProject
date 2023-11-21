package petplaza.model.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class BusinessDto {
    private String pname;//상품명
    private int pprice;// 상품가격
    private int salesVolume; // 누적판매량
    private String cost; //상품원가
    private int stock; //재고

}
