package groupflow.domain.evaluation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EvscoreRepository extends JpaRepository<EvscoreEntity,Integer> {
    @Query(value = "select sc.* from groupflow.evaluation ev , groupflow.evscore sc where ev.evno = sc.evno and  ev.evno = :evno" , nativeQuery = true)
    List<EvscoreEntity> findByEvno(@Param("evno") int evno);
}
