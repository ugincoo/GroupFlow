package groupflow.controller;

import groupflow.domain.employee.EmployeeDto;
import groupflow.service.AttendanceService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/employee")
public class AttendanceController {

    @Autowired
    private AttendanceService attendanceService;
    //출근,퇴근--------------------------------------------------------
   @PostMapping("/gowork")
    public  boolean gowork(){
        boolean result=attendanceService.gowork();
        return result;
    }


}

