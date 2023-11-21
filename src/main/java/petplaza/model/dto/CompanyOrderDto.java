package petplaza.model.dto;


import lombok.*;
import petplaza.model.entity.CompanyOrderEntity;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class CompanyOrderDto { // 발주 dto

    // 0. 필드 설계
    private int cono;            // 발주번호
    private int coamount;        // 발주수량
    private String costate;      // 발주상태
    private String cocontent;    // 발주내역
    private int pno;             // 제품 번호

    // 1. baseTime
    private LocalDateTime cdate;
    private LocalDateTime udate;

    // dto ---> entity 변환 함수 (dto --> entity)
    public CompanyOrderEntity comEntity(){
        return CompanyOrderEntity.builder()
                .cono(this.cono)
                .coamount(this.coamount)
                .costate(this.costate)
                .cocontent(this.cocontent)
                .build();
    }


}
