package groupflow.domain.department;

import groupflow.domain.employee.EmployeeEntity;
import groupflow.domain.position.PositionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DepartmentRepository extends JpaRepository< DepartmentEntity , Integer > {


}

