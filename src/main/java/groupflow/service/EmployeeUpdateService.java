package groupflow.service;

import groupflow.domain.department.*;

import groupflow.domain.employee.EmployeeDto;
import groupflow.domain.employee.EmployeeEntity;
import groupflow.domain.employee.EmployeeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class EmployeeUpdateService {
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private DepartmentChangeEntityRepository departmentChangeEntityRepository;
    @Autowired
    private DepartmentRepository departmentRepository;
    //기본프로필수정
    @Transactional
    public boolean updateEmployee(EmployeeDto employeeDto) {
        log.info("Employee update service!!!!!:" + employeeDto);
        Optional<EmployeeEntity> optionalEmployeeEntity =employeeRepository.findById(employeeDto.getEno());
        if(optionalEmployeeEntity.isPresent()){
            EmployeeEntity entity=optionalEmployeeEntity.get();
            entity.setEname(employeeDto.getEname());
            entity.setEsocialno(employeeDto.getEsocialno());
            entity.setEemail(employeeDto.getEemail());
            entity.setEphone(employeeDto.getEphone());
        }
        return true;

    }
    //부서변경
    @Transactional
    public boolean updatedepartment(DepartmentChangeDto departmentChangeDto) {
        log.info("Employee departmentChangeDto service!!!!!:" + departmentChangeDto);

        // 1. 기존 부서 수정 [ 끝날짜 설정 ]
         DepartmentChangeEntity lastDepartmentChangeEntity=departmentChangeEntityRepository.findAllMyDepartmetChangeList(departmentChangeDto.getEno());

        // 끝날짜에 날짜를 넣어줌 ( 기존부서 끝날짜 = 새로운부서이동 적용날짜(dcstartdate)의 하루전 )
        lastDepartmentChangeEntity.setDcenddate(departmentChangeDto.toEntity().getDcstartdate().minusDays(1));
       //departmentChangeDto.getDcstartdate();

       //내일이 경리부이동 오늘이 마지막 영업부 시작날짜에 현재날짜의 +1

        // 2. 새로운 부서 이동 [ 시작날짜,변경사유,부서entity,직원entity]
        // departmentEntityRepository
        // 1. dto -> entity
        DepartmentChangeEntity departmentChangeEntity=departmentChangeDto.toEntity();//받은 dto를 db에저장할려고 엔티티로 변환
        departmentChangeEntityRepository.save(departmentChangeEntity);//변환해서 디비에저장
        //1.변환해서 저장된 엔티티안에는 dcno,dcstartdate,dcenddate,dcstartreason 만있음 dno,eno값이 null값임 왜?
        //dto에서 to엔티티로 변환하는 함수에 eno,dno를 엔티티로 변환하는 식이 없어서 엔티티2개를 찾아서 departmentChangeEntity 넣어서 저장해야함

        Optional<EmployeeEntity> optionalEmployeeEntity=employeeRepository.findById(departmentChangeDto.getEno());
        if(optionalEmployeeEntity.isPresent()) {
            EmployeeEntity employeeEntity = optionalEmployeeEntity.get();//직원 엔티티
            departmentChangeEntity.setEmployeeEntity(employeeEntity);//eno로 찾은 직원엔티티(모든한줄레코드)를 부서변경레코드에 저장해줬음//단반향
            employeeEntity.getDepartmentChangeEntityList().add(departmentChangeEntity);//양방향
            //직원엔티티에(나의목록) 부서변경리스트안에 부서변경이력을 넣어주겠다.


        }
        // 양방향으로 employeeEntity의 departChangeEntityList 에 departmentChangeEntity 추가

        Optional<DepartmentEntity> optionalDepartmentEntity=departmentRepository.findById(departmentChangeDto.getDno());
        if(optionalDepartmentEntity.isPresent()){
           DepartmentEntity departmentEntity =  optionalDepartmentEntity.get();
           departmentChangeEntity.setDepartmentEntity( departmentEntity );//부서이동내역에 부서(영업팀)를 저장해줌
            departmentEntity.getDepartmentChangeEntityList().add(departmentChangeEntity);//부서에 부서이동내역리스트 안에 부서이동내역을 담아줌
        }
        // 양방향으로 DepartmentEntity의 departChangeEntityList 에 departmentChangeEntity 추가

        return true;

    }
// 부서변경 -> departmentChangeDto.getEno로 employeeEntity /  departmentChangeDto.getDno -> departmentEntity
    // 기존부서변경 수정 dcenddate -> 적용날짜 하루전날짜로 수정
        // 기존부서변경 레코드를 찾아야됨.
    // 부서변경 필드 / dcstartdate / dcstartreason / departmentEntity / employeeEntity
    //
}