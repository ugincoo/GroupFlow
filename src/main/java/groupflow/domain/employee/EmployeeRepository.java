package groupflow.domain.employee;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
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

    @Query( value =
            "SELECT * FROM groupflow.employee natural join groupflow.department_change " +
            " where dno= :dno order by dcstartdate desc limit 1 ;",nativeQuery = true ) // 부서별 직원출력하기
    List<EmployeeEntity> findemployeebydno( int dno);

    @Query( value =
            "SELECT * FROM groupflow.employee natural join groupflow.department_change " +
                    " where dcendreason= :dcendreason ;",nativeQuery = true ) // 입/퇴사 직원출력하기
    List<EmployeeEntity> findemployeebydcendreason( int dcendreason);

    @Query( value =
            "SELECT * FROM groupflow.employee natural join groupflow.department_change " +
                    " where dcendreason= :dcendreason and dno= :dno ;",nativeQuery = true ) // 입/퇴사 직원출력하기
    List<EmployeeEntity> findemployeebydcendreason_dno( int dcendreason,int dno);





}
