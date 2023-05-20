package groupflow.domain.attendance;

import groupflow.domain.employee.EmployeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AttendanceRepository extends JpaRepository<AttendanceEntity , Integer> {

    @Query( value = "SELECT * FROM groupflow.attendance where eno= :eno and DATE(cdate)=DATE_FORMAT(now(), '%Y-%m-%d')  and " +
            " DATE_FORMAT(cdate, '%Y-%m-%d %H:%i:%s') = DATE_FORMAT(udate, '%Y-%m-%d %H:%i:%s') order by cdate desc limit 1;",nativeQuery = true ) // 이름,사번검색
    Optional<AttendanceEntity> findByeno(@Param("eno") int eno);

    @Query( value = "SELECT DATE_FORMAT(cdate, '%H시%i분') FROM groupflow.attendance where eno= :eno and" +
            " date(cdate)=curdate() order by cdate desc limit 1",nativeQuery = true ) // 이름,사번검색
    String findCdatebyEno(@Param("eno") int eno);

    @Query( value = "SELECT DATE_FORMAT(udate, '%H시%i분') FROM groupflow.attendance where eno=:eno and date(udate)=curdate() and " +
            "DATE_FORMAT(cdate, '%H:%i:%s') <> DATE_FORMAT(udate, '%H:%i:%s') order by cdate desc limit 1",nativeQuery = true ) // 이름,사번검색
    String findUdatebyEno(@Param("eno") int eno);


}
