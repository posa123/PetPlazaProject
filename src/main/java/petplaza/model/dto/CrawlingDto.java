package petplaza.model.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@ToString
@Builder
@Getter
public class CrawlingDto {
    private String cimg;    // 이미지
    private String curl;    // 링크
    private String ctitle;  // 제목
}
