package petplaza.model.entity;

import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import petplaza.model.dto.OrderDto;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table( name = "ordertable" )
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@DynamicInsert
public class OrderEntity extends BaseTime {
    // 하이
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ono; // 주문번호

    @Column(length = 10, name = "ostate" ,columnDefinition = "VARCHAR(255) DEFAULT '요청'")
    private String ostate; // 주문상태

    @Column( length = 100)
    private String orequest; // 요청사항
    @Column(length = 20 , nullable = false)
    private String odestination; // 도착지
    @ToString.Exclude
    @JoinColumn(name="mno") // 회원번호
    @ManyToOne
    private MemberEntity memberEntity;


    @Builder.Default // 빌더패턴 사용시 해당 필드를 값을 기본값으로 사용
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "orderEntity" ,cascade = CascadeType.ALL ) // 하나가 다수에게 참조 pk // 실제  DB 영향 X



    private List<OrderDetailEntity> orderDetailEntity = new ArrayList<>();


    
    // Entity를 Dto로 변환
    public OrderDto toDto() {
        return OrderDto.builder()
                .ono(this.ono)
                .ostate(this.ostate)
                .orequest(this.orequest)
                .odestination(this.odestination)
                .mid(this.memberEntity.getMid())
                .cdate(this.getCdate())
                .udate(this.getUdate())
                .build();
    }
}
