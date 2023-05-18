package groupflow.domain.evaluation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EquestionRepository extends JpaRepository<EquestionEntity,Integer> {

}
