package groupflow.domain.department;

import groupflow.domain.employee.EmployeeEntity;
import groupflow.domain.position.PositionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DepartmentRepository extends JpaRepository< DepartmentEntity , Integer > {

    @Query(value = "select d.* from department d , departmentchange dc where d.dno= dc.dno and dc.eno=:eno and dc.dcenddate is null",nativeQuery = true)
    public Optional<DepartmentEntity> findByEno(@Param(value = "eno") int eno);

    @Query(value = "select dname from departmentchange where dno= :dno",nativeQuery = true)
    DepartmentEntity finddname(@Param("dno")int dno);
}

