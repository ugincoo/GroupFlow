package groupflow.domain.leaverequest;

import groupflow.domain.position.PositionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LeaveRequestRepository extends JpaRepository <LeaveRequestEntity,Integer>{

}
