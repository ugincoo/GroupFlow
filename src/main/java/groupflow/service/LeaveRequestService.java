package groupflow.service;
import groupflow.domain.attendance.AttendanceEntity;
import groupflow.domain.department.DepartmentChangeEntity;
import groupflow.domain.department.DepartmentChangeEntityRepository;
import groupflow.domain.department.DepartmentEntity;
import groupflow.domain.department.DepartmentRepository;
import groupflow.domain.employee.EmployeeDto;
import groupflow.domain.employee.EmployeeEntity;
import groupflow.domain.employee.EmployeeRepository;
import groupflow.domain.leaverequest.LeaveRequestDto;
import groupflow.domain.leaverequest.LeaveRequestEntity;
import groupflow.domain.leaverequest.LeaveRequestRepository;
import groupflow.domain.position.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.transaction.Transactional;
import java.awt.print.Pageable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class LeaveRequestService {
    @Autowired
    LeaveRequestRepository leaveRequestRepository;
    @Autowired
    DepartmentChangeEntityRepository departmentChangeEntityRepository;
    @Autowired
    EmployeeRepository employeeRepository;
    @Autowired
    PositionEntityRepository positionEntityRepository;

    //1. 연차신청
    @Transactional
    public byte post(LeaveRequestDto dto){
        log.info("postdayoff dto : " + dto);
       //1. 로그인 된 회원의 엔티티 찾기
        // 인증된  인증 정보 찾기
        Object o = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(o.equals("anonymousUser")){
            return 1; // 로그인 세션 없을때 반환
        }
        //형변환
        EmployeeDto loginDto = (EmployeeDto)o;
        // 로그인 한 직원 찾기
        EmployeeEntity employeeEntity = employeeRepository.findByEno(loginDto.getEno());
        log.info("employeeEntity" + employeeEntity);
        //***직원 부서 가져오기
        DepartmentEntity departmentEntity = employeeEntity.getDepartmentChangeEntityList().get(
                employeeEntity.getDepartmentChangeEntityList().size()-1 //마지막 인데스 가져오기
        ).getDepartmentEntity();

        //2. 연차 신청하기
        LeaveRequestEntity leaveRequestEntity = leaveRequestRepository.save(dto.toEntity());

        if (leaveRequestEntity.getLno() < 1){
            return 2; 
        }
        //4. 연차 - 부서 양방향관계[부서안에 연차 주소 대입]
        departmentEntity.getLeaveRequestEntityList().add(leaveRequestEntity);
        leaveRequestEntity.setDepartmentEntity(departmentEntity);
        //5. 연차 - 로그인 회원 양방향관계
        employeeEntity.getLeaveRequestEntityList().add(leaveRequestEntity);
        leaveRequestEntity.setEmployeeEntity(employeeEntity);

        return 3; //성공시 반환
    }

    // 2. 개인 연차 출력
    @Transactional
    public List<LeaveRequestDto> myget(){
        log.info("get Service");
        // 1. 로그인 인증세션 -->dto 형변환
        EmployeeDto employeeDto = (EmployeeDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        // 2. 로그인 한 직원 정보 가져오기
        EmployeeEntity entity = employeeRepository.findByEno(employeeDto.getEno());
        
        List<PositionEntity> yearno =  positionEntityRepository.findByYearno( employeeDto.getEno()  );
        log.info("연차개수 : " + yearno.get(0).getYearno());
        // 3. 개인 연차 정보를 담을 리스트 생성
        List<LeaveRequestDto> dtoList = new ArrayList<>() ;
        // 4. 리스트에 담기
            entity.getLeaveRequestEntityList().forEach( (e) -> {
                LeaveRequestDto dto = e.toDto();
                dto.setYearno(yearno.get(0).getYearno());
                dtoList.add(dto);

                log.info("개인연차내역 : " + dtoList);
            } );

        return dtoList;
    }

    // 3-1. 부서 연차 출력
    public List<LeaveRequestDto> pget(int dno) {
        log.info("get Service");
        // 1. 로그인 인증세션 -->dto 형변환
        EmployeeDto employeeDto = (EmployeeDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        // 2. 로그인 한 직원 정보 가져오기
        EmployeeEntity entity = employeeRepository.findByEno(employeeDto.getEno());

        // 3. 부서 별 직원 내역 가져오기
        List<LeaveRequestEntity> entityList = leaveRequestRepository.findAll();
        log.info("entityList : " + entityList);

        // 4. 개인 연차 정보를 담을 리스트 생성
        List<LeaveRequestDto> dtoList = new ArrayList<>();
        entityList.forEach( (e) -> {

            dtoList.add(e.toDto());
        } );
        return dtoList;
    }
    // 3-2. 전체 출력 [ 경영지원팀 ]
    public List<LeaveRequestDto> adminget(){
        log.info("get Service");
        // 1. 로그인 인증세션 -->dto 형변환
        EmployeeDto employeeDto = (EmployeeDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        // 2. 로그인 한 직원 정보 가져오기
        EmployeeEntity entity = employeeRepository.findByEno(employeeDto.getEno());
        // 3. 부서 별 직원 내역 가져오기
        List<LeaveRequestEntity> entityList = leaveRequestRepository.findAll();
        log.info("entityList : " + entityList);

        // 4. 개인 연차 정보를 담을 리스트 생성
        List<LeaveRequestDto> dtoList = entityList.stream().map( o -> o.toDto()).collect(Collectors.toList());
        return dtoList;
    }



    // 4. 결재 승인
    @Transactional
    public boolean pok( LeaveRequestDto dto ){
        log.info("pok :" + dto );
        //포장지에서 찾기
        Optional<LeaveRequestEntity> optionalLREntity = leaveRequestRepository.findById(dto.getLno());
        log.info("lno : "+ dto.getLno());
        if( optionalLREntity.isPresent() ){
            LeaveRequestEntity requestEntity = optionalLREntity.get();
            requestEntity.setApprovaldate(dto.getApprovaldate());
            requestEntity.setApprovalstate(dto.getApprovalstate());
            return true;
        }

        return false;
    }

    // 5. 연차신청내역 삭제
    @Transactional
    public boolean deleteLeaveRequest( int lno ){
       Optional<LeaveRequestEntity> optionalLeaveRequestEntity = leaveRequestRepository.findById(lno);
       if (optionalLeaveRequestEntity.isPresent()){
           leaveRequestRepository.delete( optionalLeaveRequestEntity.get() );
           return true;
       }
       return false;
    }

}
/*
    Repository findBy 만드는 방법
    .findById(pk값) : 해당하는 pk 값이 검색 후 존재하면 레코드[엔티티] 반환
    .findAll() : 모든 레코드 [엔티티] 반환
    .save(엔티티) : 해당 엔티티를 DB레코드에서 save
    .delete(엔티티) : 해당 엔티티를 DB레코드에서 delete
    @Transactiinal --> setter 메소드 이용 :  수정
    -------- 그외 추가 메소드 만들기
    검색
        select * from member where memail = ?;
       -> findby필드명(인수)
*/