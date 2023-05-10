package groupflow.service;

import groupflow.domain.employee.EmployeeDto;
import groupflow.domain.employee.EmployeeEntity;
import groupflow.domain.employee.EmployeeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

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
        return null;
    }
}
