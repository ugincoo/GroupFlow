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
import org.springframework.web.bind.annotation.RequestParam;

import javax.transaction.Transactional;
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

        return 3; //성공시 반환
    }

    // 개인 연차 출력
    public List<LeaveRequestDto> myget(){
        log.info("get Service");
        // 1. 로그인 인증세션 -->dto 형변환
        EmployeeDto employeeDto = (EmployeeDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        // 2. 회원 엔티티티 찾기
        EmployeeEntity entity = employeeRepository.findByEno(employeeDto.getEno());
        // 3. 개인 연차 정보를 담을 리스트 생성
        List<LeaveRequestDto> dtoList = new ArrayList<>() ;
        // 4. 리스트에 담기
        entity.getLeaveRequestEntityList().forEach( (e) -> {
            dtoList.add(e.toDto());
        } );
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