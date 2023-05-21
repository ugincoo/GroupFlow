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
    @Query(value = "select * from groupflow.evaluation where cdate >= :startdate and cdate < :enddate and targeteno = :targeteno order by cdate desc" , nativeQuery = true)
    List<EvaluationEntity> existsByEnoAndDate( @Param(value = "targeteno") int targeteno , @Param(value = "startdate") String startdate , @Param(value = "enddate") String enddate );

    // 입력받은 평가자 eno로 항목 미완료된
    @Query(value = "select  ev.* from evaluation ev , evscore sc where ev.evno = sc.evno and ev.evaluatoreno=:evaluatoreno group by ev.evno having count(ev.evno)<10;" , nativeQuery = true)
    List<EvaluationEntity> findByEvaluatorenoIncompleteEvaluation( @Param(value = "evaluatoreno") int evaluatoreno);

    // 입력받은 평가자 eno로 평가의견 미작성된 evaluation찾기
    @Query( value = "select * from evaluation where evaluatoreno=:evaluatoreno and ( evopnion is null or evopnion = '' )" , nativeQuery = true)
    List<EvaluationEntity> findByEvaluatorenoIncompleteEvopnion( @Param(value = "evaluatoreno") int evaluatoreno);

    // 입력받은 evaluation 식별번호로 업무평가조회페이지에 필요한 모든 데이터 가져오기 // 오류, 컨버터 건들여야해서 없앨예정
    @Query( value = "select eq.eqno , eq.eqtitle , eq.equestion , sc.eqscore , ev.*  from evaluation ev , evscore sc , equestion eq where ev.evno = sc.evno and eq.eqno = sc.eqno and ev.evno = :evno order by eq.eqno" , nativeQuery = true)
    List<EvaluationViewDto> findByEvnoAll( @Param("evno") int evno);
}
