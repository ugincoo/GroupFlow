package groupflow.domain.employee;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository< EmployeeEntity , Integer > {

    @Query( value = "SELECT * " +
                    " FROM employee " +
                    " WHERE hire_date >= :startDate " +
                    " AND hire_date <= :endDate " +
                    " ORDER BY eno DESC " +
                    " LIMIT 1; "
            , nativeQuery = true ) // '2023-01-01' // '2023-12-31'
    Optional<EmployeeEntity> findLastEmployeeIdByHireDateBetween( String startDate , String endDate );
}
