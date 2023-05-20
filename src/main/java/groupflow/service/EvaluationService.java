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

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

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
    public byte evaluate( EvaluationDto evaluationDto ){
        // 1. 로그인세션 호출해서 부장님인지 확인하기
            // 1. 로그인한 세션 가져오기
        EmployeeDto loginEmployeeDto = loginService.loginInfo();
        if (loginEmployeeDto == null){ return 1;} // 로그인 안함.
            // 2. 직급테이블의 '부장'직급의 pno가져오기
        int managerpno = employeeService.findManagerPno();
            // 3. 부장이 아니면 false
        if( managerpno != loginEmployeeDto.getPno() ){ return 2; } // 부장이 아님 권한없음

        // 2. 평가대상이 부장과 동일한 부서인지 검사 ( 입력값으로 받은 평가대상자의eno로 부서dno가져오기 )
            // departmentRepository에 eno로 직원의 소속 부서(departmentEntity) 찾기 쿼리
        Optional<DepartmentEntity> optionalDepartmentEntity = departmentRepository.findByEno(evaluationDto.getTargetEno());
        if(optionalDepartmentEntity.isPresent()){
            if ( optionalDepartmentEntity.get().getDno() != loginEmployeeDto.getDno() ){ return 3; } // 평가권한이 없는 직원입니다.
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
        if ( saveEvaluationEntity.getEvno() < 1 ){ return 4; } // 평가테이블 저장실패
        
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
            if ( saveEvscoreEntity.getEsno() < 1 ){ return 5;} // 점수테이블 저장실패
        }
        // for문 다 끝나고
        return 6; // 업무평가 성공
    }

    // 문항 레코드 반환하기
    public List<EquestionDto> getEquestion(){
        List<EquestionEntity> equestionEntityList = equestionRepository.findAll();

        return equestionEntityList.stream().map(e-> e.toDto() ).collect(Collectors.toList());
    }

    // 입력받은 eno에 해당하는 인사평가 리스트 반환
    public List<EvaluationDto> getEvaluationList( int eno ){
        log.info("eno : " + eno);
        List<EvaluationEntity> evaluationEntityList = evaluationRepository.findByTargeteno(eno);
        return evaluationEntityList.stream().map( evaluationEntity ->  evaluationEntity.toDto() ).collect(Collectors.toList());
    }


    // 입력받은 평가대상자의 eno로 현재 분기 업무평가가 이미 등록되었는지 유효성검사
    public boolean checkEvaluation( int eno ){
        // 1. 현재 날짜 기준 반기 기간 구하기
            // 1. 현재 날짜
            LocalDate currentDate = LocalDate.now();
            int year = currentDate.getYear();
            int month = currentDate.getMonthValue();
            log.info("현재 연도 : "+ year);
            log.info("현재 월 : "+ month);
            // 2. 시작날짜, 끝날짜
            String startdate = "";
            String enddate = "";
            if ( month < 7 ){
                startdate = year+"-01-01";
                enddate = year+"-07-01";
            }else{
                startdate = year+"-07-01";
                enddate = (year+1)+"-01-01";
            }
            log.info("업무평가 조회용 시작날짜 : "+startdate);
            log.info("업무평가 조회용 끝날짜 : "+enddate);

        // 현재 기간에 해당 eno 업무평가 조회
        List<EvaluationEntity> evaluationEntityList =  evaluationRepository.existsByEnoAndDate(eno,startdate,enddate);
        // 업무평가가 이미 있으면 false 없으면 true => true는 평가등록가능
        if ( evaluationEntityList.size()>0){ return false;}
        else{ return true; }
    }
}
