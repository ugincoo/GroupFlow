package groupflow.domain.department;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DepartmentChangeEntityRepository extends JpaRepository<DepartmentChangeEntity , Integer> {

    /*
    @Query( value = "select * from departmentchange where eno = :eno" , nativeQuery = true )
    List<DepartmentChangeEntity> findAllMyDepartmetChangeList ( @Param("eno") int eno );
    */
    @Query( value = "select * from departmentchange where eno = :eno and dcenddate is null" , nativeQuery = true )
    DepartmentChangeEntity findAllMyDepartmetChangeList ( @Param("eno") int eno );

}
