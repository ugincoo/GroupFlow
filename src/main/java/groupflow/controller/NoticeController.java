package groupflow.controller;

import groupflow.domain.notification.NoticeDto;
import groupflow.service.NoticeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/notice")
public class NoticeController {
    @Autowired
    private NoticeService noticeService;

    @PostMapping("/noticepost")
    public boolean noticepost(@RequestBody NoticeDto noticeDto){
        log.info("noticeDto controller??:"+noticeDto);
        return noticeService.noticepost(noticeDto);

    }
}
