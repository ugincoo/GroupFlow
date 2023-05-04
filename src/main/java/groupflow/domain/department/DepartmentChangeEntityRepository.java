package groupflow.domain.department;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentChangeEntityRepository extends JpaRepository<DepartmentChangeEntity , Integer> {
}
