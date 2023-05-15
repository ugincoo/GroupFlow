package groupflow.domain.leaverequest;

import groupflow.domain.department.DepartmentEntity;
import groupflow.domain.employee.EmployeeEntity;
import groupflow.domain.position.PositionEntity;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "leaverequest")
public class LeaveRequestEntity { // 연차테이블
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int lno;
    @Column
    private LocalDate lrequestdate; // 신청날짜
    @Column
    private LocalDate approvaldate; // 결재일
    @Column
    private byte approvalstate; // 결재상태
    @Column
    private LocalDate lstart; // 연차시작일
    @Column
    private LocalDate lend; // 연차끝일
    @Column
    private String requestreason; // 신청사유

    @ManyToOne
    @JoinColumn(name="eno")
    @ToString.Exclude
    private EmployeeEntity employeeEntity;

    @ManyToOne
    @JoinColumn(name="dno")
    @ToString.Exclude
    private DepartmentEntity departmentEntity;

    //출력용
    public LeaveRequestDto toDto(){
        return LeaveRequestDto.builder()
                .lno(this.lno)
                .approvalstate(this.approvalstate)
                .requestreason(this.requestreason)
                .lrequestdate( this.lrequestdate.format(DateTimeFormatter.ofPattern("yy-MM-dd")) )
                //.approvaldate( this.approvaldate.format(DateTimeFormatter.ofPattern("yy-MM-dd")) )
                .lstart(this.lstart.format(DateTimeFormatter.ofPattern("yy-MM-dd")) )
                .lend(this.lend.format(DateTimeFormatter.ofPattern("yy-MM-dd")) )
                .build();
    }

}
