package petplaza.model.dto;

import lombok.*;
import petplaza.model.entity.UnitLogueEntity;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class UnitLogueDto {
    private int ulno;   // 식별번호 : 재고기록번호 [pk]
    private String urecord; // 재고기록
    private int uincrease;  // 수량
    private int pno;
    private String pname;
    private String cdate;
    private String udate;

    // - Dto ---> Entity 변환
    public UnitLogueEntity toUnitLogueEntity(){
        return UnitLogueEntity.builder()
                .ulno(this.ulno)
                .urecord(this.urecord)
                .uincrease(this.uincrease)
                .build();
    }
}
