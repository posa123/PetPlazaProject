package petplaza.model.entity;


import lombok.*;
import petplaza.model.dto.UnitLogueDto;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "unitlogue")
@NoArgsConstructor @AllArgsConstructor
@Getter @Setter @ToString @Builder
public class UnitLogueEntity extends BaseTime{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ulno; // 재고기록번호[pk]
    @Column(name="urecord", length = 100 , nullable = false)
    private String urecord; //재고기록내용
    @Column(name="uincrease", nullable = false)
    private int uincrease; // 재고 슈량
    @ToString.Exclude // toString() 함수에서 제외되는 필드
    @JoinColumn( name = "pno") // FK 필드로 사용하겠다. ( name = "fk필드명")

    @ManyToOne // 다수가 하나에게 참조를 하고있다. // 실제 DB에는 엔티티의 ID(PK)만 저장
    private ProductEntity productEntity;

    // - Entity ---> Dto 변환
    public UnitLogueDto toUnitLogueDto(){
        return UnitLogueDto.builder()
                .ulno(this.ulno)
                .urecord(this.urecord)
                .uincrease(this.uincrease)
                .pno(productEntity.getPno())
                .pname(productEntity.getPname())
                .cdate(toTimeOrDate(this.getCdate()))
                .udate(toTimeOrDate(this.getUdate()))
                .build();
    }
}
