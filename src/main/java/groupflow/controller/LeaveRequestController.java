package groupflow.controller;

import groupflow.domain.leaverequest.LeaveRequestDto;
import groupflow.service.LeaveRequestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/dayoff")
@Slf4j
@CrossOrigin("http://localhost:3000")
public class LeaveRequestController {
    @Autowired
    LeaveRequestService leaveRequestService;
    
    // 1. 연차 신청
    @PostMapping("")
    public  byte postdayoff(@RequestBody LeaveRequestDto dto){
        byte result = leaveRequestService.postdayoff(dto);
        return result;
    }

}
