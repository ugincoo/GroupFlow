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


    //이름,사원 검색-장민정
    @Query( value = "select * from groupflow.employee where ename like %:keyword% or eno like %:keyword% ",nativeQuery = true ) // 이름,사번검색
    List<EmployeeEntity> findemployeebyKeyWord(@Param("keyword") String keyword);




    // 아이디 찾기 - 오유진
    EmployeeEntity findByEno(int Eno);

    //부서직원들찾기-장민정
    @Query(value="SELECT * FROM groupflow.employee natural join groupflow.departmentchange where dno= :dno and eenddate is null",nativeQuery = true)
    List<EmployeeEntity> findByDno(@Param("dno") int dno);



}



