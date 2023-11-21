package petplaza.model.dto;
import lombok.*;
import petplaza.model.entity.ScheduleEntity;

import java.time.LocalDateTime;

@NoArgsConstructor@AllArgsConstructor
@Getter@Setter@Builder@ToString
public class ScheduleDto {
    private int sno;
    private String sclass; //분류
    private String stitle; //제목
    private String scontent; //내용
    private String sstate; //상태- 진행, 보류, 완료
    private String sstart; //시작일
    private String send; //종료일

    private String cdate;
    private String udate;

    //
    public ScheduleEntity toEntity(){
        return ScheduleEntity.builder()
                .sno(this.sno)
                .sclass(this.sclass)
                .stitle(this.stitle)
                .scontent(this.scontent)
                .sstate(this.sstate)
                .sstart(this.sstart)
                .send(this.send)
                .build();
    }
    //등록 시 제목이 공백일 때 사용
    public ScheduleEntity registerEntity(){
        return ScheduleEntity.builder()
                .sno(this.sno)
                .sclass(this.sclass)
                .scontent(this.scontent)
                .sstate(this.sstate)
                .sstart(this.sstart)
                .send(this.send)
                .build();
    }




}
