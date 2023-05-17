package groupflow.domain.evaluation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EvaluationRepository extends JpaRepository<EvaluationEntity,Integer> {
    @Query(value = "select * from evaluation where targeteno = :targeteno" , nativeQuery = true)
    List<EvaluationEntity> findByTargeteno(@Param(value = "targeteno") int targeteno);
}
