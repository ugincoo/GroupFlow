package groupflow.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data@AllArgsConstructor@NoArgsConstructor@Builder
@Table(name = "Attendance")
public class AttendanceEntity extends BaseTime { // 근태테이블 사원fk  출근  퇴근

    //private String CheckinTime;
    //private String CheckoutTime;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  int ano;

    @OneToOne
    @JoinColumn(name="eno")
    private EmployeeEntity employeeEntity;
}
