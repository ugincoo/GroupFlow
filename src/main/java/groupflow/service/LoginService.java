package groupflow.service;

import groupflow.domain.department.DepartmentChangeEntity;
import groupflow.domain.department.DepartmentEntity;
import groupflow.domain.department.DepartmentRepository;
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
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Slf4j
public class LoginService implements UserDetailsService {

    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    DepartmentRepository departmentRepository;

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String stringEno) throws UsernameNotFoundException {
        int eno = Integer.parseInt(stringEno);
        Optional<EmployeeEntity> optionalEmployeeEntity = employeeRepository.findById(eno);
        //entityOptional.ifPresent( o -> {  productEntityRepository.delete( o ); } );

        // 직원 엔티티를 찾았으면 엔티티 반환
        if (optionalEmployeeEntity.isPresent()){
            log.info("employeeDto : " +optionalEmployeeEntity.get().toDto());

            EmployeeEntity employeeEntity = optionalEmployeeEntity.get();
            log.info("employeeEntity 테스트 : " + employeeEntity);

            // 부서변경이력, 직급변경이력
            List<DepartmentChangeEntity> departmentChangeEntityList = employeeEntity.getDepartmentChangeEntityList();
            List<PositionChangeEntity> positionChangeEntityList = employeeEntity.getPositionChangeEntityList();

            // 부서,직급 Entity
            PositionEntity positionEntity = positionChangeEntityList.get(positionChangeEntityList.size()-1).getPositionEntity();
            DepartmentEntity departmentEntity = departmentChangeEntityList.get(departmentChangeEntityList.size()-1).getDepartmentEntity();
            ////////////////////////////////
            // 로그인시 권한목록에 추가
            Set<GrantedAuthority> securityPermissionList = new HashSet<>(); // securityPermissionList : 권한목록
            // 2. 권한객체 만들기 [ DB 존재하는 권한명( ROLE_!!!! )으로  ]
            // 권한 없을경우 : ROLE_ANONYMOUS  / 권한 있을경우 ROLE_권한명 : ROLE_admin , ROLE_user
            String prole = "";
            if ( positionEntity.getPname().equals("부장") ){ prole = "DIRECTOR"; }
            SimpleGrantedAuthority permission1 = new SimpleGrantedAuthority( "ROLE_"+positionEntity.getPname() ); // 직급
            log.info("permission1: " + permission1);
            SimpleGrantedAuthority permission2 = new SimpleGrantedAuthority( "ROLE_"+departmentEntity.getDname() ); // 부서
            log.info("permission2: " + permission2);
            // 3. 만든 권한객체를 권한목록[컬렉션]에  추가
            securityPermissionList.add( permission1 );
            securityPermissionList.add( permission2 );

            EmployeeDto employeeDto = employeeEntity.toDto();
            employeeDto.setEname(new BCryptPasswordEncoder().encode(employeeDto.getEname()));
            employeeDto.setPno(positionEntity.getPno());
            employeeDto.setPname(positionEntity.getPname());
            employeeDto.setDno(departmentEntity.getDno());
            employeeDto.setDname(departmentEntity.getDname());
            employeeDto.setSecurityPermissionList(securityPermissionList);
            ////////////////////////////////
            return employeeDto;
        }
        return null;
    }

    // 로그인확인
    public EmployeeDto loginInfo( ) {
        Object o = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        log.info("loginInfo : " + o);
        if (o.equals("anonymousUser")) {
            return null;
        }
        EmployeeDto employeeDto = (EmployeeDto) o;

        Optional<EmployeeEntity> optionalEmployeeEntity = employeeRepository.findById(employeeDto.getEno());
        log.info("optionalEmployeeEntity : " + optionalEmployeeEntity);
        if (optionalEmployeeEntity.isPresent()) {
            EmployeeEntity employeeEntity = optionalEmployeeEntity.get();
            
            // 직급 구하기
            List<PositionChangeEntity> positionChangeEntityList = employeeEntity.getPositionChangeEntityList();
            PositionEntity positionEntity = positionChangeEntityList.get(positionChangeEntityList.size() - 1).getPositionEntity();

            // 부서 구하기
            Optional<DepartmentEntity> optionalDepartmentEntity = departmentRepository.findByEno(employeeEntity.getEno());
            if(optionalDepartmentEntity.isPresent()) {
                DepartmentEntity departmentEntity = optionalDepartmentEntity.get();

                return EmployeeDto.builder()
                        .eno(employeeEntity.getEno())
                        .ename(employeeEntity.getEname())
                        .pno(positionEntity.getPno())
                        .pname(positionEntity.getPname())
                        .dno(departmentEntity.getDno())
                        .dname(departmentEntity.getDname())
                        .esocialno(0).id(0).build();
            }
        }
        return null;
    }
}