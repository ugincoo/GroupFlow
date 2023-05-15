package groupflow.service;

import groupflow.domain.department.DepartmentChangeEntity;
import groupflow.domain.department.DepartmentEntity;
import groupflow.domain.employee.EmployeeDto;
import groupflow.domain.employee.EmployeeEntity;
import groupflow.domain.employee.EmployeeRepository;
import groupflow.domain.position.PositionChangeEntity;
import groupflow.domain.position.PositionEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class LoginService implements UserDetailsService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public UserDetails loadUserByUsername(String stringEno) throws UsernameNotFoundException {
        int eno = Integer.parseInt(stringEno);
        Optional<EmployeeEntity> optionalEmployeeEntity = employeeRepository.findById(eno);
        //entityOptional.ifPresent( o -> {  productEntityRepository.delete( o ); } );

        // 직원 엔티티를 찾았으면 엔티티 반환
        if (optionalEmployeeEntity.isPresent()){
            log.info("employeeDto : " +optionalEmployeeEntity.get().toDto());

                   EmployeeDto employeeDto = optionalEmployeeEntity.get().toDto();
                   log.info("employeeDto 테스트 : " + employeeDto);
                   employeeDto.setEname(new BCryptPasswordEncoder().encode(employeeDto.getEname()));
                    return employeeDto;
        }
        return new EmployeeDto();
    }

    // 로그인확인
    public EmployeeDto loginInfo( ){
        Object o = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        log.info("loginInfo : " + o);
        if ( o.equals("anonymousUser")){ return EmployeeDto.builder().ename("못찾음").build(); }
        EmployeeDto employeeDto = (EmployeeDto) o;
        Optional<EmployeeEntity> optionalEmployeeEntity = employeeRepository.findById(employeeDto.getEno());
        log.info("optionalEmployeeEntity : " + optionalEmployeeEntity);
        if(optionalEmployeeEntity.isPresent()){
            EmployeeEntity employeeEntity = optionalEmployeeEntity.get();
            List<DepartmentChangeEntity> departmentChangeEntityList = employeeEntity.getDepartmentChangeEntityList();
            List<PositionChangeEntity> positionChangeEntityList = employeeEntity.getPositionChangeEntityList();

            PositionEntity positionEntity = positionChangeEntityList.get(positionChangeEntityList.size()-1).getPositionEntity();
            DepartmentEntity departmentEntity = departmentChangeEntityList.get(departmentChangeEntityList.size()-1).getDepartmentEntity();


            return EmployeeDto.builder()
                    .eno(employeeEntity.getEno())
                    .ename(employeeEntity.getEname())
                    .pno(positionEntity.getPno())
                    .pname(positionEntity.getPname())
                    .dno(departmentEntity.getDno())
                    .dname(departmentEntity.getDname())
                    .build();
        }
        return null;
    }
}