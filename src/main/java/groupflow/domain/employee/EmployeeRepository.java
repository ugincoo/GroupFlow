package groupflow.domain.employee;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository< EmployeeEntity , Integer > {

    @Query( value = "SELECT * " +
                    " FROM employee " +
                    " WHERE hiredate >= :startDate " +
                    " AND hiredate <= :endDate " +
                    " ORDER BY eno DESC " +
                    " LIMIT 1; "
            , nativeQuery = true ) // '2023-01-01' // '2023-12-31'
    Optional<EmployeeEntity> findLastEmployeeIdByHireDateBetween( @Param("startDate") String startDate , @Param("endDate") String endDate );

    @Query( value =
           "SELECT * FROM groupflow.employee natural join groupflow.departmentchange where eenddate is null and dno= :dno" ,nativeQuery = true )
    List<EmployeeEntity> findemployeebydnoNull(@Param("dno") int dno); // 부서별 근무자만 출력

    @Query( value =
            "SELECT * FROM groupflow.employee natural join groupflow.departmentchange where eenddate is not null and dno= :dno",nativeQuery = true )
        List<EmployeeEntity> findemployeebydnoNotnull(@Param("dno") int dno);   // 부서별 퇴사자만 출력

    @Query( value =
            "select * from groupflow.employee where eenddate is null;",nativeQuery = true ) // 전직원 근무자만 출력
    List<EmployeeEntity> findemployeebyNulleenddate(@Param("leavework") int leavework);

    @Query( value =
            "select * from groupflow.employee where eenddate is not null;",nativeQuery = true ) // 모든 퇴사자 출력
    List<EmployeeEntity> findemployeebyNotNULLeenddate(@Param("leavework") int leavework);
    @Query( value =
            "select * from groupflow.employee where ename like %:keyword% or eno like %:keyword% ",nativeQuery = true ) // 모든 퇴사자 출력
    List<EmployeeEntity> findemployeebyKeyWord(@Param("keyword") String keyword);



    // 아이디 찾기 - 오유진
    EmployeeEntity findByEno(int Eno);



}



