package groupflow.domain.attendance;

import groupflow.domain.employee.EmployeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AttendanceRepository extends JpaRepository<AttendanceEntity , Integer> {

    @Query( value = " SELECT * FROM groupflow.attendance where eno= :eno and DATE(cdate)=DATE_FORMAT(now(), '%Y-%m-%d') " +
            " and DATE(cdate)= DATE(udate) order by cdate desc limit 1",nativeQuery = true ) // 이름,사번검색
    AttendanceEntity findByeno(@Param("eno") int eno);
}
