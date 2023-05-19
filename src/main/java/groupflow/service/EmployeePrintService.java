package groupflow.service;

import groupflow.domain.attendance.AttendanceListDto;
import groupflow.domain.department.*;
import groupflow.domain.employee.EmployeeDto;
import groupflow.domain.employee.EmployeeEntity;
import groupflow.domain.employee.EmployeeRepository;
import groupflow.domain.position.PositionChangeEntityRepository;
import groupflow.domain.position.PositionEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
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




    @Transactional //직원 출력 및 검색
    public List<EmployeeDto> searchEmplyee(int dno, int leavework , int key, String keyword){
        log.info("key1:"+key);log.info("keyword1:"+keyword);
        log.info("dno1:"+dno);log.info("dcendreason1:"+leavework);
        List<EmployeeEntity> entityList=null;

        if(key!=0){ // 검색o ex) 이름으로 홍 검색시 모든 직원의 홍씨를 검색하여 entityList 담는다
            entityList = employeeRepository.findemployeebyKeyWord(keyword);
        }else{// 검색x
            entityList = employeeRepository.findAll();
        }

        //검색을 하던 안하던 아래 코드는 실행한다

        List<EmployeeDto> dtoList=new ArrayList<>(); // 리턴할 빈 dtoList 생성


        entityList.forEach((e)->{

            int index =  e.getDepartmentChangeEntityList().size()-1;
            int index2= e.getPositionChangeEntityList().size()-1;

            EmployeeDto employeeDto=e.toDto();
            employeeDto.setDname( e.getDepartmentChangeEntityList().get(index).getDepartmentEntity().getDname());
            employeeDto.setPname( e.getPositionChangeEntityList().get(index2).getPositionEntity().getPname());
            employeeDto.setId(e.getEno()); //데이터테이블 출력용 id값이 필수로 필요하기때문

            if( e.getDepartmentChangeEntityList().get( index).getDepartmentEntity().getDno() == dno ){

                log.info("부서검색 : " + e.toString());
                // 근무자 = null
                if( leavework == 1 && e.getEenddate() == null  ){
                    log.info("근무자 : " + e.toString());
                    dtoList.add(employeeDto);

                }else if( leavework == 2 && e.getEenddate() !=null ){
                    log.info("퇴시자 : " + e.toString());
                    dtoList.add(employeeDto);
                }

            }else if( dno == 0 ){
                log.info("부서검색 : " + e.toString());
                // 근무자 = null
                if( leavework == 1 && e.getEenddate() == null  ){
                    log.info("근무자 : " + e.toString());
                    dtoList.add(employeeDto);

                }else if( leavework == 2 && e.getEenddate() !=null ){
                    log.info("퇴시자 : " + e.toString());
                    dtoList.add(employeeDto);
                }
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

    public int getdno(){ // 로그인한 직원의 부서 가져오기 왜? 관리부가 아니면 상세보기안나오게하려고

        int dno=0;
        Object o= SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        EmployeeDto employeeDto=(EmployeeDto) o;
        Optional<EmployeeEntity> employeeEntity=employeeRepository.findById(employeeDto.getEno());
        if(employeeEntity.isPresent()){
            int index =  employeeEntity.get().getDepartmentChangeEntityList().size()-1;
           dno=employeeEntity.get().getDepartmentChangeEntityList().get(index).getDepartmentEntity().getDno();
        }
        return dno;
    }

    //내가속한 부서의 직원들 출력하기
    public List<AttendanceListDto> getMyEmployees() {
        int dno=0;
        Object o= SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (o.equals("anonymousUser")) {
            return null;
        }
        EmployeeDto employeeDto=(EmployeeDto) o;
        Optional<EmployeeEntity> employeeEntity=employeeRepository.findById(employeeDto.getEno());

        List<AttendanceListDto>attendanceListDtoList=new ArrayList<>();

        if(employeeEntity.isPresent()){
            int index =  employeeEntity.get().getDepartmentChangeEntityList().size()-1;
            dno=employeeEntity.get().getDepartmentChangeEntityList().get(index).getDepartmentEntity().getDno();
        }
        log.info("mydno:"+dno);
        List<EmployeeEntity> entityList= employeeRepository.findByDno(dno);
        log.info("entityList:"+entityList);
        entityList.forEach((e)->{
            int index2=e.getPositionChangeEntityList().size()-1;

            DepartmentEntity departmentEntity=e.getDepartmentChangeEntityList().get(index2).getDepartmentEntity();
            PositionEntity positionEntity=e.getPositionChangeEntityList().get(index2).getPositionEntity();

            AttendanceListDto attendanceListDto=AttendanceListDto.builder()
                            .eno(e.getEno())
                            .dno(departmentEntity.getDno())
                            .pno(positionEntity.getPno())
                            .ename(e.getEname())
                            .dname(departmentEntity.getDname())
                            .pname(positionEntity.getPname())
                            .build();

            attendanceListDtoList.add(attendanceListDto);
        });
        return attendanceListDtoList;
    }
}