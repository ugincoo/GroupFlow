package groupflow.domain.evaluation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data@AllArgsConstructor@NoArgsConstructor@Builder
public class EvaluationViewDto {
    private int eqno;
    private String eqtitle;
    private String equestion;
    private int eqscore;
    private int evno;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime cdate;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime udate;
    private String evopnion;
    private int evaluatoreno;
    private int targeteno;
}
