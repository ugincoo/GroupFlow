package groupflow.service;

import groupflow.domain.employee.EmployeeDto;
import groupflow.domain.employee.EmployeeEntity;
import groupflow.domain.employee.EmployeeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
            return optionalEmployeeEntity.get().toDto();
        }
        return new EmployeeDto();
    }
}
