package groupflow.domain.attendance;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data@AllArgsConstructor@NoArgsConstructor@Builder
public class AttendanceListDto {

    private int eno;
    private int dno;
    private int pno;
    private String ename;
    private String dname;
    private String pname;
    private String cdate;
    private String udate;


}
