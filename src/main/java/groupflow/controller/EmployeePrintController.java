package groupflow.controller;

import groupflow.domain.department.DepartmentDto;
import groupflow.domain.employee.EmployeeDto;
import groupflow.service.EmployeePrintService;
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
@RequestMapping("/employee/print")
@CrossOrigin("http://localhost:3000")
public class EmployeePrintController {

    @Autowired
    private EmployeePrintService employeePrintService;

    @GetMapping("")
    public List<EmployeeDto> allEmplyee(int dno,int dcendreason){ //전직원 출력[관리자입장]
        System.out.println("dno:"+dno);
        System.out.println("dcendreason:"+dcendreason);
        List<EmployeeDto> result = employeePrintService.allEmplyee(dno,dcendreason);
        return  result;
    }

    @GetMapping("/department")
    public List<DepartmentDto> getDepartment(){ //부서셀렉트[관리자입장]

        List<DepartmentDto> result = employeePrintService.getDepartment();
        return  result;
    }






}