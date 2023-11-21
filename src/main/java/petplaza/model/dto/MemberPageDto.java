package petplaza.model.dto;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class MemberPageDto {

    // 1. 총 회원들의 정보
    List<MemberDto> memberDtoList;

    // 2. 반환된 총 페이지수
    int totalPages;

    // 3. 반환된 총 회원 수
    long totalCount;
}
