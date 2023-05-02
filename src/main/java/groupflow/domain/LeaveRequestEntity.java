package groupflow.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "LeaveRequest")
public class LeaveRequestEntity { // 연차테이블
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int lno;
    @Column
    private LocalDateTime lrequestdate; // 신청날짜
    @Column
    private LocalDateTime approvaldate; // 결재일
    @Column
    private boolean approvalstate; // 결재상태
    @Column
    private LocalDateTime lstart; // 연차시작일
    @Column
    private LocalDateTime lend; // 연차끝일
    @Column
    private String requestreason; // 신청사유

    @OneToOne
    @JoinColumn(name="eno")
    private EmployeeEntity employeeEntity;
}
