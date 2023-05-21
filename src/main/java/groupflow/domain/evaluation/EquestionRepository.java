package groupflow.domain.evaluation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EquestionRepository extends JpaRepository<EquestionEntity,Integer> {
    
    // evno로 해당 업무평가의 점수리스트 가져오기

}
