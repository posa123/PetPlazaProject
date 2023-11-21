package petplaza.model.entity;

import lombok.*;
import petplaza.model.dto.UnitpriceDto;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "unitprice")
@NoArgsConstructor@AllArgsConstructor
@Getter@Setter@ToString@Builder
public class UnitpriceEntity extends BaseTime{ // 거래처단가관리 테이블
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )

    private int uno;    // 재고단가번호 [PK] 식별번호
    @Column(name="uname", length = 70 , nullable = false ,unique = true) // 글자길이 70글자 , 공백불가 , 중복불가
    private String uname; // 재고단가이름
    @Column(name="uoriginal", nullable = false ) //공백불가
    private String uoriginal; // 재고원가

    @ToString.Exclude // toString() 함수에서 제외되는 필드
    @JoinColumn( name = "cno") // FK 필드로 사용하겠다. ( name = "fk필드명")
    @ManyToOne // 다수가 하나에게 참조를 하고있다. // 실제 DB에는 엔티티의 ID(PK)만 저장
    private ClientEntity clientEntity;


    // Entity -> Dto 변환
    public UnitpriceDto toUnitpriceDto(){
        return UnitpriceDto.
                builder()
                .uno(this.uno)
                .uname(this.uname)
                .uoriginal(this.uoriginal)
                .cdate(toTimeOrDate(this.getCdate()))
                .udate(toTimeOrDate(this.getUdate()))
                .build();
    }
}
