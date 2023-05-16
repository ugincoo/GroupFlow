package groupflow.domain.position;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PositionEntityRepository extends JpaRepository<PositionEntity,Integer> {

    // department테이블에서 부장의 dno찾기
    @Query( value = "SELECT * FROM groupflow.eposition where groupflow.eposition.pname = '부장';", nativeQuery = true )
    List<PositionEntity> findManagerDno();
}
