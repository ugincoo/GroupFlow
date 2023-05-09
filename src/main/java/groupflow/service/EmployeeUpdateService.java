package groupflow.service;

import groupflow.domain.department.DepartmentChangeDto;
import groupflow.domain.employee.EmployeeDto;
import groupflow.domain.employee.EmployeeEntity;
import groupflow.domain.employee.EmployeeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;

@Service
@Slf4j
public class EmployeeUpdateService {
    @Autowired
    private EmployeeRepository employeeRepository;
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
        Optional<EmployeeEntity> optionalEmployeeEntity =employeeRepository.findById(departmentChangeDto.getDcno());
        if(optionalEmployeeEntity.isPresent()){

        }
        return true;

    }

}