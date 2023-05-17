package groupflow.service;

import groupflow.domain.department.DepartmentEntity;
import groupflow.domain.department.DepartmentRepository;
import groupflow.domain.employee.EmployeeDto;
import groupflow.domain.employee.EmployeeEntity;
import groupflow.domain.employee.EmployeeRepository;
import groupflow.domain.evaluation.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;
import java.util.Optional;

@Service
@Slf4j
public class EvaluationService {

    @Autowired private EmployeeService employeeService;
    @Autowired private LoginService loginService;
    @Autowired private EmployeeRepository employeeRepository;
    @Autowired private EvaluationRepository evaluationRepository;
    @Autowired private EquestionRepository equestionRepository;
    @Autowired private DepartmentRepository departmentRepository;
    @Autowired private EvscoreRepository evscoreRepository;


    // 1. 평가하기 [ 매개변수 : 평가대상자eno, 10문항평가점수객체 ]
    public boolean evaluate( EvaluationDto evaluationDto ){
        // 1. 로그인세션 호출해서 부장님인지 확인하기
            // 1. 부장님 pno가져오기
        int managerdno = employeeService.findManagerDno();
            // 2. 로그인한 세션 가져오기
        EmployeeDto loginEmployeeDto = loginService.loginInfo();
        if( managerdno != loginEmployeeDto.getPno() ){ return false; } // 부장이 아님 권한없음

        // 2. 평가대상이 부장과 동일한 부서인지 검사 ( 입력값으로 받은 평가대상자의eno로 부서dno가져오기 )
            // departmentRepository에 eno로 직원의 소속 부서(departmentEntity) 찾기 쿼리
        Optional<DepartmentEntity> optionalDepartmentEntity = departmentRepository.findByEno(evaluationDto.getTargetEno());
        if(optionalDepartmentEntity.isPresent()){
            if ( optionalDepartmentEntity.get().getDno() != loginEmployeeDto.getDno() ){ return false; }
        }

        // 3. 평가entity DB저장
            // 1. DB에 추가할 EvaluationEntity 객체 생성
        EvaluationEntity evaluationEntity = new EvaluationEntity();
            // 2. 평가자 employeeEntity 구해서 EvaluationEntity에 넣기
        Optional<EmployeeEntity> optionalEvaluatorEmployeeEntity = employeeRepository.findById(loginEmployeeDto.getEno());
        optionalEvaluatorEmployeeEntity.ifPresent( employeeEntity -> { evaluationEntity.setEvaluatorEmployeeEntity(employeeEntity); });
            // 3. 평가대상자 employeeEntity 구해서 EvaluationEntity에 넣기
        Optional<EmployeeEntity> optionalTargetEmployeeEntity = employeeRepository.findById(evaluationDto.getTargetEno());
        optionalTargetEmployeeEntity.ifPresent( employeeEntity -> { evaluationEntity.setTargetEmployeeEntity(employeeEntity);});
            // 4. 평가의견 EvaluationEntity에 넣기
        evaluationEntity.setEvopnion(evaluationDto.getEvopnion());
            // 4. DB에 저장
        EvaluationEntity saveEvaluationEntity = evaluationRepository.save(evaluationEntity);
        if ( saveEvaluationEntity.getEvno() < 1 ){ return false; }
        
        // 3. 점수entity DB저장
        /*
            // 1. evaluationDto.getEvscoreDtoList() for문 돌려서 점수테이블 여러개 생성
        evaluationDto.getEvscoreDtoList().forEach((evscoreDto -> {
                // 1. 저장할 EquestionEntity 객체생성
            EvscoreEntity evscoreEntity = new EvscoreEntity();
                // 2. evscoreEntity에 평가entity넣기
            evscoreEntity.setEvaluationEntity(saveEvaluationEntity);
                // 3. evscoreEntity에 문항equestionEntity 넣기
                    // 하나의 evscoreDto에는 문항테이블 fk와 점수가 있음
            Optional<EquestionEntity> optionalEquestionEntity  = equestionRepository.findById(evscoreDto.getEqno());
            optionalEquestionEntity.ifPresent( equestionEntity -> { evscoreEntity.setEquestionEntity(equestionEntity);});
                // 4. evscoreEntity에 점수넣기
            evscoreEntity.setEqscore(evscoreDto.getEqscore());
        }));
         */
        // 3. 점수entity DB저장 - Map에서 키(문항fk), 값(점수) 꺼내서 entity에 저장하기
        for (Map.Entry<Integer, Integer> score : evaluationDto.getEvscoreMap().entrySet()) {
            // 1. 저장할 EquestionEntity 객체생성
            EvscoreEntity evscoreEntity = new EvscoreEntity();
            // 2. evscoreEntity에 평가entity넣기
            evscoreEntity.setEvaluationEntity(saveEvaluationEntity);
            // 3. evscoreEntity에 문항equestionEntity 넣기
                // 하나의 score에는 문항테이블 fk와 점수가 있음
                // key와 value를 사용하여 DB 테이블에 레코드를 저장하는 로직 수행
            Integer eqno = score.getKey();
            Optional<EquestionEntity> optionalEquestionEntity  = equestionRepository.findById(eqno);
            optionalEquestionEntity.ifPresent( equestionEntity -> { evscoreEntity.setEquestionEntity(equestionEntity);});

            // 4. evscoreEntity에 점수넣기
            Integer eqscore = score.getValue();
            evscoreEntity.setEqscore(eqscore);

            // 5. evescoreEntity DB에 저장
            EvscoreEntity saveEvscoreEntity = evscoreRepository.save(evscoreEntity);
            if ( saveEvscoreEntity.getEsno() < 1 ){ return false;}
        }
        // for문 다 끝나고
        return true;
    }
}
