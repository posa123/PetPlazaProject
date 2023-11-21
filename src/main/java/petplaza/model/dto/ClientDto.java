package petplaza.model.dto;


import lombok.*;
import petplaza.model.entity.ClientEntity;


import java.time.LocalDateTime;

@AllArgsConstructor@NoArgsConstructor
@Getter@Setter@ToString@Builder
public class ClientDto { // 거래처 dto


    // 0. 필드 설계
    private int cno;        // 1.거래처번호 pk int

    private String cname;        // 2.거래처이름

    private String cphone;        // 3.거래처전화번호

    private String caddress;       // 4.거래처주소

    // 1. baseTime
    private LocalDateTime cdate;
    private LocalDateTime udate;

    // dto ---> entity 변환 함수 (dto --> entity)
    public ClientEntity toEntity() {
        return ClientEntity.builder()
                .cno(this.cno)
                .cname(this.cname)
                .cphone(this.cphone)
                .caddress(this.caddress)
                .build();

    }



}
