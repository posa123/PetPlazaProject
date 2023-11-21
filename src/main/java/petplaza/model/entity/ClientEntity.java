package petplaza.model.entity;

import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import petplaza.model.dto.ClientDto;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@Entity // 해당 클래스를 엔티티로 사용 '
@Table(name = "client") // 테이블명 설정
@DynamicInsert // @ColumnDefault 가 적용될수 있도록 해주는 어노테이션
public class ClientEntity extends BaseTime{ // 거래처 테이블

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // mysql : auto_increment
    // 필드 설계
    private int cno;        // 1.거래처번호 pk int

    //[고연진수정] : unique추가
    @Column(name = "cname", length = 20, nullable = false,unique = true)
    private String cname;        // 2.거래처이름
    @Column(name = "cphone", length = 15, nullable = false,unique = true)
    private String cphone;        // 3.거래처전화번호
    @Column(name = "caddress", length = 50, nullable = false,unique = true)
    private String caddress;       // 4.거래처주소
    @Builder.Default
    @OneToMany ( mappedBy = "clientEntity",cascade = CascadeType.ALL)
    private List<CompanyOrderEntity> companyOrderEntityList = new ArrayList<>();

    @Builder.Default
    @OneToMany ( mappedBy = "clientEntity",cascade = CascadeType.ALL)
    private List<UnitpriceEntity> unitpriceEntityList = new ArrayList<>();


    // entity ---> dto 변환 함수 (entity --> dto)
    public ClientDto toDto() { // todt
        return ClientDto.builder()
                .cno(this.cno)
                .cname(this.cname)
                .cphone(this.cphone)
                .caddress(this.caddress)
                .cdate(this.getCdate() )
                .udate(this.getUdate() )
                .build();

    }

}