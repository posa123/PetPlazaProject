package petplaza.model.entity;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@MappedSuperclass // 엔티티X [ 여러 엔티티가 공통으로 사용하는 필드에 대해 구성할때 ]
@EntityListeners(AuditingEntityListener.class) // jap Auditing 이벤트 발생 [  @CreatedDate(insert) , @LastModifiedDate(update) ]
@Getter
public class BaseTime {
    @CreatedDate        // 엔티티가 생성될때 시간이 자동 저장/주입
    private LocalDateTime cdate; // 레코드/엔티티 생성날짜
    @LastModifiedDate   // 엔티티가 변경될때 시간이 자동 저장/주입
    private LocalDateTime udate; // 레코드/엔티티 수정날짜


    //날짜 형변환 메소드[LocalDateTime->String]
    // BaseTime.java
    public String toTimeOrDate(LocalDateTime dateTime) {
        if (dateTime != null) {
            return dateTime.toLocalDate().toString().equals(LocalDateTime.now().toLocalDate().toString()) ?
                    dateTime.toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm:ss")) :
                    dateTime.toLocalDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        } else {
            return ""; // 또는 적절한 기본값을 반환
        }
    }

}