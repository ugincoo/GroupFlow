package groupflow.controller;

import groupflow.domain.leaverequest.LeaveRequestDto;
import groupflow.domain.leaverequest.LeaveRequestEntity;
import groupflow.service.LeaveRequestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/dayoff")
@Slf4j
@CrossOrigin("http://localhost:3000")
public class LeaveRequestController {
    @Autowired
    LeaveRequestService leaveRequestService;
    
    // 1. 연차 신청
    @PostMapping("")
    public  byte post(@RequestBody LeaveRequestDto dto){
        return leaveRequestService.post(dto);
    }

    @GetMapping("")
    public List<LeaveRequestDto> get(){
        return leaveRequestService.get();
    }
}
