package groupflow.controller;

import groupflow.domain.employee.EmployeeDto;
import groupflow.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import groupflow.domain.department.DepartmentDto;
import groupflow.domain.employee.EmployeeDto;
import groupflow.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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


    @Autowired
    private EmployeeService employeeService;

    @GetMapping("")
    public List<EmployeeDto> allEmplyee(int dno,int dcendreason){ //전직원 출력[관리자입장]
        System.out.println("dno:"+dno);
        System.out.println("dcendreason:"+dcendreason);
        List<EmployeeDto> result = employeeService.allEmplyee(dno,dcendreason);
        return  result;
    }

    @GetMapping("/department")
    public List<DepartmentDto> getDepartment(){ //부서셀렉트[관리자입장]

        List<DepartmentDto> result = employeeService.getDepartment();
        return  result;
    }






}
