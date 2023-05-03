package groupflow.domain.employee;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.List;

@Data@AllArgsConstructor@NoArgsConstructor@Builder
public class EmployeeDto {
    private String eno;
    private String ename;   //이름
    private int esocialno;  //주민등록번호
    private String eemail;  //이메일
    private String ephone;  //휴대폰번호
    private String ephoto;  //사진
    private LocalDateTime hiredate; //입사일
    private LocalDateTime eenddate; //퇴사일

    public EmployeeEntity toEntity() {
        return EmployeeEntity.builder()
                .eno(this.eno)
                .ename(this.ename)
                .esocialno(this.esocialno)
                .eemail(this.eemail)
                .ephone(this.ephone)
                .ephoto(this.ephoto)
                .hiredate(this.hiredate)
                .eenddate(this.eenddate)
                .build();
    }
}
