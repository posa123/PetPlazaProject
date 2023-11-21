package petplaza.model.dto;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class SchedulePageDto {
    List<ScheduleDto> scheduleDtos; //총 게시물들
    int totalPages; // 총 페이지수
    Long totalCount; //

}
