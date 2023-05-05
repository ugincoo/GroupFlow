package groupflow.domain.leaverequest;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import java.time.LocalDateTime;

@Data @AllArgsConstructor @NoArgsConstructor @Builder
public class LeaveRequestDto {
    private int lno;
    private String lrequestdate; // 신청날짜
    private String approvaldate; // 결재일
    private byte approvalstate; // 결재상태
    private String lstart; // 연차시작일
    private String lend; // 연차끝일
    private String requestreason; // 신청사

    // 저장용
    public LeaveRequestEntity toEntity(){
        return LeaveRequestEntity.builder()
                .lno(this.lno)
                .approvalstate(this.approvalstate)
                .requestreason(this.requestreason)
                .lrequestdate( LocalDateTime.parse(this.lrequestdate) )
                .approvaldate( LocalDateTime.parse(this.approvaldate) )
                .lstart(LocalDateTime.parse(this.lstart))
                .lend(LocalDateTime.parse(this.lend))
                .build();
    }
}
