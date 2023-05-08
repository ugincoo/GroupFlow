package groupflow.service;

import com.sun.xml.bind.api.Bridge;
import groupflow.domain.department.*;
import groupflow.domain.employee.EmployeeDto;
import groupflow.domain.employee.EmployeeEntity;
import groupflow.domain.employee.EmployeeRepository;
import groupflow.domain.position.PositionChangeEntity;
import groupflow.domain.position.PositionChangeEntityRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

@Service
@Slf4j
public class EmployeePrintService {

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    DepartmentChangeEntityRepository departmentChangeRepository;

    @Autowired
    DepartmentRepository departmentRepository;

    @Autowired
    PositionChangeEntityRepository positionChangeRepository;

    /*
    01: 경영지원팀
    02: 인사팀
    03: 영업팀
    04: 마케팅팀
    05: 기술개발팀
    06: 디자인팀
    07: 생산팀
    08: 자재팀
    09: IT팀
    10: 회계팀
    */



    //전직원 출력[관리자입장]
    @Transactional
    public List<EmployeeDto> allEmplyee(int dno,int dcendreason){
        System.out.println("dno1"+dno);System.out.println("dcendreason1"+dcendreason);
        List<EmployeeEntity> entityList=null;
        if(dno==0&&dcendreason==1){ //근무하는 전체직원
            entityList=employeeRepository.findemployeebyNulleenddate(dcendreason);
        }if(dno==0&&dcendreason==2){ //전체 퇴사자
            entityList=employeeRepository.findemployeebyNotNULLeenddate(dcendreason);
        }if(dno!=0&&dcendreason==1){    //부서별 근무직원
            entityList=employeeRepository.findemployeebydno_null(dno);
        }if(dno!=0&&dcendreason==2){    //부서별 퇴사직원
            entityList=employeeRepository.findemployeebydno_notnull(dno);
        }
        List<EmployeeDto> dtoList=new ArrayList<>();

        entityList.forEach((e)->{
             EmployeeDto employeeDto=e.toDto();
             int index =  e.getDepartmentChangeEntityList().size()-1;
             employeeDto.setDname( e.getDepartmentChangeEntityList().get(index).getDepartmentEntity().getDname());
             employeeDto.setPname( e.getPositionChangeEntityList().get(index).getPositionEntity().getPname());

            dtoList.add(employeeDto);
        });

        return dtoList;
    }

    //부서 출력[직원입장]
    public List<DepartmentDto> getDepartment(){ //부서셀렉트[관리자입장]
        System.out.println("getDepartment:");
        List<DepartmentEntity> entityList=departmentRepository.findAll();
        List<DepartmentDto> dtoList= new ArrayList<>();
        entityList.forEach( (e)->{
            dtoList.add(e.toDto());
        });
        return dtoList;
    }

}