package groupflow.controller;

import groupflow.domain.employee.EmployeeDto;
import groupflow.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("")
    public List<EmployeeDto> getEmployees(){
        log.info("c getEmployees 실행 ");
        return employeeService.getEmployees();
    }

}
