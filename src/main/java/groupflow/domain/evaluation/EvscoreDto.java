package groupflow.domain.evaluation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data@AllArgsConstructor@NoArgsConstructor@Builder
public class EvscoreDto {

    private int esno; // 식별번호
    private int evno; // 평가테이블 식별번호
    private int eqno; // 문항테이블 식별번호
    private String eqtitle; // 문항타이틀
    private String equestion; // 문항내용
    private int eqscore; // 점수
}
