package groupflow.service;
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
import groupflow.domain.position.PositionEntity;
import groupflow.domain.position.PositionEntityRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.transaction.Transactional;
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

    //1. 연차신청
    @Transactional
    public byte post(LeaveRequestDto dto){
        log.info("postdayoff dto : " + dto);
       //1. 로그인 된 회원의 엔티티 찾기
        // 인증된  인증 정보 찾기
        Object o = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(o.equals("anonymousUser")){
            return 1;
        }
        //형변환
        EmployeeDto loginDto = (EmployeeDto)o;
        // 로그인 한 사원 찾기
        EmployeeEntity employeeEntity = employeeRepository.findByEno(loginDto.getEno()); // 수정 해야 함
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

        return 3;
    }

    // 연차 출력
    public List<LeaveRequestDto> get(){
        log.info("get Service : ");
        // 모든 엔티티 호출
        List<LeaveRequestEntity> entityList = leaveRequestRepository.findAll();
        //1. 로그인 된 회원의 엔티티 찾기
        // 인증된  인증 정보 찾기
        Object o = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(o.equals("anonymousUser")){
            // 모든 엔티티를 dto로변환
            List<LeaveRequestDto> dtoList =
                entityList.stream().map( m -> m.toDto()).collect(Collectors.toList());

        }
        //형변환
        EmployeeDto loginDto = (EmployeeDto)o;
        // 로그인 한 사원 찾기
        EmployeeEntity employeeEntity = employeeRepository.findByEno(loginDto.getEno()); // 수정 해야 함
        log.info("employeeEntity" + employeeEntity);

        // 모든 엔티티를 dto로변환
        List<LeaveRequestDto> dtoList =
                entityList.stream().map( m-> m.toDto()).collect(Collectors.toList());

        return dtoList;
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