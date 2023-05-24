package groupflow.domain.evaluation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data@AllArgsConstructor@NoArgsConstructor@Builder
public class EvaluationViewDto {

    private int evno;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime cdate;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime udate;
    private String evopnion;
    private int evaluatoreno;
    private String evaluatorename;
    private int targeteno;
    private String targetename;
    @Builder.Default private List<EvscoreDto> evscoreDtoList = new ArrayList<>();
    

}
