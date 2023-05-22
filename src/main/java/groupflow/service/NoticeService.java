package groupflow.service;

import groupflow.domain.notification.NoticeDto;
import groupflow.domain.notification.NoticeEntity;
import groupflow.domain.notification.NoticeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class NoticeService {
    @Autowired
    private NoticeRepository noticeRepository;
    //공지등록
    @Transactional
    public boolean noticepost(NoticeDto noticeDto){
        NoticeEntity noticeEntity =noticeRepository.save(noticeDto.toEntity());
        log.info("noticeEntity??:"+noticeEntity);
        if(noticeEntity.getNno()>0){
            return true;
        }
        return false;
    }

}
