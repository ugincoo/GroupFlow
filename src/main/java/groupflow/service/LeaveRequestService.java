package groupflow.service;
import groupflow.domain.department.DepartmentChangeEntity;
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

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    public byte post(LeaveRequestDto dto){
        log.info("postdayoff dto : " + dto);
        // 1. 부서 앤티티 찾기 [ 연차안에 부서 엔티티객체 대입 하기 위해 ]
        Optional<DepartmentEntity> departmentEntityOptional = departmentRepository.findById(dto.getPno());
        if( !departmentEntityOptional.isPresent()) {
            return 1; // 만약에 선택 된 부서카테고리가 없으면 return
        }
        DepartmentEntity DepartmentEntity = departmentEntityOptional.get();    // 3. 카테고리 엔티티 추출
/*
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
        //ㅈ직원 부서 가져오기
        employeeEntity.getDepartmentChangeEntityList().get(7).getDepartmentEntity().getDno();
       */ //3. 연차 신청하기
        LeaveRequestEntity leaveRequestEntity = leaveRequestRepository.save(dto.toEntity());
        // 4. 신청자 부서의 최고 pno에게 전달
        EmployeeEntity applicant = leaveRequestEntity.getEmployeeEntity(); // 신청자 정보 가져오기
        List<DepartmentChangeEntity> applicantDepartment = applicant.getDepartmentChangeEntityList(); // 신청자 부서 정보 가져오기

        int highestPno = 7; // 최고 pno 값


        if (leaveRequestEntity.getLno() < 1){
            // 전달 작업 수행


            return 3;
        }
        //4. 연차 - 부서 양방향관계[부서안에 연차 주소 대입]
        DepartmentEntity.getLeaveRequestEntityList().add(leaveRequestEntity);
        leaveRequestEntity.setDepartmentEntity(DepartmentEntity);
       /* //5. 연차 - 로그인 회원 양방향관계
        employeeEntity.getLeaveRequestEntityList().add(leaveRequestEntity);
        leaveRequestEntity.setEmployeeEntity(employeeEntity);*/

        return 0;
    }

    // 연차 출력
    public List<LeaveRequestDto> get(){
        log.info("get Service : ");
        // 모든 엔티티 호출
        List<LeaveRequestEntity> entityList = leaveRequestRepository.findAll();
        // 모든 엔티티를 dto로변환
        List<LeaveRequestDto> dtoList =
                entityList.stream().map( o -> o.toDto()).collect(Collectors.toList());

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