package groupflow.domain.evaluation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

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

    @Query( value = "select eq.eqno , eq.eqtitle , sum(sc.eqscore)/(select count(*) from employee em , evaluation ev where eenddate is null and ev.evaluatoreno = em.eno and ev.cdate > if( month(noW()) < 7 , CONCAT(YEAR(now()),\"-05-20\") , CONCAT(YEAR(now()),\"-12-01\") ) \n" +
            "    and ev.cdate < if( month(noW()) < 7 , CONCAT(YEAR(now()),\"-07-01\") , CONCAT(YEAR(now()),\"-12-31\") )) as eqscore\n" +
            "\tfrom equestion eq , evscore sc , evaluation ev where eq.eqno = sc.eqno and sc.evno = ev.evno and \n" +
            "\tev.cdate > if( month(noW()) < 7 , CONCAT(YEAR(now()),\"-05-20\") , CONCAT(YEAR(now()),\"-12-01\") ) \n" +
            "    and ev.cdate < if( month(noW()) < 7 , CONCAT(YEAR(now()),\"-07-01\") , CONCAT(YEAR(now()),\"-12-31\") ) group by eq.eqno" , nativeQuery = true )
    List<CharView> findAllChart();

}
