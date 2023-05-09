package groupflow.service;

import groupflow.domain.employee.EmployeeDto;
import groupflow.domain.employee.EmployeeEntity;
import groupflow.domain.employee.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

public class LoginLogoutService implements UserDetailsService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public UserDetails loadUserByUsername(String stringEno) throws UsernameNotFoundException {
        int eno = Integer.parseInt(stringEno);
        Optional<EmployeeEntity> optionalEmployeeEntity = employeeRepository.findById(eno);
        //entityOptional.ifPresent( o -> {  productEntityRepository.delete( o ); } );

        // 직원 엔티티를 찾았으면 엔티티 반환
        if (optionalEmployeeEntity.isPresent()){
            return optionalEmployeeEntity.get().toDto();

        }
        return new EmployeeDto();
    }
}
