package groupflow.controller;

import groupflow.domain.notification.NoticeDto;
import groupflow.service.NoticeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/notice")
public class NoticeController {
    @Autowired
    private NoticeService noticeService;
    //공지등록
    @PostMapping("/noticepost")
    public boolean noticepost(@RequestBody NoticeDto noticeDto){
        log.info("noticeDto controller??:"+noticeDto);
        return noticeService.noticepost(noticeDto);

    }
    //공지출력
    @GetMapping("/noticeget")
    public List<NoticeDto> noticeget(){

        List<NoticeDto> list= noticeService.noticeget();
        return list;
    }
}
