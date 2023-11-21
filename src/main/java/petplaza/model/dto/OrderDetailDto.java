package petplaza.model.dto;

import lombok.*;
import petplaza.model.entity.OrderDetailEntity;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder

public class OrderDetailDto {

    private int dno;

    private int damount;

    private int dprice;

    private int ono;

    private int pno;

    private LocalDateTime cdate;
    private LocalDateTime udate;

    /* 제품이름을 사용하기 위해서 DTO 추가 */
    private String pname;
    private int pprice;

    private String mid;

    public OrderDetailEntity DetailEntity() {
        return OrderDetailEntity.builder()
                .dno(this.dno)
                .damount(this.damount)
                .dprice(this.dprice)
                .build();

    }
}
