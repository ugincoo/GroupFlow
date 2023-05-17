package groupflow.domain.evaluation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data@AllArgsConstructor@NoArgsConstructor@Builder
public class EvaluationDto {

    private int evno; // 식별번호
    //private int evaluatorEno;   // 평가자eno -> 로그인세션으로 확인
    private int targetEno;  // 평가대상자eno
    private String evopnion; // 평가의견

    // 점수DTO 리스트 ( 문항 , 점수 )
    private List<EvscoreDto> evscoreDtoList = new ArrayList<>();


}
