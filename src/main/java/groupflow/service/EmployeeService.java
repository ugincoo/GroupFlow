package groupflow.service;

import com.sun.xml.bind.api.Bridge;
import groupflow.domain.department.*;
import groupflow.domain.employee.EmployeeDto;
import groupflow.domain.employee.EmployeeEntity;
import groupflow.domain.employee.EmployeeRepository;
import groupflow.domain.position.PositionChangeEntity;
import groupflow.domain.position.PositionChangeEntityRepository;
import groupflow.domain.position.PositionEntity;
import groupflow.domain.position.PositionEntityRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

@Service
@Slf4j
public class EmployeeService {

    int eno = 0;
    String ename = "홍길동";


    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    DepartmentChangeEntityRepository departmentChangeRepository;

    @Autowired
    DepartmentRepository departmentRepository;

    @Autowired
    PositionChangeEntityRepository positionChangeRepository;

    @Autowired
    PositionEntityRepository positionEntityRepository;

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
    public String generateEmployeeID(String hiredate) {

        //******************************** 입사연도 구하기 "2023-05-03" --> "23"
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        // 입력받은 입사일을 yy-MM-dd 형식으로 포맷
        try {
            date = dateFormat.parse(hiredate);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        // 연도추출용
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int year = cal.get(Calendar.YEAR); // 연도 정보만 추출 // % 100
        log.info("year : " + year);

        //******************************** DB에서 올해 입사한 마지막 사번 가져오기 "2301001" --> "001"

        LocalDate today = LocalDate.now(); // 현재 날짜


        // 입력한 year 시작일
        LocalDate startOfYear = LocalDate.of(year, Month.JANUARY, 1);
        String startOfYearStr = startOfYear.toString(); // yyyy-MM-dd 형식의 문자열

        // 입력한 year 마지막일
        LocalDate endOfYear = startOfYear.withDayOfYear(startOfYear.lengthOfYear());
        String endOfYearStr = endOfYear.toString(); // yyyy-MM-dd 형식의 문자열

        System.out.println("입력한 year 시작일: " + startOfYearStr);
        System.out.println("입력한 year 마지막일: " + endOfYearStr);

        // 입력한 year 입사한 마지막 사원의 사번구하기
        Optional<EmployeeEntity> optionalEmployeeEntity = employeeRepository.findLastEmployeeIdByHireDateBetween(startOfYearStr, endOfYearStr);
        log.info("마지막사원구하기 optionalEmployeeEntity : " + optionalEmployeeEntity);
        String nextEmpNum; // 전역변수 사번마지막 3자리

        if (!optionalEmployeeEntity.isPresent()) {
            nextEmpNum = "001";
        } // 입력한 year 입사한 사람이 없으면 "001"
        else {
            String eno = Integer.toString(optionalEmployeeEntity.get().getEno());
            String lastThreeDigits = eno.substring(eno.length() - 3);
            log.info("마지막3개숫자 : " + lastThreeDigits);
            // 구한 사번에서 1더하기
            int num = Integer.parseInt(lastThreeDigits) + 1;
            log.info("num : " + num);
            nextEmpNum = String.format("%03d", num);


        }

        //******************************** 사번만들기
        log.info("nextEmpNum : " + nextEmpNum);
        return String.format("%02d", year) + nextEmpNum;

    }


    // 신규 직원 등록 [ 필요 : employeeDto ( eemail , ename , ephone ,esocialno , hiredate , dno , pno ) ]

    /*
    http://localhost:8080/employee
    {
        "ename" : "홍길동" ,
            "esocialno" : "123456-1234567" ,
            "eemail" : "asd@naver.com" ,
            "ephone" : "010-0000-0000" ,
            "hiredate" : "2023-05-06" ,
            "dno" : 9 ,
            "pno" :1
    }
    */
    @Transactional
    public byte registerNewEmployee(EmployeeDto employeeDto) {
        log.info("s registerNewEmployee 실행 employeeDto : " + employeeDto);

        // 사번만들기 함수
        int eno = Integer.parseInt(generateEmployeeID(employeeDto.getHiredate()));
        log.info("생성한 사번 : " + eno);
        Optional<EmployeeEntity> optionalEmployeeEntity = employeeRepository.findById(eno);
        if (optionalEmployeeEntity.isPresent()) {
            return 1;
        } // 사번이 이미 존재함
        log.info("optionalEmployeeEntity.isPresent() : " + optionalEmployeeEntity.isPresent());
        log.info("사번중복확인");

        // 사번 dto에 대입
        employeeDto.setEno(eno);
        // 사원 dto ==> 사원 entity
        EmployeeEntity employeeEntity = employeeDto.toEntity();
        log.info("employeeDto -> Entity : " + employeeEntity);
        employeeEntity.setPositionChangeEntityList(new ArrayList<>());
        employeeEntity.setAttendanceEntityList(new ArrayList<>());
        employeeEntity.setLeaveRequestEntityList(new ArrayList<>());
        employeeEntity.setDepartmentChangeEntityList(new ArrayList<>());

        // employeeentity DB저장
        employeeRepository.save(employeeEntity);

        // 모든 사원 조회
        List<EmployeeEntity> employeeEntityList = employeeRepository.findAll();
        log.info("employeeEntityList : " + employeeEntityList);

        // 리포지토리에 엔티티 저장되었는지 확인
        Optional<EmployeeEntity> optionalEmployeeEntity2 = employeeRepository.findById(employeeEntity.getEno());
        log.info("optionalEmployeeEntity2.isPresent() :" + optionalEmployeeEntity2.isPresent());
        if (!optionalEmployeeEntity2.isPresent()) {
            return 2;
        } // 사원 생성이 안되었음
        employeeEntity = optionalEmployeeEntity2.get();
        log.info("employeeEntity :" + optionalEmployeeEntity2.get());

        log.info("사원 테이블 생성 완료 ");

        if ( employeeDto.getDno() > 0 ) { // 이사,상무,사장은 부서 없음
            // 부서이동 ----------------------------------------------------------------------------------------
            // departmentChangeentity 객체만들어서 DB저장
            DepartmentChangeEntity departmentChangeEntity = DepartmentChangeEntity.builder()
                    .dcstartdate(employeeEntity.getHiredate())
                    .dcendreason("입사")
                    .build();
            departmentChangeRepository.save(departmentChangeEntity);
            if (!(departmentChangeEntity.getDcno() > 0)) {
                return 3;
            } // departmentChangeEntity 저장안됨
            log.info("departmentChangeEntity.getDcno() :" + departmentChangeEntity.getDcno());
            log.info("부서이동 테이블 생성 완료 ");
            log.info("departmentChangeEntity :" + departmentChangeEntity);


            // departmentEntity 부서entity 구하기
            Optional<DepartmentEntity> optionalDepartmentEntity = departmentRepository.findById(employeeDto.getDno());
            if (optionalDepartmentEntity.isPresent()) {
                // departmentEntity DB에서 꺼냄
                DepartmentEntity departmentEntity = optionalDepartmentEntity.get();
                log.info("departmentEntity: " + departmentEntity);

                // 양방향 departmentEntity <--> departmentChangeEntity
                // 부서이동이력에 부서entity 저장
                departmentChangeEntity.setDepartmentEntity(departmentEntity);
                // 부서에entity 부서이동이력 저장
                departmentEntity.getDepartmentChangeEntityList().add(departmentChangeEntity);
                log.info("부서테이블 , 부서이동 테이블 양방향 완료 ");
                log.info("departmentChangeEntity :" + departmentChangeEntity);
                log.info("departmentEntity :" + departmentEntity);


            }

            // departmentChangeentity <--> employeeEntity 양방향
            // 부서이동이력에 사원entity 저장
            log.info("부서이동,사원테이블 양방향 전 entity 조회 ");
            log.info("departmentChangeEntity :  " + departmentChangeEntity);
            log.info("departmentChangeEntity.getEmployeeEntity() :  " + departmentChangeEntity.getEmployeeEntity());
            log.info("employeeEntity :  " + employeeEntity);
            log.info("employeeEntity.getDepartmentChangeEntityList() :  " + employeeEntity.getDepartmentChangeEntityList());
            departmentChangeEntity.setEmployeeEntity(employeeEntity);
            // 사원entity에 부서이동이력 저장
            employeeEntity.getDepartmentChangeEntityList().add(departmentChangeEntity);
            log.info("부서이동 테이블, 사원테이블 양방향 완료 ");
            log.info("departmentChangeEntity : " + departmentChangeEntity);
            log.info("employeeEntity : " + employeeEntity);
        } // if 부서가 있을 경우 end

        // 직급 ----------------------------------------------------------------------------------------
        // positionChangeEntity 만들기 ( 적용날짜만 저장, 적용날짜 == 입사일 )
        PositionChangeEntity positionChangeEntity = PositionChangeEntity.builder()
                .pcdate(employeeEntity.getHiredate())
                .build();
        positionChangeRepository.save(positionChangeEntity);
        if (!(positionChangeEntity.getPcno() > 0)) {
            return 4;
        } // positionChangeEntity 저장안됨
        log.info("직급변경 테이블 생성 완료 ");
        log.info("positionChangeEntity : " + positionChangeEntity);

        //


        return 0;
    }

    //전직원 출력[관리자입장]
    @Transactional
    public List<EmployeeDto> allEmplyee(int dno,int dcendreason){
        System.out.println("dno1"+dno);System.out.println("dcendreason1"+dcendreason);
        List<EmployeeEntity> entityList=null;
        if(dno==0&&dcendreason==0){ //전체직원(!dcendreason 사실 =0 은 필요없다 )
            entityList=employeeRepository.findAll();
        }if(dno!=0){    //부서별 모든직원
            entityList=employeeRepository.findemployeebydno(dno);
        }if(dcendreason!=0&&dno!=0){    //부서별+입/퇴사
            entityList=employeeRepository.findemployeebydcendreason_dno(dcendreason,dno);
        }if(dcendreason!=0&&dno==0){    //전체출력+입/퇴사
            entityList=employeeRepository.findemployeebydcendreason(dcendreason);
        }
        List<EmployeeDto> dtoList=new ArrayList<>();

        entityList.forEach((e)->{
         //   EmployeeDto employeeDto=new EmployeeDto(); 부서를 넣고싶다..
         // employeeDto.setDname( e.getDepartmentChangeEntityList().get(0).getDepartmentEntity().getDname());
            dtoList.add(e.toDto());
        });
        // positionEntity 찾기
        Optional<PositionEntity> optionalPositionEntity = positionEntityRepository.findById(employeeDto.getPno());
        if (optionalPositionEntity.isPresent()) {
            // positionEntity 꺼냄
            PositionEntity positionEntity = optionalPositionEntity.get();

        return dtoList;
    }
            //양방향 positionEntity <--> positionChangeEntity
            // 직급이동이력에 직급entity 저장
            positionChangeEntity.setPositionEntity(positionEntity);
            // 직급entity에 직급이동이력 저장
            positionEntity.getPositionChangeEntityList().add(positionChangeEntity);
            log.info("직급테이블 직급변경테이블 양방향 완료 ");

    //부서 출력[직원입장]
    public List<DepartmentDto> getDepartment(){ //부서셀렉트[관리자입장]
        System.out.println("getDepartment:");
        List<DepartmentEntity> entityList=departmentRepository.findAll();
        List<DepartmentDto> dtoList= new ArrayList<>();
        entityList.forEach( (e)->{
            dtoList.add(e.toDto());
        });
        return dtoList;
    }

}
            //양방향 positionChangeEntity <-> employeeEntity
            positionChangeEntity.setEmployeeEntity(employeeEntity);
            employeeEntity.getPositionChangeEntityList().add(positionChangeEntity);
        }

        // 모든 엔티티 조회
        //log.info("employeeEntity : " + employeeEntity);
        //log.info("employeeEntity.getPositionChangeEntityList() : " + employeeEntity.getPositionChangeEntityList());
        //log.info("employeeEntity.getDepartmentChangeEntityList() : " + employeeEntity.getDepartmentChangeEntityList());
       //log.info("positionChangeEntity : "+positionChangeEntity);
        //log.info("positionChangeEntity.getPositionEntity() : "+positionChangeEntity.getPositionEntity());
        //log.info("positionChangeEntity.getEmployeeEntity() : "+positionChangeEntity.getEmployeeEntity());
        //log.info("departmentChangeEntity : "+departmentChangeEntity);
        //log.info("departmentChangeEntity.getDepartmentEntity() : "+departmentChangeEntity.getDepartmentEntity());
        //log.info("departmentChangeEntity.getEmployeeEntity() : "+departmentChangeEntity.getEmployeeEntity());



        return 5; // 저장완료


    }


    // 로그인
    public EmployeeDto eLogin( EmployeeDto employeeDto ){
        log.info("s eLogin eno : "+employeeDto.getEno() +" ename : "+employeeDto.getEname());
        return null;
    }
}