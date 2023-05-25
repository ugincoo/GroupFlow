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
import groupflow.domain.position.PositionEntity;
import groupflow.domain.position.PositionEntityRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class EmployeeService {

    @Autowired    private EmployeeRepository employeeRepository;

    @Autowired    private DepartmentChangeEntityRepository departmentChangeRepository;

    @Autowired    private DepartmentRepository departmentRepository;

    @Autowired    private PositionChangeEntityRepository positionChangeRepository;

    @Autowired    private PositionEntityRepository positionEntityRepository;

    @Autowired    private FileService fileService;
    @Autowired private LoginService loginService;
    @Autowired private DepartmentService departmentService;

    // 로그인한 사람이 부장일 경우 부서내 직원리스트 반환
    public List<EmployeeDto> getEmployeesByDepartmentWithoutManager(){
        log.info("getEmployeesByDepartmentWithoutManager실행");
        // 1. 로그인세션
        EmployeeDto employeeDto = loginService.loginInfo();
        if( employeeDto == null ){ return null;}
        // 2. DB에 저장된 부장 dno로 로그인세션이 부장인지 확인 / findManagerDno() : 부장직급 dno
        if ( employeeDto.getPno() != findManagerPno() ){ return null; }

        // 3. 부서내 직원리스트 DB에서 꺼내기(리스트에서 부장제외)
        List<EmployeeEntity> employeeEntityList = employeeRepository.getEmployeesByDepartmentWithoutManager( employeeDto.getDno() );
        log.info("부서내 직원리트스 employeeEntityList: " + employeeEntityList);
        // 4. 직원리스트를 돌리면서 직원마다 eno,ename,dno,dname,pno,pname있는 employeeDto반환하는 employeeInfo실행해서 list에 담아서 반환
        //return employeeEntityList.stream().map(employeeEntity -> employeeEntity.toDto() ).collect(Collectors.toList());
        List<EmployeeDto> employeeDtoList = new ArrayList<>();
        for (EmployeeEntity employeeEntity : employeeEntityList){
            employeeDtoList.add( employeeInfo(employeeEntity.getEno()) );
        }
        return employeeDtoList;
    }

    // 부장 dno 구하기
    public int findManagerPno(){
        List<PositionEntity> positionEntityList = positionEntityRepository.findManagerDno();
        log.info("positionEntityList : " + positionEntityList);
        log.info("positionEntityList.size() : " + positionEntityList.size());
        if ( positionEntityList.size() < 0 ){ return 0; } // 부장 직급이 존재하지않음 -> 부장을 p
        return positionEntityList.get(0).getPno();
    }

    // 부서에 이미 부장이 존재하는지 확인
    public byte managerExist( int dno , int pno ){
        // 입력한 pno가 부장인지 확인
            /* 위에 함수로 뺌
            List<PositionEntity> positionEntityList = positionEntityRepository.findManagerDno();
            log.info("positionEntityList : " + positionEntityList);
            log.info("positionEntityList.size() : " + positionEntityList.size());
            if ( positionEntityList.size() < 0 ){ return 1; } // 부장 직급이 존재하지않음 -> 부장을 position테이블에 추가해야함.
            */
        // 부장의 pno구하는 함수
        int managerPno = findManagerPno();
        if ( managerPno == 0 ){ return 1; } // 부장 직급이 존재하지않음 -> 부장을 position테이블에 추가해야함.
        // 입력값 pno가 부장이 아니면 return 3 => 직원등록 진행
        else if ( managerPno != pno ){ return 3; }

        // 입력값 pno가 부장일경우 입력한 dno,pno로 존재하는지 확인하는 쿼리문
        else if( managerPno == pno ){
             Optional<EmployeeEntity> optionalEmployeeEntity = employeeRepository.findByDnoAndPno( dno , pno );
             if ( optionalEmployeeEntity.isPresent() ){
                 if ( optionalEmployeeEntity.get().getEno() > 0 ){ return 2;} // 해당팀에 이미 부장이 존재
             }else{
                 return 3; // 해당팀에 부장이 존재하지않음 => 부장으로 직원등록 가능 => 직원등록 진행
             }
        }
        return 4; //  예상치못한 상황
    }

     
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

    /* 테스터
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
    public int registerNewEmployee(MultipartFile multipartFile , EmployeeDto employeeDto) {
        log.info("s registerNewEmployee 실행 employeeDto : " + employeeDto);

        // 직원등록시 부장을 선택했는지, 선택한 부서에 부장이 존재하는지
        /*
            1=부장 직급이 존재하지않음 -> 부장을 position테이블에 추가해야함.
            2=해당팀에 이미 부장이 존재
            3=부장을 선택하지않았거나, 해당부서는 부장이 없음 => 직원등록가능
            4=그외의 예상치못한 상황
        */
        byte managerExistResult = managerExist( employeeDto.getDno() , employeeDto.getPno() );
        log.info("s managerExistResult : " + managerExistResult);
        if ( managerExistResult == 2 ){ return 1;} // 해당부서는 부장이 이미존재
        else if ( managerExistResult != 3 ){ return 2; } // 직원등록이 어려운상황 => 관리자문의

        // 사번만들기 함수
        int eno = Integer.parseInt(generateEmployeeID(employeeDto.getHiredate()));
        log.info("생성한 사번 : " + eno);
        Optional<EmployeeEntity> optionalEmployeeEntity = employeeRepository.findById(eno);
        if (optionalEmployeeEntity.isPresent()) {
            return 3; // 사번이 이미 존재함
        }
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


        // 리포지토리에 엔티티 저장되었는지 확인
        Optional<EmployeeEntity> optionalEmployeeEntity2 = employeeRepository.findById(employeeEntity.getEno());
        log.info("optionalEmployeeEntity2.isPresent() :" + optionalEmployeeEntity2.isPresent());
        if (!optionalEmployeeEntity2.isPresent()) {
            return 4; // 사원 생성이 안되었음
        }
        employeeEntity = optionalEmployeeEntity2.get();
        log.info("employeeEntity :" + optionalEmployeeEntity2.get());

        log.info("사원 테이블 생성 완료 ");

        // 사원 이미지 서버폴더에 저장 , 사원사진파일명 DB employeeEntity에 ephoto에 대입
        String ephoto = fileService.fileupload(multipartFile);
        log.info("ephoto :" + ephoto);
        employeeEntity.setEphoto(ephoto);

        if ( employeeDto.getDno() > 0 ) { // 이사,상무,사장은 부서 없음
            // 부서이동 ----------------------------------------------------------------------------------------
            // departmentChangeentity 객체만들어서 DB저장
            DepartmentChangeEntity departmentChangeEntity = DepartmentChangeEntity.builder()
                    .dcstartdate(employeeEntity.getHiredate())
                    .dcstartreason("입사")
                    .build();
            departmentChangeRepository.save(departmentChangeEntity);
            if (!(departmentChangeEntity.getDcno() > 0)) {
                return 5; // departmentChangeEntity 저장안됨
            }
            log.info("departmentChangeEntity.getDcno() :" + departmentChangeEntity.getDcno());
            log.info("부서이동 테이블 생성 완료 ");
            log.info("departmentChangeEntity :" + departmentChangeEntity);


            // departmentEntity 부서entity 구하기
            log.info("employeeDto.getDno()"+employeeDto.getDno());
            Optional<DepartmentEntity> optionalDepartmentEntity = departmentRepository.findById(employeeDto.getDno());
            log.info("optionalDepartmentEntity :"+optionalDepartmentEntity);
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
                .pcstartreason("입사")
                .build();
        positionChangeRepository.save(positionChangeEntity);
        if (!(positionChangeEntity.getPcno() > 0)) {
            return 6; // positionChangeEntity 저장안됨
        }
        log.info("직급변경 테이블 생성 완료 ");
        log.info("positionChangeEntity : " + positionChangeEntity);

        // positionEntity 찾기
        Optional<PositionEntity> optionalPositionEntity = positionEntityRepository.findById(employeeDto.getPno());
        if (optionalPositionEntity.isPresent()) {
            // positionEntity 꺼냄
            PositionEntity positionEntity = optionalPositionEntity.get();

            //양방향 positionEntity <--> positionChangeEntity
            // 직급이동이력에 직급entity 저장
            positionChangeEntity.setPositionEntity(positionEntity);
            // 직급entity에 직급이동이력 저장
            positionEntity.getPositionChangeEntityList().add(positionChangeEntity);
            log.info("직급테이블 직급변경테이블 양방향 완료 ");

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



        return employeeEntity.getEno(); // 저장완료


    }



    // 사원정보 출력위해서 부서번호,부서명,직급번호,직급명
    public EmployeeDto employeeInfo( int eno ){
        log.info("employeeInfo eno : "+ eno);
        // 입력받은 사번으로 employeeEntity찾기
        Optional<EmployeeEntity> optionalEmployeeEntity = employeeRepository.findById(eno);
        log.info("optionalEmployeeEntity : " + optionalEmployeeEntity);
        if(optionalEmployeeEntity.isPresent()){
            EmployeeEntity employeeEntity = optionalEmployeeEntity.get();
            List<DepartmentChangeEntity> departmentChangeEntityList = employeeEntity.getDepartmentChangeEntityList();
            List<PositionChangeEntity> positionChangeEntityList = employeeEntity.getPositionChangeEntityList();

            PositionEntity positionEntity = positionChangeEntityList.get(positionChangeEntityList.size()-1).getPositionEntity();
            DepartmentEntity departmentEntity = departmentChangeEntityList.get(departmentChangeEntityList.size()-1).getDepartmentEntity();

            return EmployeeDto.builder()
                    .eno(employeeEntity.getEno())
                    .ename(employeeEntity.getEname())
                    .pno(positionEntity.getPno())
                    .pname(positionEntity.getPname())
                    .dno(departmentEntity.getDno())
                    .dname(departmentEntity.getDname())
                    .hiredate(employeeEntity.getHiredate().format( DateTimeFormatter.ofPattern( "yy-MM-dd")))
                    .build();
        }
        return null;
    }
}