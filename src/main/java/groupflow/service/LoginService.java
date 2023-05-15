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
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

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

            ////////////////////////////////
            // 로그인시 권한목록에 추가
            Set<GrantedAuthority> securityPermissionList = new HashSet<>(); // securityPermissionList : 권한목록
            // 2. 권한객체 만들기 [ DB 존재하는 권한명( ROLE_!!!! )으로  ]
            // 권한 없을경우 : ROLE_ANONYMOUS  / 권한 있을경우 ROLE_권한명 : ROLE_admin , ROLE_user
            SimpleGrantedAuthority permission1 = new SimpleGrantedAuthority( "ROLE_"+positionEntity.getPname() );
            // 3. 만든 권한객체를 권한목록[컬렉션]에  추가
            securityPermissionList.add( permission1 );

            ////////////////////////////////

            return EmployeeDto.builder()
                    .eno(employeeEntity.getEno())
                    .ename(employeeEntity.getEname())
                    .pno(positionEntity.getPno())
                    .pname(positionEntity.getPname())
                    .dno(departmentEntity.getDno())
                    .dname(departmentEntity.getDname())
                    .securityPermissionList( securityPermissionList )// 4. UserDetails 에 권한목록 대입
                    .build();
        }
        return null;
    }
}
