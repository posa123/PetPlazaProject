package petplaza.model.entity;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import petplaza.model.dto.ScheduleDto;

import javax.persistence.*;

@Entity
@Table(name = "schedule")
@AllArgsConstructor@NoArgsConstructor
@Setter@Getter@ToString@Builder@DynamicInsert
public class ScheduleEntity extends BaseTime{
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) // auto.incerment
    private int sno;
    @Column
    private String sclass; //분류
    @Column @ColumnDefault("'내용없음'")
    private String stitle; //제목
    @Column(columnDefinition = "longtext", nullable = true)
    private String scontent; //내용
    @Column(nullable = false) @ColumnDefault("0")
    private String sstate; //상태

    @Column
    private String sstart; //시작일

    @Column
    private String send; //종료일

    public ScheduleDto ToDto(){
        return ScheduleDto.builder()
                .sno(this.sno)
                .sclass(this.sclass)
                .stitle(this.stitle)
                .scontent(this.scontent)
                .sstate(this.sstate)
                .sstart(this.sstart)
                .send(this.send)
                .cdate(toTimeOrDate(this.getCdate()))
                .udate(toTimeOrDate(this.getUdate()))
                .build();
    }


}
