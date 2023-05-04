package groupflow.domain.position;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PositionChangeEntityRepository extends JpaRepository<PositionChangeEntity,Integer> {
}
