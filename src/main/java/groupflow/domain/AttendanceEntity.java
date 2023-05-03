package groupflow.domain;

import lombok.*;

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

    @ManyToOne
    @JoinColumn(name="eno")
    @ToString.Exclude
    private EmployeeEntity employeeEntity;
}
