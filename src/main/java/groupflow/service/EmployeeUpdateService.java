package groupflow.service;

import groupflow.domain.department.DepartmentChangeDto;

import groupflow.domain.department.DepartmentChangeEntity;
import groupflow.domain.department.DepartmentChangeEntityRepository;
import groupflow.domain.employee.EmployeeDto;
import groupflow.domain.employee.EmployeeEntity;
import groupflow.domain.employee.EmployeeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Slf4j
public class EmployeeUpdateService {
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private DepartmentChangeEntityRepository departmentChangeEntityRepository;
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
        Optional<DepartmentChangeEntity>optionalDepartmentChangeEntity=
                departmentChangeEntityRepository.findById(departmentChangeDto.getEno());
        log.info("optionalDepartmentChangeEntity:?????"+optionalDepartmentChangeEntity);
        if(optionalDepartmentChangeEntity.isPresent()){
            optionalDepartmentChangeEntity.get().getDepartmentEntity().getDno();
        }
        return true;

    }
// 부서변경 -> departmentChangeDto.getEno로 employeeEntity /  departmentChangeDto.getDno -> departmentEntity
    // 기존부서변경 수정 dcenddate -> 적용날짜 하루전날짜로 수정
        // 기존부서변경 레코드를 찾아야됨.
    // 부서변경 필드 / dcstartdate / dcstartreason / departmentEntity / employeeEntity
    //
}