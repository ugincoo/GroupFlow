package groupflow.domain.department;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class DepartmentChangeDto {

    private int dcno; // 식별번호
    private String dcstartdate; // 적용날짜
    private String dcenddate; // 끝날짜
    private String dcstartreason; // 부서변경사유
    private int dno ; // 부서테이블FK
    private int eno ; // 사원테이블FK

    //추가


    public DepartmentChangeEntity toEntity() {
        /*
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String dcstartdate = this.dcstartdate;
        LocalDateTime startdatetime = LocalDate.parse(dcstartdate,formatter).atStartOfDay();

        String dcenddate = this.dcenddate;
        LocalDateTime enddatetime = LocalDate.parse(dcenddate,formatter).atStartOfDay();

         */

        return  DepartmentChangeEntity.builder()
                .dcno(this.dcno)
                .dcstartdate(LocalDate.parse(this.dcstartdate))
                //.dcenddate(enddatetime)
                .dcstartreason(this.dcstartreason)
                .build();


    }
}
