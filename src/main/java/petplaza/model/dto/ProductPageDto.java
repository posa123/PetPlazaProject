package petplaza.model.dto;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder

public class ProductPageDto {
    // 1. 반환된 총 제품들
    List<ProductDto> productDtoList;

    // 2. 반환된 총 페이지 수
    int totalPages;

    // 3. 반환된 총 제품 수
    Long totalCount;
}
