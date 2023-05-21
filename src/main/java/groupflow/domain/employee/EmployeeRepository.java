package groupflow.domain.employee;

import groupflow.domain.position.PositionEntity;
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


    // 입력한 dno, pno에 해당하는 직원이 존재하는지 찾기 - 유슬비
    @Query( value = "select e.eno from employee e , departmentchange dc , positionchange pc where e.eno = dc.eno and e.eno = pc.eno and dc.dno = :dno and pc.pno = :pno", nativeQuery = true)
    Optional<EmployeeEntity> findByDnoAndPno( @Param(value = "dno") int dno , @Param("pno") int pno);

    // 입력한 dno에 해당하는 부서 내 직원찾기 (부장제외) => 반환값 List<EmployeeEntity>라서 EmployeeRepository에 넣음 
    // 유슬비
    @Query( value = "select e.* from employee e , departmentchange dc , eposition p , positionchange pc where e.eno = dc.eno and e.eno = pc.eno and p.pno = pc.pno and pc.enddate is null and pname !='부장' and dcenddate is null and e.eenddate is null and dc.dno = :dno order by p.pno desc , e.hiredate asc" , nativeQuery = true)
    List<EmployeeEntity> getEmployeesByDepartmentWithoutManager(@Param(value = "dno") int dno);
}



