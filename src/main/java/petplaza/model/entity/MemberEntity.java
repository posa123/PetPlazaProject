package petplaza.model.entity;
import lombok.*;
import petplaza.model.dto.MemberDto;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@Table(name = "member")
public class MemberEntity extends BaseTime {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private int mno;
    @Column( length = 20 , nullable = false ,unique = true)
    private String mname;
    @Column( length = 30 , nullable = false ,unique = true)
    private String mid;
    @Column( length = 30 , nullable = false )
    private String mpwd;
    @Column( length = 13 , nullable = false ,unique = true )
    private String mphone;

    @Builder.Default
    @OneToMany ( mappedBy = "memberEntity",cascade = CascadeType.ALL)
    private List<OrderEntity>  orderEntityList = new ArrayList<>();

   // entity -- > dto 변환
    public MemberDto allToDto(){
        return MemberDto.builder()
                .mno(this.mno)
                .mid(this.mid)
                .mpwd(this.mpwd)
                .mname(this.mname)
                .mphone(this.mphone)
                .cdate(this.getCdate())
                .udate(this.getUdate())
                .build();
    }
}