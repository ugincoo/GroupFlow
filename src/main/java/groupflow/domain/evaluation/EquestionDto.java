package groupflow.domain.evaluation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data@AllArgsConstructor@NoArgsConstructor@Builder
public class EquestionDto {
    private int eqno; //식별번호
    private String eqtitle; // 평가항목
    private String equestion; // 문항
}
