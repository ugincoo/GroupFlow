package groupflow.service;

import groupflow.domain.department.*;
import groupflow.domain.employee.EmployeeDto;
import groupflow.domain.employee.EmployeeEntity;
import groupflow.domain.employee.EmployeeRepository;
import groupflow.domain.position.PositionChangeEntityRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


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



    //직원 출력[관리자입장]
    @Transactional
    public List<EmployeeDto> allEmplyee(int dno, int leavework){
        System.out.println("dno1"+dno);System.out.println("dcendreason1"+leavework);
        List<EmployeeEntity> entityList=null;
        if(dno==0&&leavework==1){ //근무하는 전체직원
            entityList=employeeRepository.findemployeebyNulleenddate(leavework);
        }else if(dno==0&&leavework==2){ //전체 퇴사자
            entityList=employeeRepository.findemployeebyNotNULLeenddate(leavework);
        }else if(dno!=0&&leavework==1){    //부서별 근무직원
            entityList=employeeRepository.findemployeebydnoNull(dno);
        }else if(dno!=0&&leavework==2){    //부서별 퇴사직원
            entityList=employeeRepository.findemployeebydnoNotnull(dno);
        }
        List<EmployeeDto> dtoList=new ArrayList<>();

        entityList.forEach((e)->{
             EmployeeDto employeeDto=e.toDto();
             int index =  e.getDepartmentChangeEntityList().size()-1;
             employeeDto.setDname( e.getDepartmentChangeEntityList().get(index).getDepartmentEntity().getDname());
             employeeDto.setPname( e.getPositionChangeEntityList().get(index).getPositionEntity().getPname());
             employeeDto.setId(e.getEno());
            dtoList.add(employeeDto);
        });

        return dtoList;
    }

    @Transactional
    public List<EmployeeDto> searchEmplyee(int dno, int leavwork , int key, String keyword){
        List<EmployeeEntity> entityList=null;
        if(key!=0){
            entityList = employeeRepository.findemployeebyKeyWord(keyword);
        }


        List<EmployeeDto> dtoList=new ArrayList<>();

        entityList.forEach((e)->{

            int index =  e.getDepartmentChangeEntityList().size()-1;

            if( e.getDepartmentChangeEntityList().get( index).getDepartmentEntity().getDno() == dno ){

                // 근무자 = null
                if( leavwork == 1 && e.getEenddate() == null  ){
                    EmployeeDto employeeDto=e.toDto();
                    employeeDto.setDname( e.getDepartmentChangeEntityList().get(index).getDepartmentEntity().getDname());
                    employeeDto.setPname( e.getPositionChangeEntityList().get(index).getPositionEntity().getPname());
                    employeeDto.setId(e.getEno());
                    dtoList.add(employeeDto);
                }else{
                    EmployeeDto employeeDto=e.toDto();
                    employeeDto.setDname( e.getDepartmentChangeEntityList().get(index).getDepartmentEntity().getDname());
                    employeeDto.setPname( e.getPositionChangeEntityList().get(index).getPositionEntity().getPname());
                    employeeDto.setId(e.getEno());
                    dtoList.add(employeeDto);
                }

            }else if( dno == 0 ){
                EmployeeDto employeeDto=e.toDto();
                employeeDto.setDname( e.getDepartmentChangeEntityList().get(index).getDepartmentEntity().getDname());
                employeeDto.setPname( e.getPositionChangeEntityList().get(index).getPositionEntity().getPname());
                employeeDto.setId(e.getEno());
                dtoList.add(employeeDto);

            }
        });

        return dtoList;
    }


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