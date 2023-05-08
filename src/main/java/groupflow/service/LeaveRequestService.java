package groupflow.service;
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
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;

@Service
@Slf4j
public class LeaveRequestService {
    @Autowired
    LeaveRequestRepository leaveRequestRepository;

    @Autowired
    DepartmentRepository departmentRepository;
    @Autowired
    EmployeeRepository employeeRepository;



    //1. 연차신청
    public  byte postdayoff(LeaveRequestDto dto){
        log.info("postdayoff dto : " + dto);
        // 1. 부서 앤티티 찾기 [ 연차 게시물 안에 부서 엔티티객체 대입 하기 위해 ]
        Optional<DepartmentEntity> departmentEntityOptional = departmentRepository.findById(dto.getPno());
        if( !departmentEntityOptional.isPresent()) {
            return 1; // 만약에 선택 된 카테고리가 없으면 return
        }
        DepartmentEntity DepartmentEntity = departmentEntityOptional.get();    // 3. 카테고리 엔티티 추출

        //2. 로그인 된 회원의 엔티티 찾기
            // 아직 시큐리티 미완성
        // 인증된  인증 정보 찾기
        Object o = SecurityContextHolder.getContext().getAuthentication.getPrincipal();
        if(o.equals("anonymousUser")){
            return 2;
        }
        //형변환
        EmployeeDto loginDto = (EmployeeDto)o;
        // 회원엔티티 찾기
        EmployeeEntity employeeEntity = employeeRepository.findById(loginDto.getEmployee); // 수정 해야 함

        //3. 연차 신청하기
        LeaveRequestEntity leaveRequestEntity = leaveRequestRepository.save(dto.toEntity());
        if (leaveRequestEntity.getLno() < 1){
            return 3;
        }
        //4. 연차 - 부서 양방향관계[부서안에 연차 주소 대입]
        DepartmentEntity.getLeaveRequestEntityList().add(leaveRequestEntity);
        leaveRequestEntity.setDepartmentEntity(DepartmentEntity);
        //5. 연차 - 로그인 회원 양방향관계
        employeeEntity.getLeaveRequestEntityList().add(leaveRequestEntity);
        leaveRequestEntity.setEmployeeEntity(employeeEntity);

        return 0;
    }

}
