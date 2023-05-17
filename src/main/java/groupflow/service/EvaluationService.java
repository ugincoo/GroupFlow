package groupflow.service;

import groupflow.domain.employee.EmployeeDto;
import groupflow.domain.evaluation.EvaluationDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
@Slf4j
public class EvaluationService {

    @Autowired private EmployeeService employeeService;
    @Autowired private LoginService loginService;


    // 1. 평가하기 [ 매개변수 : 평가대상자eno, 10문항평가점수객체 ]
    public boolean evaluate( EvaluationDto evaluationDto ){
        // 1. 로그인세션 호출해서 부장님인지 확인하기
            // 1. 부장님 pno가져오기
        int managerdno = employeeService.findManagerDno();
            // 2. 로그인한 세션 가져오기
        EmployeeDto employeeDto = loginService.loginInfo();
        if( managerdno != employeeDto.getPno() ){ return false; } // 부장이 아님 권한없음


        return true;
    }
}
