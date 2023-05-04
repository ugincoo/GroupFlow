package groupflow.service;

import groupflow.domain.department.DepartmentChangeEntity;
import groupflow.domain.department.DepartmentChangeEntityRepository;
import groupflow.domain.department.DepartmentEntity;
import groupflow.domain.department.DepartmentRepository;
import groupflow.domain.employee.EmployeeDto;
import groupflow.domain.employee.EmployeeEntity;
import groupflow.domain.employee.EmployeeRepository;
import groupflow.domain.position.PositionChangeEntity;
import groupflow.domain.position.PositionChangeEntityRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

@Service
@Slf4j
public class EmployeeService {

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    DepartmentChangeEntityRepository departmentChangeRepository;

    @Autowired
    DepartmentRepository departmentRepository;

    @Autowired
    PositionChangeEntityRepository positionChangeRepository;

    /*
    01: 경영지원팀
    02: 인사팀
    03: 영업팀
    04: 마케팅팀
    05: 기술개발팀
    06: 디자인팀
    07: 생산팀
    08: 자재팀
    09: IT팀
    10: 회계팀
    */

    // 랜덤 사번 생성
    // 사번만들때 필요한 정보 : 입사일() ,  DB에서 올해 입사한 마지막 사번 가져오기
    public String generateEmployeeID( String hiredate  ) {

        //******************************** 입사연도 구하기 "2023-05-03" --> "23"
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        // 입력받은 입사일을 yy-MM-dd 형식으로 포맷
        try { date = dateFormat.parse(hiredate); } catch (ParseException e) {  throw new RuntimeException(e); }

        // 연도추출용
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int year = cal.get(Calendar.YEAR) % 100; // 연도 정보만 추출
        log.info("year : " + year);

        //******************************** DB에서 올해 입사한 마지막 사번 가져오기 "2301001" --> "001"

        LocalDate today = LocalDate.now(); // 현재 날짜

        // 올해 시작일
        LocalDate startOfYear = today.withDayOfYear(1);
        String startOfYearStr = startOfYear.toString(); // yyyy-MM-dd 형식의 문자열

        // 올해 마지막일
        LocalDate endOfYear = today.withDayOfYear(today.lengthOfYear());
        String endOfYearStr = endOfYear.toString(); // yyyy-MM-dd 형식의 문자열

        System.out.println("올해 시작일: " + startOfYearStr);
        System.out.println("올해 마지막일: " + endOfYearStr);
  
        // 올해 입사한 마지막 사원의 사번구하기
        Optional<EmployeeEntity> optionalEmployeeEntity = employeeRepository.findLastEmployeeIdByHireDateBetween( startOfYearStr , endOfYearStr );

        String nextEmpNum; // 전역변수 사번마지막 3자리

        if ( !optionalEmployeeEntity.isPresent() ){ nextEmpNum = "001"; } // 올해 입사한 사람이 없으면 "001"
        else {
            String eno = Integer.toString(optionalEmployeeEntity.get().getEno());
            String lastThreeDigits = eno.substring(eno.length() - 3);
            // 구한 사번에서 1더하기
            int num = Integer.parseInt(lastThreeDigits) + 1;
            nextEmpNum = String.format("%03d", num);
            System.out.println(nextEmpNum); // 출력: 002
        }

        //******************************** 사번만들기

        return String.format("%02d", year) + nextEmpNum;

    }


    // 신규 직원 등록
    @Transactional
    public byte registerNewEmployee( EmployeeDto employeeDto , int dno , String pno ) {
        // 사번만들기 함수
        int id = Integer.parseInt( generateEmployeeID( employeeDto.getHiredate() ) );
        Optional<EmployeeEntity> optionalEmployeeEntity = employeeRepository.findById(id);
        if (optionalEmployeeEntity.isPresent()){ return 1; } // 사번이 이미 존재함

        // employeeentity DB저장
        EmployeeEntity employeeEntity = employeeDto.toEntity();
        employeeRepository.save( employeeEntity );
        

        // 부서 ----------------------------------------------------------------------------------------

        // departmentChangeentity 객체만들어서 DB저장
        DepartmentChangeEntity departmentChangeEntity = DepartmentChangeEntity.builder().dcendreason("입사").build();
        departmentChangeRepository.save(departmentChangeEntity);
        if ( !(departmentChangeEntity.getDcno() > 0) ){ return 3; } // departmentChangeEntity 저장안됨

        // departmentChangeentity 양방향
        departmentChangeEntity.setEmployeeEntity( employeeEntity );
        employeeEntity.getDepartmentChangeEntityList().add(departmentChangeEntity);

        // departmentEntity
        Optional<DepartmentEntity> optionalDepartmentEntity = departmentRepository.findById(dno);
        if (optionalDepartmentEntity.isPresent()){
            // departmentEntity DB에서 꺼냄
            DepartmentEntity departmentEntity = optionalDepartmentEntity.get();
            // departmentEntity 양방향
            departmentChangeEntity.setDepartmentEntity(departmentEntity);
            departmentEntity.getDepartmentChangeEntityList().add(departmentChangeEntity);
        }


        // 직급 ----------------------------------------------------------------------------------------
        // positionChangeEntity 만들기 ( 적용날짜만 저장, 적용날짜 == 입사일 )
        PositionChangeEntity positionChangeEntity = PositionChangeEntity.builder().pcdate(employeeEntity.getHiredate()).build();
        positionChangeRepository.save(positionChangeEntity);
        if ( !(positionChangeEntity.getPcno() > 0) ){ return 4; } // positionChangeEntity 저장안됨

        //



    }
}
