package groupflow.domain.position;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PositionEntityRepository extends JpaRepository<PositionEntity,Integer> {

    // department테이블에서 부장의 dno찾기
    @Query( value = "SELECT * FROM groupflow.eposition where groupflow.eposition.pname = '부장';", nativeQuery = true )
    List<PositionEntity> findManagerDno();

    // 연차 개수 찾기 - 유진추가
    @Query(value="select p.* from eposition p join positionchange pc on p.pno = pc.pno  where pc.eno = :eno order by pcdate desc limit 1 ",nativeQuery = true)
    List<PositionEntity> findByYearno(@Param(value = "eno")  int eno );
}
