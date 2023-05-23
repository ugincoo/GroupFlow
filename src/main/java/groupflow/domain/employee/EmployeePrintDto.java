package groupflow.domain.employee;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmployeePrintDto {
    private int eno;
    private String ename;
    private int dno; // 부서번호
    private int pno; // 직급번호
    private String dname; //부서명
    private String pname; //직급명

}
