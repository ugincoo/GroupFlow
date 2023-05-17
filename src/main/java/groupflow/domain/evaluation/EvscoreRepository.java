package groupflow.domain.evaluation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EvscoreRepository extends JpaRepository<EvscoreEntity,Integer> {
    
}
