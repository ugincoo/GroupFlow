package groupflow.controller;

import groupflow.domain.evaluation.EquestionDto;
import groupflow.domain.evaluation.EvaluationDto;
import groupflow.service.EvaluationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
}
