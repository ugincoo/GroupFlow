package groupflow.service;

import groupflow.domain.attendance.AttebdanceDto;
import groupflow.domain.attendance.AttendanceEntity;
import groupflow.domain.attendance.AttendanceRepository;

import groupflow.domain.employee.EmployeeDto;
import groupflow.domain.employee.EmployeeEntity;
import groupflow.domain.employee.EmployeeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Slf4j
@Service
public class AttendanceService {

    @Autowired
    private AttendanceRepository attendanceRepository;
    @Autowired
    private EmployeeRepository employeeRepository;
    //출근퇴근---------------------------------------------------------------------------------------------
    int eno=2021001;
    @Transactional
    public  boolean gowork( ){
        log.info("gowork실행");
         Optional<EmployeeEntity> optionalEmployeeEntity =employeeRepository.findById(eno);
        log.info("optionalEmployeeEntity??"+optionalEmployeeEntity);
         if(optionalEmployeeEntity.isPresent()){
             EmployeeEntity entity=optionalEmployeeEntity.get(); log.info("entity:"+entity);
             AttendanceEntity attendanceEntity=AttendanceEntity.builder()//근태 엔티티를 구하면서 동시에 DB에서 구한 직원엔티티도 집어넣는거
                     .employeeEntity(entity).build();
             AttendanceEntity attendanceEntity1=attendanceRepository.save(attendanceEntity);
             if(attendanceEntity1.getAno()>0){
             entity.getAttendanceEntityList().add(attendanceEntity1);//eno로 찾은 직원엔티티에 근태리스트갖고와서 근태엔티티추가
             attendanceEntity1.setEmployeeEntity(entity);
                 return true;
             }//생성된 근태엔티티에 직원엔티티 저장

         }


        return false;
    }
    //퇴근
    @Transactional
    public  boolean outwork(){
        Optional<EmployeeEntity> optionalEmployeeEntity=employeeRepository.findById(eno);//eno 를 이용해서 직원엔티티 찾는다
        if(optionalEmployeeEntity.isPresent()){
            List<AttendanceEntity> attendanceEntityList =optionalEmployeeEntity.get().getAttendanceEntityList();//찾은 직원엔티티에는 근태리스트가 있음 .꺼내라 왜?출근한 직원의 정보를 갖고와야해서
            if(!attendanceEntityList.isEmpty()){ //근태리스트에 레코드가 있으면
                int lastindex =attendanceEntityList.size()-1; //마지막인덱스=근태리스트-1(마지막 인덱스=최근인덱스 찾을려고)
                AttendanceEntity attendanceEntity=attendanceEntityList.get(lastindex);

            }



           return true;

        }
        return false;

    }


}





