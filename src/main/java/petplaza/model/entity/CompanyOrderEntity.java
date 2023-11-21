package petplaza.model.entity;


import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import petplaza.model.dto.CompanyOrderDto;

import javax.persistence.*;

@Entity
@Table(name="companyorder")
@DynamicInsert
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class CompanyOrderEntity extends BaseTime{ // 발주 엔티티

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int cono; //발주번호

    @Column(name="coamount", nullable=false)
    private int coamount; //발주수량

    @Column(name="costate", nullable=false)
    private String costate; //발주상태

    @Column(name="cocontent", nullable=false , length = 30)
    private String cocontent; //발주내역

    @ToString.Exclude
    @JoinColumn(name="cno") //fk 거래처번호 
    @ManyToOne
    private ClientEntity clientEntity; //거래처

    // entity -> dto 변환후 디티오에게 전달 한다.
    public CompanyOrderDto companyDto() {
        return  CompanyOrderDto.builder()
                .cono(this.cono)
                .coamount(this.coamount)
                .costate(this.costate)
                .cocontent(this.cocontent)
                .cdate(this.getCdate() )
                .udate(this.getUdate() )
                .build();
    }


}