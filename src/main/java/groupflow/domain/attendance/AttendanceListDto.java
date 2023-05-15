package groupflow.domain.attendance;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data@AllArgsConstructor@NoArgsConstructor@Builder
public class AttendanceListDto {
    //내부서 직원들만 출력하기용 dto
    private int eno;
    private int dno;
    private int pno;
    private String ename;
    private String dname;
    private String pname;
    private String cdate;
    private String udate;


}
