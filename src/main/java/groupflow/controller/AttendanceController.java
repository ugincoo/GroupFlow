package groupflow.controller;

import groupflow.domain.employee.EmployeeDto;
import groupflow.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/employee")
public class AttendanceController {

    @Autowired
    private EmployeeService employeeService;
    //출근,퇴근--------------------------------------------------------
  /*  @PostMapping("/gowork")
    public  boolean gowork(@RequestBody EmployeeDto employeeDto){
        boolean result=employeeService.gowork(eno,ename);
        return result;
    }

*/

}

