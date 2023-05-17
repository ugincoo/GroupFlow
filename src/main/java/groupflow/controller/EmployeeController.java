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

    // 사원등록
    @PostMapping("")
    public byte registerNewEmployee(
            @RequestPart(value = "info") EmployeeDto employeeDto,
            @RequestPart(value = "ephotodata") MultipartFile ephotodata
    ) {

        log.info("ephotodata : " + ephotodata);
        log.info("employeeDto : " + employeeDto);
        return employeeService.registerNewEmployee(ephotodata , employeeDto);
    }


    // eno로 직원정보 (직원번호,직원명,부서번호,부서명,직급번호,직급명) 가져오기
    @GetMapping("/select/info")
    public EmployeeDto employeeInfo( @RequestParam int eno ){
        return employeeService.employeeInfo(eno);
    }


    // 로그인한 사람이 부장일 경우 부서내 직원리스트 가져오기 ( 리스트에서 부장제외 )
    @GetMapping("/department")
    public List<EmployeeDto> getEmployeesByDepartmentWithoutManager(){
        return employeeService.getEmployeesByDepartmentWithoutManager();
    }

}