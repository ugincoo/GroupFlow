package groupflow.service;

import groupflow.domain.notification.NoticeDto;
import groupflow.domain.notification.NoticeEntity;
import groupflow.domain.notification.NoticeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    //공지출력
    @Transactional
    public List<NoticeDto> noticeget(){
        List<NoticeEntity> noticeEntityList=noticeRepository.findAll();
        List<NoticeDto> list=new ArrayList<>();
        noticeEntityList.forEach((e)->{
            list.add(e.todto());
        });
        log.info("list??????:"+list);
        return list;
    }
    @Transactional
    public boolean noticedelete(int nno){
        Optional<NoticeEntity> optionalNoticeEntity =noticeRepository.findById(nno);
        if(optionalNoticeEntity.isPresent()){
            NoticeEntity noticeEntity=optionalNoticeEntity.get();
            noticeRepository.delete(noticeEntity); return  true;
        }
        return false;
    }

}
