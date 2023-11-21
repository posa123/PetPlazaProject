package petplaza.model.dto;

import lombok.*;
import petplaza.model.entity.OrderEntity;

import java.time.LocalDateTime;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder

public class OrderDto {

    private int ono;

    private String ostate;          // 상태

    private String orequest;        // 요청사항

    private String odestination;    // 도착지

    private int mno;                // 회원명

    private LocalDateTime cdate;
    private LocalDateTime udate;

    private  int dno;

    private String mid;



    public OrderEntity toEntity() {
        return OrderEntity.builder()
                .ono(this.ono)
                .ostate(this.ostate)
                .orequest(this.orequest)
                .odestination(this.odestination)
                .build();

    }
}
