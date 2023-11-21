package petplaza.model.dto;

import lombok.*;
import petplaza.model.entity.MemberEntity;

import java.time.LocalDateTime;

@AllArgsConstructor@NoArgsConstructor
@Getter@Setter@ToString@Builder
public class MemberDto {
    private int mno;        // 회원번호
    private String mname;   // 회원이름 
    private String mid;     // 회원아이디
    private String mpwd;    // 회원비밀번호
    private String mphone;   // 회원 전화번호
    // baseTime
    private LocalDateTime cdate;    // 등록날짜
    private LocalDateTime udate;    // 수정 날짜

        // Dto -> Entity 변환
    public MemberEntity toEntity(){
        return MemberEntity.builder()
                .mno(this.mno)
                .mname(this.mname)
                .mid(this.mid)
                .mpwd(this.mpwd)
                .mphone(this.mphone)
                .build();
    }
}
