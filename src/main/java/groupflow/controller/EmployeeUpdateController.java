package groupflow.controller;

import groupflow.domain.department.DepartmentChangeDto;
import groupflow.domain.employee.EmployeeDto;

import groupflow.domain.position.PositionChangeDto;
import groupflow.service.EmployeeUpdateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController 
@Slf4j
@RequestMapping("/employee")
public class EmployeeUpdateController {
    @Autowired
    private EmployeeUpdateService employeeUpdateService;

    //기본프로필 수정
    @PutMapping("")
    public boolean updateEmployee(
            @RequestPart(value = "info") EmployeeDto employeeDto,
            @RequestPart(value = "ephotodata") MultipartFile ephotodata
    ) {
        log.info("Employee update controller?????????:" + employeeDto);
        boolean result = employeeUpdateService.updateEmployee(employeeDto,ephotodata);
        return result;


    }

    //부서변경 수정
    @PutMapping("/updatedepartment")
    public boolean updatedepartment(@RequestBody DepartmentChangeDto departmentChangeDto) {
        log.info("Employee updatedepartment controller?????????:" + departmentChangeDto);
        boolean result = employeeUpdateService.updatedepartment(departmentChangeDto);
        return result;


    }

    //부서변경 출력
    @GetMapping("/departmentprint")
    public List<DepartmentChangeDto> departmentprint(){
        return employeeUpdateService.departmentprint();
    }




    //직급변경 수정
    @PutMapping("/updateposition")
    public boolean updateposition(@RequestBody PositionChangeDto positionChangeDto) {
        log.info("Employee updateposition controller?????????:" + positionChangeDto);
        boolean result = employeeUpdateService.updateposition(positionChangeDto);
        return result;
    }
    //재직-->퇴사변경
    @PutMapping("/updateenddate")
    public  boolean updateenddate(@RequestBody EmployeeDto employeeDto){
        boolean result=employeeUpdateService.updateenddate(employeeDto);
        return  result;
    }


}