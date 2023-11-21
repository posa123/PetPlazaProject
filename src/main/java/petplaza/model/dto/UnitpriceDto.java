package petplaza.model.dto;

import lombok.*;
import petplaza.model.entity.UnitpriceEntity;

import java.time.LocalDateTime;

@NoArgsConstructor @AllArgsConstructor
@Getter @Setter @ToString @Builder
public class UnitpriceDto {

    private int uno;                 // 재고 번호
    private String uname;            // 재고이름
    private String uoriginal;        // 재고원가
    private int cno;                 // 거채처번호 [ fk ]
    private String cdate;
    private String udate;


    // - Dto ---> Entity 변환
    public UnitpriceEntity tounitpriceEntity(){
        return UnitpriceEntity.builder()
                .uno(this.uno)
                .uname(this.uname)
                .uoriginal(this.uoriginal)
                .build();
    }
}
