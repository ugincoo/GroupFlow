package groupflow.controller;



import groupflow.domain.attendance.AttebdanceDto;
import groupflow.domain.employee.EmployeeDto;
import groupflow.service.AttendanceService;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/employee")
@CrossOrigin("http://localhost:3000")
public class AttendanceController {

    @Autowired
    private AttendanceService attendanceService;

    //출근----------------------------------------------------
   @PostMapping("/gowork")
    public  boolean gowork(){
        log.info("테스트");
        boolean result=attendanceService.gowork();
        return result;
    }
    //퇴근----------------------------------------------------
    @PutMapping("/outwork")
    public boolean outwork() {
        log.info("outwork putmapping:" );
        boolean result = attendanceService.outwork();
        return result;
    }
}

