package groupflow.service;

import groupflow.domain.attendance.AttendanceEntity;
import groupflow.domain.attendance.AttendanceRepository;

import groupflow.domain.employee.EmployeeEntity;
import groupflow.domain.employee.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class AttendanceService {

    @Autowired
    private AttendanceRepository attendanceRepository;
    private EmployeeRepository employeeRepository;
    //출근퇴근---------------------------------------------------------------------------------------------
    int eno=20230505;
    @Transactional
    public  boolean gowork( ){
         Optional<EmployeeEntity> optionalEmployeeEntity =employeeRepository.findById(eno);
         if(optionalEmployeeEntity.isPresent()){
             EmployeeEntity entity=optionalEmployeeEntity.get();
             AttendanceEntity attendanceEntity=AttendanceEntity.builder()//근태 엔티티를 구하면서 동시에 DB에서 구한 직원엔티티도 집어넣는거
                     .employeeEntity(entity).build();
             AttendanceEntity attendanceEntity1=attendanceRepository.save(attendanceEntity);
             if(attendanceEntity1.getAno()>0){return true;}
             entity.getAttendanceEntityList().add(attendanceEntity1);//eno로 찾은 직원엔티티에 근태리스트갖고와서 근태엔티티추가
             attendanceEntity1.setEmployeeEntity(entity);//생성된 근태엔티티에 직원엔티티 저장

         }


        return true;
    }




}





