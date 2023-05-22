package groupflow.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import groupflow.domain.attendance.AttendanceDto;
import groupflow.domain.attendance.AttendanceEntity;
import groupflow.domain.attendance.AttendanceRepository;

import groupflow.domain.employee.EmployeeDto;
import groupflow.domain.employee.EmployeeEntity;
import groupflow.domain.employee.EmployeeRepository;
import groupflow.socket.AttendanceHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
@Slf4j
@Service
public class AttendanceService {

    @Autowired
    private AttendanceRepository attendanceRepository;
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private LoginService loginService;
    @Autowired
    private AttendanceHandler attendanceHandler;
    //출근퇴근---------------------------------------------------------------------------------------------

    @Transactional
    public  boolean gowork(){


        log.info("gowork실행");
        EmployeeDto employeeDto = loginService.loginInfo();
         Optional<EmployeeEntity> optionalEmployeeEntity =employeeRepository.findById(employeeDto.getEno());
        log.info("optionalEmployeeEntity??"+optionalEmployeeEntity);
         if(optionalEmployeeEntity.isPresent()){
             EmployeeEntity entity=optionalEmployeeEntity.get(); log.info("entity:"+entity);
             //근태 엔티티를 생성
             AttendanceEntity attendanceEntity= new AttendanceEntity();
                     //AttendanceEntity.builder().employeeEntity(entity).build(); 동시에 DB에서 구한 직원엔티티도 집어넣는거

             AttendanceEntity attendanceEntity1=attendanceRepository.save(attendanceEntity);
             if(attendanceEntity1.getAno()>0){
                 attendanceEntity1.setEmployeeEntity(entity); // 근태엔티티에 직원엔티티 추가
                 entity.getAttendanceEntityList().add(attendanceEntity1);//eno로 찾은 직원엔티티에 근태리스트갖고와서 근태엔티티추가
                 attendanceEntity1.setEmployeeEntity(entity);//생성된 근태엔티티에 직원엔티티 저장


                 try {
                     ObjectMapper mapper = new ObjectMapper();
                     String json = mapper.writeValueAsString("enter");
                     TextMessage message = new TextMessage(json);
                     attendanceHandler.handleMessage(null, message); }
                 catch (Exception e) { throw new RuntimeException(e);}
                 return true;
             }

         }


        return false;
    }
    //퇴근
    @Transactional
    public  boolean outwork(){
        EmployeeDto employeeDto = loginService.loginInfo();
        Optional<EmployeeEntity> optionalEmployeeEntity=employeeRepository.findById(employeeDto.getEno());//eno 를 이용해서 직원엔티티 찾는다
        if(optionalEmployeeEntity.isPresent()){
            List<AttendanceEntity> attendanceEntityList =optionalEmployeeEntity.get().getAttendanceEntityList();//찾은 직원엔티티에는 근태리스트가 있음 .꺼내라 왜?출근한 직원의 정보를 갖고와야해서
            log.info("attendanceEntityList??:"+attendanceEntityList);
           if(!attendanceEntityList.isEmpty()){ //근태리스트에 레코드가 있으면
                int lastindex =attendanceEntityList.size()-1; //마지막인덱스=근태리스트-1(마지막 인덱스=최근인덱스 찾을려고)
                AttendanceEntity attendanceEntity=attendanceEntityList.get(lastindex);
               attendanceEntity.setUdate( LocalDateTime.now() ); // 근태엔티티에 변경할수있는 필드가 없어서 수정이 자동으로 안됌.
               //BaseTime @Date 를 넣어주고 setUdate 에 현재 시간을 강제적으러 넣어줘서 변경해줌.
               log.info("attendanceEntityList??22222:"+attendanceEntityList);


               try {
                   ObjectMapper mapper = new ObjectMapper();
                   String json = mapper.writeValueAsString("enter");
                   TextMessage message = new TextMessage(json);
                   attendanceHandler.handleMessage(null, message); }
               catch (Exception e) { throw new RuntimeException(e);}


               return true;
            }
        }
        return false;

    }

    //출퇴근출력
    @Transactional
 public List<AttendanceDto> gooutwork(){
     //Object o = SecurityContextHolder.getContext().getAuthentication().getPrincipal(); <- eno, ename암호화 , dno~pno~ 없음
     EmployeeDto employeeDto = loginService.loginInfo(); // employeeDto : eno, ename , dno, dname , pno , pname
  Optional<EmployeeEntity> optionalEmployeeEntity =employeeRepository.findById( employeeDto.getEno());
     log.info("optionalEmployeeEntity??????????bba:"+optionalEmployeeEntity);
  if(optionalEmployeeEntity.isPresent()){
      List<AttendanceEntity>attendanceEntityList =optionalEmployeeEntity.get().getAttendanceEntityList();//eno로 찾은 근태리스트
      log.info("attendanceEntityList??????????Aa:"+attendanceEntityList);
      List<AttendanceDto> list=new ArrayList<>();
      attendanceEntityList.forEach((e)->{
          list.add(e.toDto());
      });
      log.info("list???:"+list);
      Collections.reverse(list);
      return list;

  }
     return  null;
    }

    //출근상태
    @Transactional
    public boolean infostate(){
        log.info("start!!!!");
       EmployeeDto employeeDto = loginService.loginInfo();
        log.info("employeeDto:????"+employeeDto);
        Optional<AttendanceEntity> optionalAttendanceEntity=attendanceRepository.findByeno(employeeDto.getEno());
        log.info("optionalAttendanceEntity:????"+optionalAttendanceEntity);
        if(optionalAttendanceEntity.isPresent()){
            AttendanceEntity attendanceEntity=optionalAttendanceEntity.get();
            log.info("attendanceEntity:????"+attendanceEntity);
            attendanceRepository.save(attendanceEntity);
            if(attendanceEntity.getAno()>0){return true;}
        }
        return false;
        }



}





