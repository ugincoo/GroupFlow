package groupflow.controller;

import groupflow.domain.evaluation.EquestionDto;
import groupflow.domain.evaluation.EvaluationDto;
import groupflow.service.EvaluationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/evaluation")
public class EvaluationController {

    @Autowired
    private EvaluationService evaluationService;

    // 1. 평가하기 [ 매개변수 : 평가대상자eno, 10문항평가점수객체 , 평가의견 ]
    @PostMapping("")
    public byte evaluate( @RequestBody EvaluationDto evaluationDto){
        return evaluationService.evaluate(evaluationDto);
    }

    // 2. 문항레코드 반환하기
    @GetMapping("/equestion")
    public List<EquestionDto> getEquestion(){
        return evaluationService.getEquestion();
    }

    // 3.  입력받은 eno에 해당하는 인사평가 리스트 반환
    @GetMapping("/list")
    public List<EvaluationDto> getEvaluationList( int eno ){
        return evaluationService.getEvaluationList(eno);
    }

    // 4. 입력받은 평가대상자의 eno로 현재 분기 업무평가가 이미 등록되었는지 유효성검사
    @GetMapping("/check")
    public boolean checkEvaluation( @RequestParam int eno ){
        return evaluationService.checkEvaluation(eno);
    }
    
    // 5. 기존 업무평가 수정
    @PutMapping("")
    public byte updateEvaluation( @RequestBody EvaluationDto evaluationDto ){
        return evaluationService.updateEvaluation(evaluationDto);
    }
}
