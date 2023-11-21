package petplaza.model.entity;

import lombok.*;
import petplaza.model.dto.OrderDetailDto;

import javax.persistence.*;

@Entity
@Table(name = "orderdetail")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class OrderDetailEntity extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int dno;

    @Column(nullable = false)
    private int damount;

    @Column(nullable = false)
    private int dprice;

    @ToString.Exclude
    @JoinColumn(name = "pno")
    @ManyToOne
    private ProductEntity productEntity;

    @ToString.Exclude
    @JoinColumn(name = "ono")
    @ManyToOne
    private OrderEntity orderEntity;



    public OrderDetailDto toDetailDto() {
        return OrderDetailDto.builder()
                .dno(this.dno)
                .damount(this.damount)
                .dprice(this.dprice)
                .pno(this.productEntity.getPno())
                .ono(this.orderEntity.getOno())
                .pname(this.productEntity.getPname())
                .cdate(this.getCdate())
                .udate(this.getUdate())
                .pprice(this.productEntity.getPprice())
                .build();
    }
}
