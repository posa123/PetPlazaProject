package petplaza.model.dto;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class ClientPageDto {

    // 1. 반환된 총 게시물들
    List<ClientDto> clientDtoList;

    // 2. 반환된 총 페이지수
    int totalPages;

    // 3. 반환된 총 게시물 수
    Long totalCount;

}// class e
