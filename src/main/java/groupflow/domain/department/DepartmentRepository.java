package groupflow.domain.department;

import groupflow.domain.employee.EmployeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DepartmentRepository extends JpaRepository< DepartmentEntity , Integer > {
    // 05/10 유진 추가
    // 로그인 사원 부서 찾기
       // select * from Departmen where

}
