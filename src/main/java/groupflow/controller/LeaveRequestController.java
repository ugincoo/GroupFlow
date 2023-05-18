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
public class LeaveRequestController {
    @Autowired
    LeaveRequestService leaveRequestService;
    
    // 1. 연차 신청
    @PostMapping("")
    public  byte post(@RequestBody LeaveRequestDto dto){
        return leaveRequestService.post(dto);
    }

    // 2. 개인 연차 출력
    @GetMapping("/myget")
    public List<LeaveRequestDto> myget(){
        return leaveRequestService.myget();
    }

    /*-------------------------------------시큐리티 권한 때문에 똑같은거 두개 만듬 -----------------------------------------*/
    // 3-1. 부서 연차 출력 [ 부장 직급 ]
    @GetMapping("/position")
    public List<LeaveRequestDto> pget(@RequestParam int dno){
        return leaveRequestService.pget(dno);
    }
    // 3-2. 전체 출력 [ 경영지원팀 ]
    @GetMapping("/admin")
    public List<LeaveRequestDto> adminget(){
        return leaveRequestService.adminget();
    }

    // 4. 결재 승인
    @PutMapping("/pok")
    public boolean pok( @RequestBody LeaveRequestDto dto ){
        return leaveRequestService.pok(dto);
    }

}
