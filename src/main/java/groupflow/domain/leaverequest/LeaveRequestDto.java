package groupflow.domain.leaverequest;

import groupflow.domain.employee.EmployeeEntity;
import groupflow.domain.position.PositionEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data @AllArgsConstructor @NoArgsConstructor @Builder
public class LeaveRequestDto {
    private int lno;
    private String lrequestdate; // 신청날짜
    private String approvaldate; // 결재일
    private byte approvalstate; // 결재상태
    private String lstart; // 연차시작일
    private String lend; // 연차끝일
    private String requestreason; // 신청사
    // 추가
    private int dno; // 부서 넘버
    private String dname; // 부서이름
    private String ename; // 직원 이름
    private int yearno;//

    // 저장용
    public LeaveRequestEntity toEntity(){
        return LeaveRequestEntity.builder()
                .lno(this.lno)
                .approvaldate(this.approvaldate)
                .approvalstate(this.approvalstate)
                .requestreason(this.requestreason)
                .lrequestdate( LocalDate.now() )
                .lstart(LocalDate.parse(this.lstart))
                .lend(LocalDate.parse(this.lend))
                .build();
    }
}
