package petplaza.model.dto;

import lombok.*;
import org.hibernate.criterion.Order;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class OrderPageDto {
    // 1. 반환된 총 게시물수
    List<OrderDto> orderDtos;
    // 2. 반환된 총 페이지수
    int totalPages;
    // 3. 반환된 총 게시물수
    long totalCounts;

}
