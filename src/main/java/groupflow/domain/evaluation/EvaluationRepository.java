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

    // 입력받은 평가대상자의 eno로 현재 분기 업무평가가 이미 등록되었는지 유효성검사
    @Query(value = "select * from groupflow.evaluation where cdate >= :startdate and cdate < :enddate and targeteno = :targeteno " , nativeQuery = true)
    List<EvaluationEntity> existsByEnoAndDate( @Param(value = "targeteno") int targeteno , @Param(value = "startdate") String startdate , @Param(value = "enddate") String enddate );
}
