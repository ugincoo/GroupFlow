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
        //log.info("ephotoFile : " + employeeDto.getEphotoData());
        return employeeService.registerNewEmployee(employeeDto);
    }

    @PostMapping("/login")
    public EmployeeDto eLogin( @RequestBody EmployeeDto employeeDto ){
        return employeeService.eLogin( employeeDto );
    }

}