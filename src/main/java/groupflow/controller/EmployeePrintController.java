package groupflow.controller;

import groupflow.domain.department.DepartmentDto;
import groupflow.domain.employee.EmployeeDto;
import groupflow.service.EmployeePrintService;
import groupflow.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/employee/print")
@CrossOrigin("http://localhost:3000")
public class EmployeePrintController {

    @Autowired
    private EmployeePrintService employeePrintService;

    @GetMapping("")
    public List<EmployeeDto> allEmplyee(@RequestParam int dno, @RequestParam int leavework){ //전직원 출력[관리자입장]
        System.out.println("dno:"+dno);
        System.out.println("dcendreason:"+leavework);
        List<EmployeeDto> result = employeePrintService.allEmplyee(dno,leavework);
        return  result;
    }
    @GetMapping("/search")
    public List<EmployeeDto> searchEmplyee(@RequestParam int dno, @RequestParam int leavework ,@RequestParam int key, @RequestParam String keyword){ //전직원 출력[관리자입장]
        log.info("key:"+key);log.info("keyword:"+keyword);
        log.info("dno:"+dno);log.info("dcendreason:"+leavework);
        List<EmployeeDto> result = employeePrintService.searchEmplyee(dno,leavework,key,keyword);
        return  result;
    }

    @GetMapping("/department")
    public List<DepartmentDto> getDepartment(){ //부서셀렉트에출력[관리자입장]

        List<DepartmentDto> result = employeePrintService.getDepartment();
        return  result;
    }






}