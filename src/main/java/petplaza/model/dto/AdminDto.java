package petplaza.model.dto;

import lombok.*;

@AllArgsConstructor@NoArgsConstructor
@Getter@Setter@ToString@Builder
public class AdminDto {
    private String admin;
    private String pw;
}
