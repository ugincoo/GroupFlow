package groupflow.controller;

import groupflow.domain.employee.EmployeeDto;
import groupflow.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/employee")
@CrossOrigin("http://localhost:3000")
public class EmployeeController {

    @Autowired
    EmployeeService employeeService;
    public byte registerNewEmployee(EmployeeDto employeeDto) {
        return employeeService.registerNewEmployee(employeeDto);
    }

}
