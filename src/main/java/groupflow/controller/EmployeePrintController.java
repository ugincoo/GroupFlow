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
public class EmployeePrintController {
//장민정 page

    @Autowired
    private EmployeePrintService employeePrintService;


    @GetMapping("")
    public List<EmployeeDto> searchEmplyee(@RequestParam int dno, @RequestParam int leavework ,@RequestParam int key, @RequestParam String keyword){ //전직원 출력[관리자입장]
        log.info("key:"+key);log.info("keyword:"+keyword);
        log.info("dno:"+dno);log.info("dcendreason:"+leavework);
        List<EmployeeDto> result = employeePrintService.searchEmplyee(dno,leavework,key,keyword);
        return  result;
    }

    @GetMapping("/department")//부서셀렉트에출력[관리자입장]
    public List<DepartmentDto> getDepartment(){
        List<DepartmentDto> result = employeePrintService.getDepartment();
        return  result;
    }

    @GetMapping("/finddno") //경영지원팀만 상세보기 가능
    public int getdno(){
        int result = employeePrintService.getdno();
        log.info("result:"+result);
        return result;
    }

    @GetMapping("/findmyemployees") //우리부서 직원들 구하기
    public List<EmployeeDto> getEmployees(){
        log.info("컨트롤");
        List<EmployeeDto> result=employeePrintService.getMyEmployees();
        log.info("우리직원"+result);
        return result;

    }





}