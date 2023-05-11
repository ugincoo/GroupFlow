package groupflow.controller;

import groupflow.domain.department.DepartmentChangeDto;
import groupflow.domain.employee.EmployeeDto;

import groupflow.service.EmployeeUpdateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController 
@Slf4j
@RequestMapping("/employee")
public class EmployeeUpdateController {
    @Autowired
    private EmployeeUpdateService employeeUpdateService;
    //기본프로필 수정
    @PutMapping("")
    public boolean updateEmployee(@RequestBody EmployeeDto employeeDto){
        log.info("Employee update controller?????????:"+employeeDto);
        boolean result =employeeUpdateService.updateEmployee(employeeDto);
        return result;


    }
    //부서변경 수정
    @PutMapping("/updatedepartment")
    public boolean updatedepartment(@RequestBody DepartmentChangeDto departmentChangeDto){
        log.info("Employee departmentChangeDto controller?????????:"+departmentChangeDto);
        boolean result =employeeUpdateService.updatedepartment(departmentChangeDto);
        return result;


    }
}
