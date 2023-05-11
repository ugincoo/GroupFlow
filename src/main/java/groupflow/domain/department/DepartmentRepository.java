package groupflow.domain.department;

import groupflow.domain.employee.EmployeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DepartmentRepository extends JpaRepository< DepartmentEntity , Integer > {

}

