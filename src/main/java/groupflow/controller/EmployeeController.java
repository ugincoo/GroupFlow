package groupflow.controller;

import groupflow.domain.employee.EmployeeDto;
import groupflow.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/employee")
@CrossOrigin("http://localhost:3000")
public class EmployeeController {

    @Autowired
    EmployeeService employeeService;

    @PostMapping("")
    public byte registerNewEmployee( @RequestBody EmployeeDto employeeDto) {
        log.info("c registerNewEmployee 실행 employeeDto : " + employeeDto );
        return employeeService.registerNewEmployee(employeeDto);
    }

}
