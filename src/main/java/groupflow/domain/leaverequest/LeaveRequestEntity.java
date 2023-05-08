package groupflow.domain.leaverequest;

import groupflow.domain.employee.EmployeeEntity;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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
    private byte approvalstate; // 결재상태
    @Column
    private LocalDateTime lstart; // 연차시작일
    @Column
    private LocalDateTime lend; // 연차끝일
    @Column
    private String requestreason; // 신청사유

    @ManyToOne
    @JoinColumn(name="eno")
    @ToString.Exclude
    private EmployeeEntity employeeEntity;

    //출력용
    public LeaveRequestDto toDto(){
        return LeaveRequestDto.builder()
                .lno(this.lno)
                .approvalstate(this.approvalstate)
                .requestreason(this.requestreason)
                .lrequestdate( this.lrequestdate.toLocalDate().format(DateTimeFormatter.ofPattern("yy-MM-dd")) )
                .approvaldate( this.approvaldate.toLocalDate().format(DateTimeFormatter.ofPattern("yy-MM-dd")) )
                .lstart(this.lstart.toLocalDate().format(DateTimeFormatter.ofPattern("yy-MM-dd")) )
                .lend(this.lend.toLocalDate().format(DateTimeFormatter.ofPattern("yy-MM-dd")) )
                .build();
    }

}
