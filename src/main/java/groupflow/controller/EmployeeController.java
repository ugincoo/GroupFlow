package groupflow.controller;

import groupflow.domain.employee.EmployeeDto;
import groupflow.domain.employee.EmployeeEntity;
import groupflow.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    EmployeeService employeeService;

    @PostMapping("")
    public byte registerNewEmployee(
            @RequestPart(value = "info") EmployeeDto employeeDto,
            @RequestPart(value = "ephotodata") MultipartFile ephotodata
    ) {

        log.info("ephotodata : " + ephotodata);
        log.info("employeeDto : " + employeeDto);
        return employeeService.registerNewEmployee(ephotodata , employeeDto);
    }

    @PostMapping("/login")
    public EmployeeDto eLogin( @RequestBody EmployeeDto employeeDto ){
        return employeeService.eLogin( employeeDto );
    }

}